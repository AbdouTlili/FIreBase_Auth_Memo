package com.example.abdou.csemembersapp;

import java.io.Serializable;
import java.util.HashMap;

        // class memo


public class Memo implements Serializable {

    private String name;
    private String comment;
    private String date;

    public Memo() {

    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String message) {
        this.comment = message;
    }


    public HashMap<String,String> toFirebaseObject() {
        HashMap<String,String> memo =  new HashMap<String,String>();
        memo.put("name", name);
        memo.put("message", comment);
        memo.put("date", date);

        return memo;
    }
}
