package com.example.csa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.csa.Model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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

    String name,email,regdno,semester,prevName;

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
        etName = (EditText) findViewById(R.id.editprofile_edit_name);
        etEmail = (EditText) findViewById(R.id.editprofile_edit_email);


        // Firebase declare
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        prevName = firebaseUser.getDisplayName();
        databaseReference.child(firebaseUser.getDisplayName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Student student = (Student) dataSnapshot.getValue(Student.class);
                etName.setText(prevName);
                etEmail.setText(firebaseUser.getEmail());
                //etRegdNo.setText(student.getRegdNo());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    // Something
    public void exitEditButton(View view) {
        onBackPressed();
    }

    public void updateProfileButton(View view) {
        name = etName.getText().toString();
        email = etEmail.getText().toString();
        regdno = etRegdNo.getText().toString();
        semester = semester.substring(9);

       /*
        });semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semester = parent.getItemAtPosition(position).toString();
                semester = semester.substring(9);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                semester = semester.substring(9);
            }

        // Update Name
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
        firebaseUser.updateProfile(request).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this,"Update failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                // Update Email
                firebaseUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(EditProfile.this,"Update failed",Toast.LENGTH_SHORT).show();
                        } else {

                            // Update Sem & Regd
                            Student student = new Student(semester,regdno);
                            databaseReference.child(prevName).removeValue();
                            databaseReference.child(firebaseUser.getDisplayName()).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(EditProfile.this,"Update failed",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(EditProfile.this,"Update successful",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homePageIntent = new Intent(EditProfile.this,HomePage.class);
    }

    public void changeMyemail(View view) {

    }

    public void changeMyPassword(View view) {

    }

}
