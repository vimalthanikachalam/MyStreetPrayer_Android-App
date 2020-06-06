package com.mystreetprayer.app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mystreetprayer.app.alarmclock.ui.AlarmMainActivity;


import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

import jonathanfinerty.once.Once;

public class Fragment_Home extends Fragment {
    public static TextView dailyVerse;
    public static TextView verseAuthor;
    private SharedPreferences sharedPreferences, registerdTimePref;

    private View rootView;
    private TextView greetingText, registerText;
    private ProgressBar progressBar;

    private String showWhatsNew = "showWhatsNewTag";


    private ImageView bannerImage;
    private int[] images = {
            R.drawable.bg_getting_started, R.drawable.bg, R.drawable.verse_img_1,
            R.drawable.verse_img_2, R.drawable.verse_img_3, R.drawable.verse_img_4,
            R.drawable.verse_img_5, R.drawable.verse_img_6, R.drawable.verse_img_7,
            R.drawable.verse_img_8, R.drawable.verse_img_9, R.drawable.verse_img_10,
            R.drawable.verse_img_11, R.drawable.verse_img_12, R.drawable.verse_img_13,
            R.drawable.verse_img_14, R.drawable.verse_img_15, R.drawable.verse_img_16,
            R.drawable.verse_img_17, R.drawable.verse_img_18, R.drawable.verse_img_19,
            R.drawable.verse_img_20,
            R.drawable.verse_img_new_2, R.drawable.verse_img_new_3, R.drawable.verse_img_new_4,
            R.drawable.verse_img_new_5, R.drawable.verse_img_new_6, R.drawable.verse_img_new_7,
            R.drawable.verse_img_new_8, R.drawable.verse_img_new_10, R.drawable.verse_img_new_11,
            R.drawable.verse_img_new_12, R.drawable.verse_img_new_13, R.drawable.verse_img_new_14,
            R.drawable.verse_img_new_15, R.drawable.verse_img_new_16, R.drawable.verse_img_new_17,
            R.drawable.verse_img_new_18, R.drawable.verse_img_new_19, R.drawable.verse_img_new_20,
            R.drawable.verse_img_new_21, R.drawable.verse_img_new_22, R.drawable.verse_img_new_23,
            R.drawable.verse_img_new_24, R.drawable.verse_img_new_25, R.drawable.verse_img_new_26,
            R.drawable.verse_img_new_27, R.drawable.verse_img_new_28, R.drawable.verse_img_new_29,
            R.drawable.verse_img_new_30, R.drawable.verse_img_new_31, R.drawable.verse_img_new_32,
            R.drawable.verse_img_new_33, R.drawable.verse_img_new_34
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //Analytics
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity());

        progressBar = (ProgressBar) rootView.findViewById(R.id.registertime_progress);
        registerText = (TextView) rootView.findViewById(R.id.register_text);

        CardView prayerSong = (CardView) rootView.findViewById(R.id.prayer_songs);
        CardView registerPrayerTime = (CardView) rootView.findViewById(R.id.register_PrayerTime);
        CardView knowthetruth = (CardView) rootView.findViewById(R.id.knowtheTruth);
        CardView covid19page = (CardView) rootView.findViewById(R.id.covid_19);
        CardView prayerRequestPage = (CardView) rootView.findViewById(R.id.prayer_request);
        CardView testimonialPage = (CardView) rootView.findViewById(R.id.testimonial_view);
        CardView videoPage = (CardView) rootView.findViewById(R.id.videoViewCard);
        CardView prayerRequest = (CardView) rootView.findViewById(R.id.prayerRequestView);


        //Notification
        ImageButton notificationView = (ImageButton) rootView.findViewById(R.id.notification_view_home);
        notificationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notification = new Intent(requireActivity(), NotificationActivity.class);
                startActivity(notification);
            }
        });


        bannerImage = (ImageView) rootView.findViewById(R.id.image_view1);

        new VOTD_Data_Fragment(getActivity()).execute();

        Once.initialise(requireActivity());

        //BindMethod
        randomImage();
        firestoreData();
        greetingMessage();
        prayerTimePref();

//        runOnce();

        prayerSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prayerIntent = new Intent(getActivity(), WorshipSongsMain.class);
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
//                Intent prayertimeView = new Intent(getActivity(), RegisterTimeActivity.class);
//                startActivity(prayertimeView);
                  firestoreRegisterdCheck();
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
                Intent prayerRequst = new Intent(getActivity(), PrayerPoints.class);
                startActivity(prayerRequst);
            }
        });


        testimonialPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testimonial = new Intent(getActivity(), Testimonial.class);
                startActivity(testimonial);
            }
        });

        videoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoIntent = new Intent(getActivity(), VideosActivity.class);
                startActivity(videoIntent);
            }
        });

        prayerRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prayerIntent = new Intent(getActivity(), PrayerRequests.class);
                startActivity(prayerIntent);
            }
        });

        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dailyVerseIntent = new Intent(getActivity(), DailyVerse_Activity.class);
                startActivity(dailyVerseIntent);
            }
        });

        return rootView;
    }


    //Greeting Message
    private void greetingMessage() {
        greetingText = (TextView) rootView.findViewById(R.id.user_greeting);

        //Save UserData in Local
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String getUSerName = sharedPreferences.getString("username", "");

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            greetingText.setText(getString(R.string.morning, getUSerName));
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            greetingText.setText(getString(R.string.afternoon, getUSerName));
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            greetingText.setText(getString(R.string.evening, getUSerName));
        } else if(timeOfDay >= 21 && timeOfDay < 24){
            greetingText.setText(getString(R.string.night, getUSerName));
        }
    }

    private void runOnce() {
        if (!Once.beenDone(Once.THIS_APP_VERSION, showWhatsNew)) {
            firstTargetSequence();
            Once.markDone(showWhatsNew);
        }
    }


    private void firstTargetSequence() {

        TapTargetView.showFor(requireActivity(), TapTarget.forView(rootView.findViewById(R.id.prayer_songs),
                "Worship Songs!", "Browse songs that are recorded in Faith Ministries Church, available in English,Kannada,Tamil & Hindi ")
                        .transparentTarget(true),
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        secondTarget();
                    }
                }
        );

    }

    private void secondTarget() {
        TapTargetView.showFor(requireActivity(), TapTarget.forView(rootView.findViewById(R.id.knowtheTruth),
                "Books", "Study to use the Word of God Correctly!").transparentTarget(true),
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        thirdTarget();
                    }
                }
        );
    }

    private void thirdTarget() {
        TapTargetView.showFor(requireActivity(), TapTarget.forView(rootView.findViewById(R.id.videoViewCard),
                "Sermons", "Live Videos and Audio updates for you!").transparentTarget(true),
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        fourthTarget();
                    }
                }
        );

    }

    private void fourthTarget() {
        TapTargetView.showFor(requireActivity(), TapTarget.forView(rootView.findViewById(R.id.daily_verse),
                "Daily Verse", "Every Day, New Verse is updated for you!").transparentTarget(true),
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        fithTarget();
                    }
                }
        );

    }

    private void fithTarget() {
        TapTargetView.showFor(requireActivity(), TapTarget.forView(rootView.findViewById(R.id.register_PrayerTime),
                "Register Your Prayer Time!", "One last thing, before we finish our Introduction. \n" +
                        "Claim your community for Christ! Register now!.").transparentTarget(true));
    }


    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("Home");
        dailyVerse = (TextView) rootView.findViewById(R.id.daily_verse);
        verseAuthor = (TextView) rootView.findViewById(R.id.verse_author);

    }

    //Daily Verse Random Images Pick from Local
    private void randomImage() {
        Random random = new Random();
        bannerImage.setImageResource(images[random.nextInt(images.length)]);

    }

    //Get username
    private void firestoreData() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        firestore.collection("users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    assert documentSnapshot != null;
                    String username = documentSnapshot.getString("fName");
                    sharedPreferences
                            .edit()
                            .putString("username", username)
                            .apply();
                }
            }
        });
    }


    private void firestoreRegisterdCheck() {

        progressBar.setVisibility(View.VISIBLE);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        firestore.collection("users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();

                    assert documentSnapshot != null;
                    if(documentSnapshot.exists()){
                        String registration = documentSnapshot.getString("prayerRegistration");

                        if(registration != null && registration.equals("true")){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Your Prayer Time is Already Registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), AlarmMainActivity.class));
                            String usertime = documentSnapshot.getString("prayerTime");

                            registerdTimePref
                                    .edit()
                                    .putString("user_time", usertime)
                                    .apply();


                        }else {
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getActivity(), RegisterTimeActivity.class));
                            Toast.makeText(getActivity(), "Register Prayer Time", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(getActivity(), RegisterTimeActivity.class));
                        Toast.makeText(getActivity(), "Data Not Found", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void prayerTimePref(){
        //Save UserData in Local
        registerdTimePref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String getUserTime = registerdTimePref.getString("user_time", null);

        if(getUserTime != null) {
            registerText.setText("Your Prayer Time  :  " + getUserTime);
        }else{
            registerText.setText("Register Prayer Time");
        }

    }

}
