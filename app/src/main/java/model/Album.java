package model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shaheer on 11/19/17.
 */

public class Album implements Serializable{

    private String name;
    private String date;
    private List<Photo> photosList;

    public Album(String name){
        this.name=name;
    }

    public void addPhoto(Photo photo){
        photosList.add(photo);
    }
    public void setName(String name){
        this.name=name;
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
