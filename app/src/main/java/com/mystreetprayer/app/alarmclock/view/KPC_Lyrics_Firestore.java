package com.mystreetprayer.app.alarmclock.view;

public class KPC_Lyrics_Firestore {

    private String lyrics_title;
    private String lyrics_description;
    private String lyrics_webUrl;


    public KPC_Lyrics_Firestore(){

    }

    public String getLyrics_title() {
        return lyrics_title;
    }

    public String getLyrics_description() {
        return lyrics_description;
    }

    public String getLyrics_webUrl() {
        return lyrics_webUrl;
    }

    public KPC_Lyrics_Firestore(String lyrics_title, String lyrics_description, String lyrics_webUrl) {

        this.lyrics_title = lyrics_title;
        this.lyrics_description = lyrics_description;
        this.lyrics_webUrl = lyrics_webUrl;


    }





}
