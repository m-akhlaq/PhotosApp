package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shaheer on 11/24/17.
 */

public class Photo implements Serializable, Parcelable {
    private String url;
    private ArrayList<String> location;
    private ArrayList<String> person;

    public Photo(String url){
        this.url=url;
        location=new ArrayList<>();
        person=new ArrayList<>();
    }
    public String getUrl(){
        return url;
    }
    public void addLocation(String loc){
        location.add(loc);
    }
    public void addPerson(String p){
        person.add(p);
    }
    public ArrayList<String> getPerson(){
        return person;
    }

    public ArrayList<String> getLocation() {
        return location;
    }

    protected Photo(Parcel in) {
        url = in.readString();
        if (in.readByte() == 0x01) {
            location = new ArrayList<String>();
            in.readList(location, String.class.getClassLoader());
        } else {
            location = null;
        }
        if (in.readByte() == 0x01) {
            person = new ArrayList<String>();
            in.readList(person, String.class.getClassLoader());
        } else {
            person = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        if (location == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(location);
        }
        if (person == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(person);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}