package com.example.csa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

public class SignIn extends AppCompatActivity {
    // UI declare
    EditText etEmail,etPassword;

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

        // Firebase declaration
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void signInButton(View view) {

        final SimpleArcDialog mDialog = new SimpleArcDialog(this);
        ArcConfiguration configuration = new ArcConfiguration(this);
        configuration.setLoaderStyle(SimpleArcLoader.STYLE.COMPLETE_ARC);
        configuration.setText("Please wait...");
        //int colorValue = Color.parseColor("#59B9F0");
        //configuration.setColors(new int[]{colorValue});
        mDialog.setConfiguration(configuration);

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
            mDialog.show();
            // Sign in user
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        mDialog.dismiss();
                        Toast.makeText(SignIn.this,"Authentication failed",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SignIn.this,"Welcome "+firebaseAuth.getCurrentUser().getDisplayName(),Toast.LENGTH_LONG).show();
                        Intent homePageIntent = new Intent(SignIn.this,HomePage.class);
                        homePageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(homePageIntent);
                    }
                }
            });
        }
    }


    public void onBackPressed() {
        super.onBackPressed();
        Intent mainIntent = new Intent(SignIn.this,MainActivity.class);
        //ActivityOptions option = ActivityOptions.makeCustomAnimation(SignIn.this,R.anim.slide_from_left,R.anim.no_anim);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }

    public void forgot_password_btn(View view) {
        SimpleArcDialog mDialog = new SimpleArcDialog(this);
        mDialog.setConfiguration(new ArcConfiguration(this));
        mDialog.setTitle("Please wait...");
        mDialog.show();
        Intent forgotPasswordIntent = new Intent(SignIn.this,ForgotPassword.class);
        startActivity(forgotPasswordIntent);
    }

    public void troubleSigningInBtn(View view) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SignIn.this);
        bottomSheetDialog.setContentView(R.layout.trouble_sign_in_bottom_sheet_dialog);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
//        Toast.makeText(SignIn.this,"Please email us your query",Toast.LENGTH_LONG).show();
//        Intent troubleIntent = new Intent(Intent.ACTION_SEND);
//        String[] recipient = {"deptcsacet@gmail.com"};
//        troubleIntent.putExtra(Intent.EXTRA_EMAIL,recipient);
//        troubleIntent.putExtra(Intent.EXTRA_SUBJECT,"Trouble signing in to the app");
//        troubleIntent.setType("text/html");
//        troubleIntent.setPackage("com.google.android.gm");
//        startActivity(Intent.createChooser(troubleIntent, "Send mail"));
    }

    public void adminBtn(View view) {
        Intent adminIntent = new Intent(SignIn.this,Admin.class);
        startActivity(adminIntent);
    }
}
