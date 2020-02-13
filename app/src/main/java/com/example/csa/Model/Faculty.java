package com.example.csa.Model;

public class Faculty {
    private int image;
    private String title;
    private String rank;
    private String qualification;
    private String subjects;
    private String doj;
    private String email;
    private String phoneno;

    public Faculty(int image, String title, String rank, String qualification, String subjects, String doj, String email, String phoneno) {
        this.image = image;
        this.title = title;
        this.rank = rank;
        this.qualification = qualification;
        this.subjects = subjects;
        this.doj = doj;
        this.email = email;
        this.phoneno = phoneno;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}

