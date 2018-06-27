package com.platzi.platzivideos.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.platzi.platzivideos.repositories.database.SqlVideoStatesHandler;

public class Video implements Parcelable {

    private final String urlImage;
    private final String title;
    private final String id;

    public Video(String urlImage, String title, String id) {
        this.urlImage = urlImage;
        this.title = title;
        this.id = id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Video)) return false;
        Video otherVideo = (Video) other;
        return otherVideo.id.equals(getId()) && otherVideo.title.equals(getTitle()) &&
                otherVideo.urlImage.equals(getUrlImage());
    }

    public static final Parcelable.Creator<Video> CREATOR
            = new Parcelable.Creator<Video>() {
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    private Video(Parcel in) {
        urlImage = in.readString();
        title = in.readString();
        id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(urlImage);
        parcel.writeString(title);
        parcel.writeString(id);
    }
}
