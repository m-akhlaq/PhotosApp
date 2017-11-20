package com.app.msa.androidphotos15;

/**
 * Created by shaheer on 11/19/17.
 */

public class Album {

    private String name;
    private String date;

    public Album(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return name;
    }
}
