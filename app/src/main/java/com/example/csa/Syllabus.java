package com.example.csa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Syllabus extends AppCompatActivity {

    ListView myPDFListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        myPDFListView = (ListView) findViewById(R.id.myListView);

        String[] pdfFiles = {"Overview","Semester 1","Semester 2"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,pdfFiles){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                TextView myText = (TextView) view.findViewById(android.R.id.text1);

                return view;
            }
        };

        myPDFListView.setAdapter(adapter);

        myPDFListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String item = myPDFListView.getItemAtPosition(i).toString();

                Intent start = new Intent(getApplicationContext(),PDFOpener.class);

                start.putExtra("pdfFileName",item);
                startActivity(start);


            }
        });

    }
}
