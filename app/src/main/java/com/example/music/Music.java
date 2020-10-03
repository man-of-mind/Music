package com.example.music;

import android.widget.ImageView;

import java.util.ArrayList;

public class Music {
    private static String mImage;
    private String mArtist;
    private String mAlbum;
    private String mTrack;
    private String mPlaylist;

    public Music(String artist, String album, String track, String image){
        this.mAlbum = album;
        this.mArtist = artist;
        this.mTrack = track;
        this.mImage = image;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getPlaylist() {
        return mPlaylist;
    }

    public String getTrack() {
        return mTrack;
    }

    public static String getImage() {
        return mImage;
    }
}
