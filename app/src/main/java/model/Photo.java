package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by shaheer on 11/24/17.
 */

public class Photo implements Serializable, Parcelable {
    private String url;
    private String location;
    private String person;

    public Photo(String url){
        this.url=url;
    }
    public String getUrl(){
        return url;
    }


    protected Photo(Parcel in) {
        url = in.readString();
        location = in.readString();
        person = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(location);
        dest.writeString(person);
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
