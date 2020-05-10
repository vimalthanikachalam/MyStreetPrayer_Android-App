package com.mystreetprayer.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mystreetprayer.app.alarmclock.ui.AlarmMainActivity;

import java.util.Objects;


public class Fragment_Profile extends Fragment {

    private SharedPreferences sharedPreferences;
    private LinearLayout visitWebsite, prayerTimeProfile;
    private String feedbackUrl = "https://play.google.com/store/apps/details?id=com.mystreetprayer.app";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView userNameProfile = (TextView) rootView.findViewById(R.id.username_profile);

        visitWebsite = (LinearLayout) rootView.findViewById(R.id.visitWebsite);
        prayerTimeProfile = (LinearLayout) rootView.findViewById(R.id.profile_Prayer_Time);


        LinearLayout whatsApp = (LinearLayout) rootView.findViewById(R.id.profile_whatsapp);
        Button logOutbtn = (Button) rootView.findViewById(R.id.logout);


        //Save UserData in Local
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String getUSerName = sharedPreferences.getString("username", "");

        userNameProfile.setText(getUSerName);

        //MethodCalls
        firestoreData();
        visitOurWebsite();
        prayerTime();



        //Contact us on WhatsApp
        whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactUs();
            }
        });

        //Logout Method
        logOutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                Toast.makeText(getContext(), "Logout Successful!", Toast.LENGTH_SHORT).show();
            }
        });

        //App Feedback
        LinearLayout feedback = (LinearLayout) rootView.findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedbackIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(feedbackUrl));
                startActivity(feedbackIntent);
            }
        });

        //Share the App
        LinearLayout sharetheApp = (LinearLayout) rootView.findViewById(R.id.share_app);
        sharetheApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, "Karnataka Prayer Call 24/7 APP.\n"
                        +"\n"
                        + "Be the first to receive updates from KPC APP. \n"
                        + "\n"
                        + "Download the App Now!  \n"
                        + "https://play.google.com/store/apps/details?id="
                        + BuildConfig.APPLICATION_ID);
                startActivity(Intent.createChooser(share, "Share"));
            }
        });

        return rootView;
    }

    private void contactUs() {
        String contact = "+91 9341231121"; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = requireActivity().getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(getActivity(), "WhatsApp app not installed in your Phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void prayerTime() {
        prayerTimeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prayerIntent = new Intent(getActivity(), AlarmMainActivity.class);
                startActivity(prayerIntent);
            }
        });
    }

    private void visitOurWebsite() {
        visitWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://mystreetprayer.com");
                Intent visitWebsiteIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(visitWebsiteIntent);
            }
        });
    }

    private void firestoreData() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        firestore.collection("users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
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

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("Profile");
    }
}
