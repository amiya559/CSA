package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

    // UI declare
    TextView tvName;


    // Firebase variables
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // UI initialize
        tvName = (TextView) findViewById(R.id.home_text_name_profile);

        // Firebase declare
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        tvName.setText(firebaseUser.getDisplayName());
    }

    public void signOutButton(View view) {
        firebaseAuth.signOut();
        Intent mainIntent = new Intent(HomePage.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }

    public void editProfileBtn(View view) {
        Intent editProfileIntent = new Intent(HomePage.this,EditProfile.class);
        startActivity(editProfileIntent);
    }

    public void feedback_btn(View view) {
        Intent feedbackBtnIntent = new Intent(HomePage.this,SelectFaculty.class);
        startActivity(feedbackBtnIntent);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        tvName.setText(firebaseUser.getDisplayName());
    }

    public void facultyInfo(View view) {
        Intent facultyInfo = new Intent(HomePage.this,FacultyInfo.class);
        startActivity(facultyInfo);
    }

    public void syllabus_btn(View view) {
        Intent viewSyllabus = new Intent(HomePage.this,Syllabus.class);
        startActivity(viewSyllabus);
    }
}
