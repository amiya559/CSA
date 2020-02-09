package com.example.csa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    // UI declare
    EditText etEmail;
    LinearLayout layoutLoader;
    TextView tvLoaderText;

    String email;

    // Firebase variables
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // UI initialize
        etEmail = (EditText) findViewById(R.id.forgot_password_edit_email);
        layoutLoader = (LinearLayout) findViewById(R.id.forgot_password_layout_loader);
        tvLoaderText = (TextView) findViewById(R.id.forgot_password_text_loader);

        // Firebase declaration
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void resetPasswordBtn(View view) {
        email = etEmail.getText().toString().trim();
        if (email.isEmpty()) {
            etEmail.requestFocus();
            etEmail.setError("Field cannot be left blank");
        } else if (!email.contains("@") || !email.contains(".com")) {
            etEmail.setText("");
            etEmail.requestFocus();
            etEmail.setError("Email entered is invalid");
        }
        else {
            etEmail.setError(null);

            // Sending reset email
            layoutLoader.setVisibility(View.VISIBLE);
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPassword.this, "Password reset email sent", Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent(ForgotPassword.this,MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);
                    }else {
                        etEmail.setText("");
                        etEmail.requestFocus();
                        etEmail.setError("Email entered is invalid");
                        layoutLoader.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();Intent mainIntent = new Intent(ForgotPassword.this,MainActivity.class);
        Intent signInIntent = new Intent(ForgotPassword.this,SignIn.class);
        signInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityOptions option = ActivityOptions.makeCustomAnimation(ForgotPassword.this,R.anim.slide_from_left,R.anim.no_change);
        startActivity(signInIntent,option.toBundle());

    }
}
