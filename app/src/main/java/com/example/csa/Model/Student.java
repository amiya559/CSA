package com.example.csa.Model;

public class Student {

    private String semester,regdNo;

    public Student(String semester, String regdNo) {
        this.semester = semester;
        this.regdNo = regdNo;
    }

    public Student() {
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
