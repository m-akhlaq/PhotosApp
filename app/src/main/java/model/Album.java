package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaheer on 11/19/17.
 */

public class Album implements Serializable,Parcelable {

    private String name;
    private ArrayList<Photo> photosList = new ArrayList<>();

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
    public ArrayList<Photo> getPhotosList() {
        return photosList;
    }



    @Override
    public String toString() {
        return name;
    }

    protected Album(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0x01) {
            photosList = new ArrayList<Photo>();
            in.readList(photosList, Photo.class.getClassLoader());
        } else {
            photosList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (photosList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(photosList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}