package com.mystreetprayer.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Random;

public class Fragment_Home extends Fragment {
    private FirebaseAnalytics mFirebaseAnalytics;
    public static TextView dailyVerse;
    public static TextView verseAuthor;




    private ImageView bannerImage;
    private int[] images = {
            R.drawable.bg_getting_started,R.drawable.bg,R.drawable.verse_img_1,
            R.drawable.verse_img_2, R.drawable.verse_img_3, R.drawable.verse_img_4,
            R.drawable.verse_img_5, R.drawable.verse_img_6, R.drawable.verse_img_7,
            R.drawable.verse_img_8, R.drawable.verse_img_9, R.drawable.verse_img_10,
            R.drawable.verse_img_11, R.drawable.verse_img_12, R.drawable.verse_img_13,
            R.drawable.verse_img_14, R.drawable.verse_img_15, R.drawable.verse_img_16,
            R.drawable.verse_img_17, R.drawable.verse_img_18, R.drawable.verse_img_19,
            R.drawable.verse_img_20};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        CardView prayerSong = (CardView) rootView.findViewById(R.id.prayer_songs);
        CardView registerPrayerTime = (CardView) rootView.findViewById(R.id.register_PrayerTime);
        CardView knowthetruth = (CardView) rootView.findViewById(R.id.knowtheTruth);
        CardView covid19page = (CardView) rootView.findViewById(R.id.covid_19);
        CardView prayerRequestPage = (CardView) rootView.findViewById(R.id.prayer_request);
        CardView testimonialPage = (CardView) rootView.findViewById(R.id.testimonial_view);
        CardView videoPage = (CardView) rootView.findViewById(R.id.videoViewCard);



        dailyVerse = (TextView) rootView.findViewById(R.id.daily_verse);
        verseAuthor = (TextView) rootView.findViewById(R.id.verse_author);
        bannerImage = (ImageView) rootView.findViewById(R.id.image_view);


        new VOTD_Data(getActivity()).execute();


        //BindMethod
        fetchDailyVerseData();
        randomImage();


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
                Intent truthIntent = new Intent(getActivity(), KnowTheTruth.class);
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


        covid19page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent covid19intent = new Intent(getActivity(), Covid19WebPage.class);
                startActivity(covid19intent);
            }
        });


        prayerRequestPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prayerrequstintent = new Intent(getActivity(), PrayerRequest.class);
                startActivity(prayerrequstintent);
            }
        });


        testimonialPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prayerrequstintent = new Intent(getActivity(), Testimonial.class);
                startActivity(prayerrequstintent);
            }
        });

        videoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoIntent = new Intent(getActivity(), VideosActivity.class);
                startActivity(videoIntent);
            }
        });

        return rootView;
    }

    private void fetchDailyVerseData() {

        new VOTD_Data(getContext()).execute();
    }


    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("Home");
    }

    //Daily Verse Random Images Pick from Local
    private void randomImage() {
        Random random = new Random();
        bannerImage.setImageResource(images[random.nextInt(images.length)]);

    }

}
