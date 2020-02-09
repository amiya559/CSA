package com.example.csa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    // UI declaration
    EditText etName,etEmail,etPassword;
    TextView tvLoaderText;
    LinearLayout layoutLoader;

    String name,email,password;

    // Firebase variables
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // UI initialize
        etName = (EditText) findViewById(R.id.sign_up_edit_name);
        etEmail = (EditText) findViewById(R.id.sign_up_edit_email);
        etPassword = (EditText) findViewById(R.id.sign_up_edit_password);
        tvLoaderText = (TextView) findViewById(R.id.sign_up_text_loader);
        layoutLoader = (LinearLayout) findViewById(R.id.sign_up_layout_loader);

        // Firebase declaration
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
    }

    public void alreadyHaveAnAccountBtn(View view) {
        Intent signInIntent = new Intent(SignUp.this,SignIn.class);
        startActivity(signInIntent);
    }

    public void continueRegdBtn(View view) {
        // Close keyboard
        etPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);

        // Get data
        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        // Verify data
        if (name.isEmpty()) {
            etName.requestFocus();
            etName.setError("Field cannot be left blank");
        } else if (email.isEmpty()) {
            etName.setError(null);
            etEmail.requestFocus();
            etEmail.setError("Field cannot be left blank");
        } else if (!email.contains("@") || !email.contains(".com")) {
            etName.setError(null);
            etEmail.setText("");
            etEmail.requestFocus();
            etEmail.setError("Invalid format");
        } else if (password.isEmpty()) {
            etEmail.setError(null);
            etPassword.requestFocus();
            etPassword.setError("Field cannot be left blank");
        } else {
            etEmail.setError(null);
            etName.setError(null);
            etPassword.setError(null);
            // Check duplicate email
            firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    boolean check = !task.getResult().getSignInMethods().isEmpty();
                    if (check) {
                        etEmail.setText("");
                        etEmail.requestFocus();
                        etEmail.setError("Email ID already exists");
                    } else {

                        // Create email authentication
                        layoutLoader.setVisibility(View.VISIBLE);
                        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()) {
                                    layoutLoader.setVisibility(View.GONE);
                                    Toast.makeText(SignUp.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                                } else {

                                    // Send email verification link
                                    firebaseUser = firebaseAuth.getCurrentUser();
                                    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(!task.isSuccessful()) {
                                                layoutLoader.setVisibility(View.GONE);
                                                Toast.makeText(SignUp.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                                                deleteAccount();
                                            } else {

                                                // Set user profile name
                                                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                                firebaseUser.updateProfile(request).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        layoutLoader.setVisibility(View.GONE);
                                                        Toast.makeText(SignUp.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                                                        deleteAccount();
                                                    }
                                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        // Store data to database
                                                        Student student= new Student(email);
                                                        databaseReference.child(firebaseUser.getDisplayName()).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (!task.isSuccessful()) {
                                                                    layoutLoader.setVisibility(View.GONE);
                                                                    Toast.makeText(SignUp.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                                                                    deleteAccount();
                                                                } else {
                                                                    layoutLoader.setVisibility(View.GONE);
                                                                    Toast.makeText(SignUp.this,"Continuing Registration",Toast.LENGTH_SHORT).show();
                                                                    Intent continueRegIntent = new Intent(SignUp.this,ContinueRegistration.class);
                                                                    ActivityOptions option = ActivityOptions.makeCustomAnimation(SignUp.this,R.anim.slide_from_left,R.anim.no_change);
                                                                    continueRegIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                    startActivity(continueRegIntent,option.toBundle());
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    private void deleteAccount() {
        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainIntent = new Intent(SignUp.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityOptions option = ActivityOptions.makeCustomAnimation(SignUp.this,R.anim.slide_from_right,R.anim.no_change);
        startActivity(mainIntent,option.toBundle());
    }
}
