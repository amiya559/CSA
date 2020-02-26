package com.example.csa.Model;

public class SyllabusPDF {

    public String name;
    public String url;

    public SyllabusPDF() {
    }

    public SyllabusPDF(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
