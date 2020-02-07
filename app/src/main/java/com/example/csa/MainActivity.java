package com.example.csa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // UI declare
    LinearLayout layoutLoader,layoutSelectSetup;
    TextView tvLoaderText;

    // Firebase variables
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI Initialize
        layoutLoader = (LinearLayout) findViewById(R.id.main_layout_loader);
        layoutSelectSetup = (LinearLayout) findViewById(R.id.main_layout_select_setup);
        tvLoaderText = (TextView) findViewById(R.id.main_text_loader);

        // Firebase declare
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null && firebaseUser.isEmailVerified()) {
                    layoutLoader.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this,"Logging in",Toast.LENGTH_SHORT).show();
                    Intent homePageIntent = new Intent(MainActivity.this,HomePage.class);
                    homePageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homePageIntent);
                } else {
                    layoutLoader.setVisibility(View.INVISIBLE);
                    layoutSelectSetup.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    public void signUpNowButton(View view) {
        Intent intent=new Intent(MainActivity.this,SignUp.class);
        startActivity(intent);
    }

    public void signInNowBtn(View view) {
        Intent intent=new Intent(MainActivity.this,SignIn.class);
        startActivity(intent);
    }

    // Verify If User Is Signed In
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }
}
