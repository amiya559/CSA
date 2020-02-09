package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditProfile extends AppCompatActivity {

    Spinner semesterSpinner;
    String semester;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        semesterSpinner = (Spinner) findViewById(R.id.editprofile_spinner);

        // Create semester list
        List<String> semesterlist = new ArrayList<>();
        semesterlist.add(0, "Select Your Semester");
        semesterlist.add("Semester 1");
        semesterlist.add("Semester 2");
        semesterlist.add("Semester 3");
        semesterlist.add("Semester 4");
        semesterlist.add("Semester 5");
        semesterlist.add("Semester 6");

        // Style and populate the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, semesterlist);

        // Dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attaching data adapter to spinner
        semesterSpinner.setAdapter(adapter);





    }
}
