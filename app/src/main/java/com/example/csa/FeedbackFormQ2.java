package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackFormQ2 extends AppCompatActivity {

    String Message;
    private TextView rating;
    private AppCompatRatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form_q2);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.godown, R.anim.goup);
    }

    public void nextButton(View view) {

    }
}
