package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectFaculty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_faculty);
    }

    public void dasboardImageBtn(View view) {
        Intent facualtyBtnIntent = new Intent(SelectFaculty.this, FeedbackFormQ1.class);
        facualtyBtnIntent.putExtra("message","Question 1");
        startActivity(facualtyBtnIntent);
    }
}
