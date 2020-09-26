package com.example.music;

import java.util.ArrayList;

public class Music {
    private String mArtist;
    private String mAlbum;
    private String mTrack;
    private String mPlaylist;

    public Music(String artist, String album, String track){
        this.mAlbum = album;
        this.mArtist = artist;
        this.mTrack = track;
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
}
