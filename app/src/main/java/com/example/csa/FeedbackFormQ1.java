package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackFormQ1 extends AppCompatActivity {

    String Message;
    private TextView rating;
    private AppCompatRatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form_q1);

        rating = (TextView) findViewById(R.id.ratevalue);
        ratingBar = (AppCompatRatingBar) findViewById(R.id.ratingbar);

        Message = getIntent().getStringExtra("message");
        Toast.makeText(this, Message , Toast.LENGTH_LONG).show();


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rateValue, boolean b) {
                rating.setText("Rating: " + rateValue);
            }
        });

    }


    public void nextButton(View view) {
        Intent nextIntent = new Intent(FeedbackFormQ1.this,FeedbackFormQ2.class);
        nextIntent.putExtra("message","Question 2");
        startActivity(nextIntent);
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}
