package com.example.csa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContinueRegistration extends AppCompatActivity {

    // UI declaration
    Spinner semesterSpinner;
    LinearLayout layoutLoader;
    EditText etRegdNo;
    TextView tvLoaderText;

    String semester,regdNo;

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
        layoutLoader = (LinearLayout) findViewById(R.id.continue_reg_layout_loader);
        etRegdNo = (EditText) findViewById(R.id.continue_reg_edit_reg_no);
        tvLoaderText = (TextView) findViewById(R.id.continue_reg_text_loader);

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

        // Set listener to spinner
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
        databaseReference = firebaseDatabase.getReference("Users").child(firebaseUser.getDisplayName());
    }

    public void signUpButton(View view) {
        // Close keyboard
        etRegdNo.onEditorAction(EditorInfo.IME_ACTION_DONE);

        // Get data
        regdNo = etRegdNo.getText().toString().trim();

        // Verify data
        if (regdNo.isEmpty()) {
            etRegdNo.requestFocus();
            etRegdNo.setError("Field left blank");
        } else if (semester.isEmpty()) {
            semesterSpinner.requestFocus();
            Toast.makeText(ContinueRegistration.this,"Select your semester",Toast.LENGTH_SHORT).show();
        }

        // Add data
        else {
            layoutLoader.setVisibility(View.VISIBLE);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Student student = (Student) dataSnapshot.getValue(Student.class);
                    student.setSemester(semester);
                    student.setRegdNo(regdNo);
                    databaseReference.setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()) {
                                layoutLoader.setVisibility(View.INVISIBLE);
                                Toast.makeText(ContinueRegistration.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                                deleteAccount();
                            } else {
                                layoutLoader.setVisibility(View.INVISIBLE);
                                Toast.makeText(ContinueRegistration.this,"Registration successful.. Verify your email",Toast.LENGTH_SHORT).show();
                                Intent signInIntent = new Intent(ContinueRegistration.this,SignIn.class);
                                signInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(signInIntent);
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

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
