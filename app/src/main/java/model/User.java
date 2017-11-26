package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaheer on 11/23/17.
 */

public class User implements Serializable {

    private String name;
    private List<Album> albumList = new ArrayList<>();
    public User(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }
    public void addAlbum(Album album){
        albumList.add(album);
    }
    public List<Album> getAlbums(){
        return albumList;
    }

    public boolean nameExists(String proposedName){
        proposedName = proposedName.toLowerCase();
        for(Album a:albumList){
            if (a.getName().toLowerCase().equals(proposedName)){
                return true;
            }
        }
        return false;
    }

    public void setAlbumList(List<Album> albums){
         albumList.clear();albumList.addAll(albums);
    }
}
