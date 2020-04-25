package com.mystreetprayer.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.mystreetprayer.app.alarmclock.ui.AlarmMainActivity;

public class Fragment_Home extends Fragment {

    private CardView  registerPrayerTime,prayerSong,knowthetruth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        prayerSong = (CardView) rootView.findViewById(R.id.prayer_songs);
        registerPrayerTime = (CardView) rootView.findViewById(R.id.register_PrayerTime);
        knowthetruth = (CardView) rootView.findViewById(R.id.knowtheTruth);

        prayerSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prayerIntent = new Intent(getActivity(), PrayerSongs_Activity.class);
                startActivity(prayerIntent);
            }
        });

        knowthetruth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent truthIntent = new Intent(getActivity(), AlarmMainActivity.class);
                startActivity(truthIntent);
            }
        });

        registerPrayerTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prayertimeView = new Intent(getActivity(), RegisterTimeActivity.class);
                startActivity(prayertimeView);
            }
        });


    return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Home");
    }
}
