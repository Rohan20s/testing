package com.example.jsonextract;

public class Atc {
    String title,desc;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Atc(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }
}
