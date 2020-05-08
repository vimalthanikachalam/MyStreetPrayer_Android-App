package com.mystreetprayer.app.alarmclock.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mystreetprayer.app.KnowTheTruth;
import com.mystreetprayer.app.NotificationActivity;
import com.mystreetprayer.app.PrayerRequest;
import com.mystreetprayer.app.PrayerSongs_Activity;
import com.mystreetprayer.app.R;
import com.mystreetprayer.app.RegisterTimeActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class KPC_FirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessage";
    private static final String NOTIFICATION_CHANNEL_ID = "2";

    public KPC_FirebaseMessagingService(){

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        if(remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message Data Payload :" + remoteMessage.getData());

            try {
                JSONObject data = new JSONObject(remoteMessage.getData());
                String jsonMessage = data.getString("extra_information");
                Log.d(TAG, "onMessageRecived: \n" + "Extra Info:" + jsonMessage);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        if(remoteMessage.getNotification() != null) {

            String title = remoteMessage.getNotification().getTitle(); //get title
            String message = remoteMessage.getNotification().getBody(); //get message
            String click_action = remoteMessage.getNotification().getClickAction(); //get click_action

            Log.d(TAG, "Message Notification Title: " + title);
            Log.d(TAG, "Message Notification Body: " + message);
            Log.d(TAG, "Message Notification click_action: " + click_action);

            assert click_action != null;
            sendNotification(title, message, click_action);
        }

    }


    @Override
    public void onDeletedMessages() {

    }


    private void sendNotification(String title, String messageBody, String click_action) {
        Intent notificationIntent;

        if(click_action.equals("PRAYERSONGS")){
            notificationIntent = new Intent(this, PrayerSongs_Activity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        if(click_action.equals("PRAYER")){
            notificationIntent = new Intent(this, PrayerRequest.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        if(click_action.equals("BOOKS")){
            notificationIntent = new Intent(this, KnowTheTruth.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        if(click_action.equals("NOTIFICATION")){
            notificationIntent = new Intent(this, NotificationActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }


        else{

            notificationIntent = new Intent(this, RegisterTimeActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2 , notificationIntent, PendingIntent.FLAG_ONE_SHOT);


        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "KPC Messaging", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(2 /* Request Code */, notificationBuilder.build());

    }


}
