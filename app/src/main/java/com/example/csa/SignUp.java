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
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;

public class SignUp extends AppCompatActivity {

    // UI declaration
    EditText etEmail,etPassword;

    String email,password;

    // Firebase variables
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // UI initialize
        etEmail = (EditText) findViewById(R.id.sign_up_edit_email);
        etPassword = (EditText) findViewById(R.id.sign_up_edit_password);

        // Firebase declaration
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void alreadyHaveAnAccountBtn(View view) {
        Intent signInIntent = new Intent(SignUp.this,SignIn.class);
        startActivity(signInIntent);
    }

    public void continueRegdBtn(View view) {

        final SimpleArcDialog mDialog = new SimpleArcDialog(this);
        mDialog.setConfiguration(new ArcConfiguration(this));
        mDialog.setTitle("Please wait...");

        // Close keyboard
        etPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);

        // Get data
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        // Verify data
        if (email.isEmpty()) {
            etEmail.requestFocus();
            etEmail.setError("Field cannot be left blank");
        } else if (!email.contains("@") || !email.contains(".com")) {
            etEmail.setText("");
            etEmail.requestFocus();
            etEmail.setError("Invalid format");
        } else if (password.isEmpty()) {
            etEmail.setError(null);
            etPassword.requestFocus();
            etPassword.setError("Field cannot be left blank");
        } else {
            etEmail.setError(null);
            etPassword.setError(null);
            mDialog.show();
            // Check duplicate email
            firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    boolean check = !task.getResult().getSignInMethods().isEmpty();
                    if (check) {
                        etEmail.setText("");
                        etEmail.requestFocus();
                        mDialog.dismiss();
                        etEmail.setError("Email ID already exists");
                    } else {

                        // Create email authentication
                        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUp.this,"Continuing Registration",Toast.LENGTH_SHORT).show();
                                    Intent continueRegIntent = new Intent(SignUp.this,HomePage.class);
                                    ActivityOptions option = ActivityOptions.makeCustomAnimation(SignUp.this,R.anim.slide_from_left,R.anim.no_anim);
                                    startActivity(continueRegIntent,option.toBundle());
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainIntent = new Intent(SignUp.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       // ActivityOptions option = ActivityOptions.makeCustomAnimation(SignUp.this,R.anim.slide_from_right,R.anim.no_anim);
        startActivity(mainIntent);
    }
}
