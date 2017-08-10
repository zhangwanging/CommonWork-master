package com.hzu.jpg.commonwork.enity.moudle;

/**
 * Created by Administrator on 2017/4/15.
 */

public class Picture {

    private String id;

    private String picture;

    private String title;

    private String url;

    private String grade;


    public Picture() {}


    public Picture(String id, String picture, String title, String url, String grade) {
        this.id = id;
        this.picture = picture;
        this.title = title;
        this.url = url;
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }


    @Override
    public String toString() {
        return "Picture{" +
                "id='" + id + '\'' +
                ", picture='" + picture + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
