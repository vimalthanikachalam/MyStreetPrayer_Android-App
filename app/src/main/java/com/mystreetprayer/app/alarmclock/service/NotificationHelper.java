package com.mystreetprayer.app.alarmclock.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

import com.mystreetprayer.app.DailyVerse_Activity;
import com.mystreetprayer.app.MainActivity;
import com.mystreetprayer.app.R;

public class NotificationHelper {

    private Context mContext;
    private static final String NOTIFICATION_CHANNEL_ID = "10001";


    NotificationHelper(Context context) {
        mContext = context;
    }

    void createNotification()
    {

        Bitmap bigImage = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.verse_img_2);
        Bitmap thumbnail = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);

        Intent intent = new Intent(mContext , DailyVerse_Activity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                100 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(R.drawable.ic_prayer_request);
        mBuilder.setContentTitle("Verse of the Day!")
                .setContentText("Click on the 'Notification' to read the verse!")
                .setLargeIcon(thumbnail)
                .setAutoCancel(true)
                .setSound(null)
//                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bigImage))
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Verse of the day!", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(100 /* Request Code */, mBuilder.build());
    }
}
