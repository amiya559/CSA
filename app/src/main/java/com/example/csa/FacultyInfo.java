package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.os.Bundle;

import com.example.csa.Model.Faculty;

import java.util.ArrayList;
import java.util.List;

public class FacultyInfo extends AppCompatActivity {

    ViewPager viewPager;
    Adapter adapter;
    List<Faculty> faculties;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_info);

        faculties = new ArrayList<>();
        faculties.add(new Faculty(R.drawable.hodsir, "Dr. Jibitesh Mishra","HOD", "Ph.D. Computer Science", "Fractal Graphics, Web Engineering.", "14/07/1994","jmishra@cet.edu.in","9337832006"));
        faculties.add(new Faculty(R.drawable.profile, "Mrs.  Swarnalata Pati","Assistant Professor", "MCA (NIT, RKL) M.Tech. (NIT, RKL)", "Database Systems, Programming Languages", "05/11/1993","spati@cet.edu.in","9437101903"));
        faculties.add(new Faculty(R.drawable.manjitt, "Mr. Manjit Kumar Nayak","Assistant Professor", "MCA (CET, OUAT), M.Tech. in CS (Utkal Univ.)", "Mobile Computing, Design & Analysis of Algorithms, Theory of Automata", "22/03/2013","manjitcsa@cet.edu.in","9692268809"));
        faculties.add(new Faculty(R.drawable.ratnakar, "Mr. Ratnakar Das","Lecturer", "M.Tech.", "Analysis & Design of Algorithm, Theory of Computation", "","rkdas.puri@gmail.com","9437280851"));
        faculties.add(new Faculty(R.drawable.debashish, "Mr.  Debasis Gountia","Assistant Professor", "B.E.(UCE, Burla) M.Tech. (IIT, KGP)", "Data Structure, Cryptography, Distributed System", "24/01/2006","dgountia@gmail.com","9437229338"));
        faculties.add(new Faculty(R.drawable.profile, "Mrs.  Susmita Pal","Lecturer", "MCA(UU)", "Operating System, Java", "","susmitapal@gmail.com","9437389327"));
        faculties.add(new Faculty(R.drawable.profile, "Mrs.  Rojalin Mallick","Lecturer", "MCA (SOA)", "ITEJ, MIS, OOPS", "","rojalinmallick@gmail.com","9778574484"));
        faculties.add(new Faculty(R.drawable.profile, "Mr.  Amitav Mohapatra","Lecturer", "MCA(OUAT), M.Tech. CSE, CET(BPUT), BBSR", "Soft Computing, Software Engg", "","amitavmahapatracse@cet.edu.in","9937383858"));
        faculties.add(new Faculty(R.drawable.sushree, "Mrs. Sushree Sonam Mohapatra","Lecturer", "MCA, M.Tech. ", "Computer Organization and Architecture, Operating System", "","sonamsushree@gmail.com","9658578816"));



        adapter = new Adapter(faculties, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color5),
                getResources().getColor(R.color.color6),
                getResources().getColor(R.color.color7),
                getResources().getColor(R.color.color8),
                getResources().getColor(R.color.color9),


        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
