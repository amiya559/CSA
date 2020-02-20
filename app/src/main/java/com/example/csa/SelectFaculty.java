package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SelectFaculty extends AppCompatActivity {

    Spinner facultiesSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_faculty);

        facultiesSpinner = (Spinner) findViewById(R.id.select_faculty_spinner_faculties_list);

        // Create faculties list
        List<String> facultieslist = new ArrayList<>();
        facultieslist.add(0, "Select Your Faculty To Give Feedback");
        facultieslist.add("Dr. Jibitesh Mishra");
        facultieslist.add("Mrs.  Swarnalata Pati");
        facultieslist.add("Mr. Manjit Kumar Nayak");
        facultieslist.add("Mr. Ratnakar Das");
        facultieslist.add("Mr.  Debasis Gountia");
        facultieslist.add("Mrs.  Susmita Pal");

        // Style and populate the spinner_button
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, facultieslist);

        // Dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attaching data adapter to spinner_button
        facultiesSpinner.setAdapter(adapter);



    }

    public void selectFacultyBtn(View view) {
        Intent facualtyBtnIntent = new Intent(SelectFaculty.this, FeedbackFormQ1.class);
        facualtyBtnIntent.putExtra("message","Question 1");
        startActivity(facualtyBtnIntent);
    }
}
