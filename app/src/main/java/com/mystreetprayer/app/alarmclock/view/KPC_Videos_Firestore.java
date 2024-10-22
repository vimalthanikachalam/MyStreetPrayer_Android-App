package com.mystreetprayer.app.alarmclock.view;

public class KPC_Videos_Firestore {

    private String videoTitle;
    private String videoDescription;
    private String videoUrl;
    private String videoImage;
    private String videoExpanded;
    private int sort;

    public KPC_Videos_Firestore(){

    }

    public KPC_Videos_Firestore(String videoTitle, String videoDescription,String videoUrl, String videoImage, String videoExpanded, int sort){
        this.videoTitle = videoTitle;
        this.videoDescription = videoDescription;
        this.videoUrl = videoUrl;
        this.videoImage = videoImage;
        this.videoExpanded = videoExpanded;
        this.sort = sort;

    }


    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public String getVideoExpanded() { return videoExpanded; }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public int getSort() {
        return sort;
    }
}
