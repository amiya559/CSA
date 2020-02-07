package com.example.csa;

public class Student {

    private String email,semester,regdNo;

    public Student(String email, String semester, String regdNo) {
        this.email = email;
        this.semester = semester;
        this.regdNo = regdNo;
    }

    public Student(String email) {
        this.email = email;
    }

    public Student() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getRegdNo() {
        return regdNo;
    }

    public void setRegdNo(String regdNo) {
        this.regdNo = regdNo;
    }
}
