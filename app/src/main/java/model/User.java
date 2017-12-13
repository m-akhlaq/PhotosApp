package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaheer on 11/23/17.
 */

public class User implements Serializable, Parcelable {

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

    protected User(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0x01) {
            albumList = new ArrayList<Album>();
            in.readList(albumList, Album.class.getClassLoader());
        } else {
            albumList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (albumList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(albumList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}