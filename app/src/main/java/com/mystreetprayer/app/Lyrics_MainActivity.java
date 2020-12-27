package com.mystreetprayer.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mystreetprayer.app.alarmclock.adapter.KPC_LyricsAdapter;
import com.mystreetprayer.app.alarmclock.view.KPC_Lyrics_Firestore;
import com.mystreetprayer.app.alarmclock.view.KPC_Notify_Firestore;


import java.util.Objects;

public class Lyrics_MainActivity extends AppCompatActivity {

    private KPC_LyricsAdapter lyricsAdapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference lyricsRefrence = db.collection("Lyrics");

    private ProgressBar sermonsProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics__main);

        sermonsProgress = (ProgressBar) findViewById(R.id.progress_sermons);

        FloatingActionButton floatingActionButton = findViewById(R.id.create_new_lyrics);
        //Enable only for admin
        floatingActionButton.setVisibility(View.INVISIBLE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Lyrics_MainActivity.this, UploadLyricsActivity.class));
            }
        });

        lyricsRecyclerView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.sermons_Toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

    }


    //Implementing Recycler View
    private void lyricsRecyclerView() {

            Query query = lyricsRefrence;

        FirestoreRecyclerOptions<KPC_Lyrics_Firestore> options = new FirestoreRecyclerOptions.Builder<KPC_Lyrics_Firestore>()
                .setQuery(query, KPC_Lyrics_Firestore.class)
                .build();


        lyricsAdapter = new KPC_LyricsAdapter(options);

        lyricsRefrence.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        sermonsProgress.setVisibility(View.GONE);
                    }
                }
            });


        RecyclerView recyclerView = findViewById(R.id.sermons_recycler_view);
            recyclerView.setHasFixedSize(true);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(lyricsAdapter);

            lyricsAdapter.setOnItemClickListner(new KPC_LyricsAdapter.OnItemClickListerner() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                KPC_Notify_Firestore nofificationList = documentSnapshot.toObject(KPC_Notify_Firestore.class);
//
//              String id = documentSnapshot.getId();
                String url = documentSnapshot.getString("lyrics_webUrl");

                Intent intent = new Intent(getApplicationContext(), PrayerSongWeb.class);
                intent.putExtra("url", url);
                startActivity(intent);

                Toast.makeText(Lyrics_MainActivity.this, "Opening" , Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        lyricsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lyricsAdapter.stopListening();
    }

    //On Back Pressed
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

