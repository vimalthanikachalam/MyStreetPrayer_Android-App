package com.mystreetprayer.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
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
import com.mystreetprayer.app.alarmclock.adapter.KPC_VideoAdapter;
import com.mystreetprayer.app.alarmclock.view.KPC_Videos_Firestore;

import java.util.Objects;

public class VideosActivity extends AppCompatActivity {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference videoRefrence = firestore.collection("Videos");

    private KPC_VideoAdapter videosAdapter;
    private ProgressBar videoprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        videoprogress = (ProgressBar) findViewById(R.id.videos_progress);

        Toolbar toolbar = (Toolbar) findViewById(R.id.video_Toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.create_new_video);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VideosActivity.this, UploadVideos.class));
            }
        });

        setUpVideoView();

    }

    private void setUpVideoView() {
        Query query = videoRefrence.orderBy("sort", Query.Direction.DESCENDING);

        videoRefrence.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    videoprogress.setVisibility(View.GONE);
                }
            }
        });

        FirestoreRecyclerOptions<KPC_Videos_Firestore> options = new FirestoreRecyclerOptions.Builder<KPC_Videos_Firestore>()
                .setQuery(query, KPC_Videos_Firestore.class)
                .build();

        videosAdapter = new KPC_VideoAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.video_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(videosAdapter);


        videosAdapter.setOnItemClickListerVideo(new KPC_VideoAdapter.OnItemClickListerner() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                KPC_Videos_Firestore kpc_videos_firestore = documentSnapshot.toObject(KPC_Videos_Firestore.class);
                String videourl = documentSnapshot.getString("videoUrl");

                //Launch the Action in External Browser
                Intent webLink = new Intent(Intent.ACTION_VIEW, Uri.parse(videourl));
                startActivity(webLink);

                Toast.makeText(VideosActivity.this, "Playing Video : " + videourl, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        videosAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        videosAdapter.stopListening();
    }

    //On Back Pressed
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
