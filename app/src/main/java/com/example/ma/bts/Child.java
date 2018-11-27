package com.example.ma.bts;

/**
 * Created by BTS on 10/29/18.
 */

public class Child {
    String name, grade, image ,parentId,lang;
    int phoneNum;

    public Child() {
    }

    public Child(String name, String grade, String image, String parentId, String lang, int phoneNum) {
        this.name = name;
        this.grade = grade;
        this.image = image;
        this.parentId = parentId;
        this.lang = lang;
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getparentId() {
        return parentId;
    }

    public void setparentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

}