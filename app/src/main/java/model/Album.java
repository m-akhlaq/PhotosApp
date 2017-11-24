package model;

import java.util.List;

/**
 * Created by shaheer on 11/19/17.
 */

public class Album {

    private String name;
    private String date;
    private List<Photo> photosList;

    public Album(String name){
        this.name=name;
    }

    public void addPhoto(Photo photo){
        photosList.add(photo);
    }


    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public List<Photo> getPhotosList() {
        return photosList;
    }

    @Override
    public String toString() {
        return name;
    }
}
