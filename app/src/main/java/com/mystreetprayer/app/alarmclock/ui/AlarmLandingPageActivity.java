package com.mystreetprayer.app.alarmclock.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mystreetprayer.app.R;

public final class AlarmLandingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity_landing_page);

    }

    public static Intent launchIntent(Context context) {
        final Intent i = new Intent(context, AlarmLandingPageActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        return i;
    }

}
