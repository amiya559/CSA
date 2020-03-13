package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class PDFOpener extends AppCompatActivity {

    PDFView myPDFViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_opener);

        myPDFViewer = (PDFView) findViewById(R.id.pdfViewer);

        String getItem = getIntent().getStringExtra("pdfFileName");

        if (getItem.equals("Overview")){

            myPDFViewer.fromAsset("Overview.pdf").load();
        }

        if (getItem.equals("Semester 1")){

            myPDFViewer.fromAsset("Semester 1.pdf").load();
        }

        if (getItem.equals("Semester 2")){

            myPDFViewer.fromAsset("Semester 2.pdf").load();
        }

    }
}
