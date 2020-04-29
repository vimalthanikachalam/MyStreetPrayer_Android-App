package com.mystreetprayer.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mystreetprayer.app.alarmclock.service.AlarmReceiver;
import com.mystreetprayer.app.alarmclock.ui.AlarmMainActivity;

import org.json.JSONObject;

import java.util.Objects;
import java.util.Random;

import static android.text.TextUtils.isEmpty;

public class Fragment_Home extends Fragment {

    public static TextView dailyVerse;
    public static TextView verseAuthor;

    public String myText = "";


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

        CardView prayerSong = (CardView) rootView.findViewById(R.id.prayer_songs);
        CardView registerPrayerTime = (CardView) rootView.findViewById(R.id.register_PrayerTime);
        CardView knowthetruth = (CardView) rootView.findViewById(R.id.knowtheTruth);
        CardView covid19page = (CardView) rootView.findViewById(R.id.covid_19);
        CardView prayerRequestPage = (CardView) rootView.findViewById(R.id.prayer_request);


        dailyVerse = (TextView) rootView.findViewById(R.id.daily_verse);
        verseAuthor = (TextView) rootView.findViewById(R.id.verse_author);
        bannerImage = (ImageView) rootView.findViewById(R.id.image_view);



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



        return rootView;
    }

    private void fetchDailyVerseData() {
        VOTD_Data process = new VOTD_Data();
        process.execute();
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




    //Dialog to view full verse of the day
//    private void viewFullVerse() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle(R.string.word_for_today);
//        SharedPreferences sp = getActivity().getSharedPreferences("verse_sp", Context.MODE_PRIVATE);
//        if (sp != null) {
//            String vas = sp.getString("verse", "bible verse");
//            builder.setMessage(vas);
//        } else {
//            builder.setMessage(res);
//        }
//        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // User clicked the "Delete" button, so delete the pet.
//                dialog.dismiss();
//            }
//        });
//        // Create and show the AlertDialog
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }


//    //Daily Verse Random Images Pick from firebase
//    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//    private DatabaseReference databaseReference = firebaseDatabase.getReference();
//    private DatabaseReference imageUrl = databaseReference.child("DailyVerseImages");
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        imageUrl.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Random random = new Random();
//                    int index = random.nextInt((int) dataSnapshot.getChildrenCount());
//                    int count = 0;
//
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        if (count == index) {
//                            String link = snapshot.getValue(String.class);
//                            Glide.with(requireContext()).load(link).into(bannerImage);
//                            return;
//                        }
//                        count++;
//
//                    }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w("Failed to read value.", databaseError.toException());
//            }
//        });
//    }


    //Load verse for the day using daily manna Api
//    private void loadBibleVerse() {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
//        requestQueue.start();
//
//        String url = "https://beta.ourmanna.com/api/v1/get/?format=text";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    res = response;
//                    SharedPreferences sp = requireActivity().getSharedPreferences("verse_sp", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putString("verse", response);
//                    editor.apply();
//                } catch (StringIndexOutOfBoundsException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        String err = null;
//                        if (error instanceof com.android.volley.NoConnectionError) {
//                            err = "No internet Connection!";
//                        }
//                        try {
//                            if (!isEmpty(err)) {
//                                Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//
//        int sockettimeout = 3000;
//        RetryPolicy policy = new DefaultRetryPolicy(sockettimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(policy);
//        requestQueue.add(stringRequest);
//    }

}
