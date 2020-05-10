package com.mystreetprayer.app.alarmclock.view;

public class KPC_Notify_Firestore {

    private String title;
    private String description;
    private String webUrl;
    private String imageUrl;
    private String actionBtn;


    public KPC_Notify_Firestore(){

    }

    public KPC_Notify_Firestore(String title, String description,String webUrl,String imageUrl, String actionBtn){

        this.title = title;
        this.description = description;
        this.webUrl = webUrl;
        this.imageUrl = imageUrl;
        this.actionBtn = actionBtn;


    }

    public String getTitle() { return title; }

    public String getDescription() {
        return description;
    }

    public String getWebUrl(){
        return webUrl;
    }

    public String getImageUrl() { return imageUrl;}

    public String getActionBtn() { return actionBtn;}


}
