package com.example.csa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    // UI declare
    EditText etEmail,etPassword;
    LinearLayout layoutLoader;
    TextView tvLoaderText;

    String email,password;

    // Firebase variables
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // UI initialize
        etEmail = (EditText) findViewById(R.id.sign_in_edit_email);
        etPassword = (EditText) findViewById(R.id.sign_in_edit_password);
        layoutLoader = (LinearLayout) findViewById(R.id.sign_in_layout_loader);
        tvLoaderText = (TextView) findViewById(R.id.sign_in_text_loader);

        // Firebase declaration
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void signInButton(View view) {
        // Get data
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        // Verify data
        if (email.isEmpty()) {
            etEmail.requestFocus();
            etEmail.setError("Field left blank");
        } else if (!email.contains("@") || !email.contains(".com")) {
            etEmail.setText("");
            etEmail.requestFocus();
            etEmail.setError("Invalid format");
        } else if (password.isEmpty()) {
            etPassword.requestFocus();
            etPassword.setError("Field left blank");
        } else {

            // Sign in user
            layoutLoader.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        layoutLoader.setVisibility(View.GONE);
                        Toast.makeText(SignIn.this,"Unable to log in/Password may be incorrect or changed",Toast.LENGTH_LONG).show();
                    } else {

                        // Check for email verification
                        if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                            layoutLoader.setVisibility(View.GONE);
                            Toast.makeText(SignIn.this,"Log in successful",Toast.LENGTH_LONG).show();
                            Intent homePageIntent = new Intent(SignIn.this,HomePage.class);
                            homePageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homePageIntent);
                        } else {
                            layoutLoader.setVisibility(View.GONE);
                            Toast.makeText(SignIn.this,"Email verification incomplete",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainIntent = new Intent(SignIn.this,MainActivity.class);
        ActivityOptions option = ActivityOptions.makeCustomAnimation(SignIn.this,R.anim.slide_from_left,R.anim.no_anim);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent,option.toBundle());
    }

    public void forgot_password_btn(View view) {
        Intent forgotPasswordIntent = new Intent(SignIn.this,ForgotPassword.class);
        ActivityOptions option = ActivityOptions.makeCustomAnimation(SignIn.this,R.anim.slide_from_right,R.anim.no_anim);
        startActivity(forgotPasswordIntent,option.toBundle());
    }

    public void troubleSigningInBtn(View view) {
        Toast.makeText(SignIn.this,"Please email us your query",Toast.LENGTH_LONG).show();
        Intent troubleIntent = new Intent(Intent.ACTION_SEND);
        String[] recipient = {"deptcsacet@gmail.com"};
        troubleIntent.putExtra(Intent.EXTRA_EMAIL,recipient);
        troubleIntent.putExtra(Intent.EXTRA_SUBJECT,"Trouble signing in to the app");
        troubleIntent.setType("text/html");
        troubleIntent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(troubleIntent, "Send mail"));
    }
}
