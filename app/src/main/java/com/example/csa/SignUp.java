package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void alreadyHaveAnAccountBtn(View view) {
        Intent intent=new Intent(SignUp.this,SignIn.class);
        startActivity(intent);
    }

    public void continueRegdBtn(View view) {
        Intent intent=new Intent(SignUp.this,ContinueRegistration.class);
        startActivity(intent);
    }
}
