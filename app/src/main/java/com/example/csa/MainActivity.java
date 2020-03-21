package com.example.csa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    // Firebase variables
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase declare
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Toast.makeText(MainActivity.this,"Welcome back "+firebaseUser.getDisplayName(),Toast.LENGTH_SHORT).show();
                    Intent homePageIntent = new Intent(MainActivity.this,HomePage.class);
                    homePageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homePageIntent);
                }
            }
        };
    }

    public void signUpNowButton(View view) {
        SimpleArcDialog mDialog = new SimpleArcDialog(this);
        mDialog.setConfiguration(new ArcConfiguration(this));
        mDialog.setTitle("Please wait...");
        mDialog.show();
        Intent signUpIntent = new Intent(MainActivity.this,SignUp.class);
        //ActivityOptions option = ActivityOptions.makeCustomAnimation(MainActivity.this,R.anim.slide_from_left,R.anim.no_anim);
        startActivity(signUpIntent);
    }

    public void signInNowBtn(View view) {

        // Arc loader
        final SimpleArcDialog mDialog = new SimpleArcDialog(this);
        ArcConfiguration configuration = new ArcConfiguration(this);
        configuration.setLoaderStyle(SimpleArcLoader.STYLE.COMPLETE_ARC);
        configuration.setText("Please wait...");
        mDialog.setConfiguration(configuration);
        mDialog.show();

        Intent signInIntent = new Intent(MainActivity.this,SignIn.class);
        //ActivityOptions option = ActivityOptions.makeCustomAnimation(MainActivity.this,R.anim.slide_from_right,R.anim.no_anim);
        startActivity(signInIntent);
    }

    // Verify If User Is Signed In
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }
}
