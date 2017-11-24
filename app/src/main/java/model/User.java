package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaheer on 11/23/17.
 */

public class User {

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
    public void setAlbumList(List<Album> albums){
        albumList.addAll(albums);
    }
}
