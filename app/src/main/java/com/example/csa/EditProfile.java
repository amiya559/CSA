package com.example.csa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class EditProfile extends AppCompatActivity {

    // UI declare
    EditText etName,etEmail,etRegdNo;
    Spinner semesterSpinner;

    String name,email,regdno,semester;

    // Firebase variables
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // UI initialize
        semesterSpinner = (Spinner) findViewById(R.id.editprofile_spinner_sem);
        etName = (EditText) findViewById(R.id.editprofile_edit_name);
        etEmail = (EditText) findViewById(R.id.editprofile_edit_email);
        etRegdNo = (EditText) findViewById(R.id.editprofile_edit_regdno);

        // Create semester list
        List<String> semesterlist = new ArrayList<>();
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

        // Firebase declare
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(firebaseUser.getDisplayName());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Student student = (Student) dataSnapshot.getValue(Student.class);
                etName.setText(firebaseUser.getDisplayName());
                etEmail.setText(student.getEmail());
                etRegdNo.setText(student.getRegdNo());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void exitEditButton(View view) {
        onBackPressed();
    }

    public void updateProfileButton(View view) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homePageIntent = new Intent(EditProfile.this,HomePage.class);
    }
}
