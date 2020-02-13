package com.example.csa;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.csa.Model.Faculty;

import java.util.List;

public class Adapter extends PagerAdapter {

    private List<Faculty> faculties;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(List<Faculty> faculties, Context context) {
        this.faculties = faculties;
        this.context = context;
    }

    @Override
    public int getCount() {
        return faculties.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.faculties, container, false);

        ImageView imageView;
        TextView title, rank,  qualification, subjects, doj, email, phoneno;

        imageView = view.findViewById(R.id.image);
        rank = view.findViewById(R.id.rank);
        title = view.findViewById(R.id.title);
        qualification = view.findViewById(R.id.qualification);
        subjects = view.findViewById(R.id.subjects);
        doj = view.findViewById(R.id.dateOfJoining);
        email = view.findViewById(R.id.emails);
        phoneno = view.findViewById(R.id.phoneno);



        imageView.setImageResource(faculties.get(position).getImage());
        title.setText(faculties.get(position).getTitle());
        rank.setText(faculties.get(position).getRank());
        qualification.setText(faculties.get(position).getQualification());
        subjects.setText(faculties.get(position).getSubjects());
        doj.setText(faculties.get(position).getDoj());
        email.setText(faculties.get(position).getEmail());
        phoneno.setText(faculties.get(position).getPhoneno());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
