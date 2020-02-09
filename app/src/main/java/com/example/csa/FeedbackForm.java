package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class FeedbackForm extends AppCompatActivity {

    private TextView rating;
    private Button nextButton;
    private AppCompatRatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);

        rating = (TextView) findViewById(R.id.ratevalue);
        nextButton = (Button) findViewById(R.id.nextButton);
        ratingBar = (AppCompatRatingBar) findViewById(R.id.ratingbar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rateValue, boolean b) {
                rating.setText("Rating :" + rateValue);
            }
        });

    }


}
