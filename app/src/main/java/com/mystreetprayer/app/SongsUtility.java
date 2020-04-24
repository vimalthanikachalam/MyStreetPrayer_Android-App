package com.mystreetprayer.app;

public class SongsUtility {
    private String songName, songUrl;

    public SongsUtility() {
    }

    public SongsUtility(String songName, String songUrl) {
        this.songName = songName;
        this.songUrl = songUrl;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }


}
