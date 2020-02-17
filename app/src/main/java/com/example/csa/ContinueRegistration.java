package com.example.csa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csa.Model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContinueRegistration extends AppCompatActivity {

    // UI declaration
    Spinner semesterSpinner;
    EditText etRegdNo,etName;

    String semester,regdNo,name;

    // Firebase variables
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_registration);

        // UI initialize
        semesterSpinner = (Spinner) findViewById(R.id.continue_reg_spinner_semester_list);
        etRegdNo = (EditText) findViewById(R.id.continue_reg_edit_reg_no);
        etName = (EditText) findViewById(R.id.continue_reg_edit_name);

        // Create semester list
        List<String> semesterlist = new ArrayList<>();
        semesterlist.add(0, "Select Your Semester");
        semesterlist.add("Semester 1");
        semesterlist.add("Semester 2");
        semesterlist.add("Semester 3");
        semesterlist.add("Semester 4");
        semesterlist.add("Semester 5");
        semesterlist.add("Semester 6");

        // Style and populate the spinner_button
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, semesterlist);

        // Dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attaching data adapter to spinner_button
        semesterSpinner.setAdapter(adapter);

        // Set listener to spinner_button
        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)view).setTextSize(13);
                ((TextView)view).setTypeface(Typeface.DEFAULT_BOLD);
                ((TextView)view).setTextColor(Color.parseColor("#757575"));
                if (parent.getItemAtPosition(position).equals("Select Your Semester")) {
                    semester = "1";
                }
                else {
                    semester = parent.getItemAtPosition(position).toString();
                    semester = semester.substring(9);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                semester = "1";
            }
        });

        // Firebase declaration
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
    }

    public void signUpButton(View view) {
        // Get data
        name = etName.getText().toString().trim();
        regdNo = etRegdNo.getText().toString().trim();

        // Verify data
        if (name.isEmpty()) {
            etName.requestFocus();
            etName.setError("Field left blank");
        } else if (regdNo.isEmpty()) {
            etName.setError(null);
            etRegdNo.requestFocus();
            etRegdNo.setError("Field left blank");
        }

        // Add data
        else {
            etName.setError(null);
            etRegdNo.setError(null);

            // Set display name
            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
            firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(ContinueRegistration.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                    } else {
                        // Add details to database
                        Student student = new Student(semester,regdNo);
                        databaseReference.child(name).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(!task.isSuccessful()) {
                                    Toast.makeText(ContinueRegistration.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                                } else {
                                    firebaseAuth.signOut();
                                    Toast.makeText(ContinueRegistration.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                    Intent signInIntent = new Intent(ContinueRegistration.this,SignIn.class);
                                    signInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(signInIntent);
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    private void deleteAccount() {
        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteAccount();
        Toast.makeText(ContinueRegistration.this,"Registration Failed",Toast.LENGTH_SHORT).show();
        Intent signUpIntent = new Intent(ContinueRegistration.this,SignUp.class);
        signUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signUpIntent);
    }
}
