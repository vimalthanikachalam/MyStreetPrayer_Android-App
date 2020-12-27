package com.mystreetprayer.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.mystreetprayer.app.alarmclock.adapter.KPC_NotificationAdapter;
import com.mystreetprayer.app.alarmclock.view.KPC_Notify_Firestore;

import java.util.Objects;

public class NotificationActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notificationRefrence = db.collection("NotificationView");

    private KPC_NotificationAdapter notificationAdapter;

    private ProgressBar notifyProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notifyProgress = (ProgressBar) findViewById(R.id.progress_notification);

//      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//      getSupportActionBar().setTitle("Notifications");

        Toolbar toolbar = (Toolbar) findViewById(R.id.notification_Toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        FloatingActionButton floatingActionButton = findViewById(R.id.create_new_notify);
        //Enable only for admin
        floatingActionButton.setVisibility(View.INVISIBLE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotificationActivity.this, CreateNotificationActivity.class));
            }
        });

        setUpRecyclerView();

    }


    //Implementing Recycler View
    private void setUpRecyclerView() {
        Query query = notificationRefrence;

        FirestoreRecyclerOptions<KPC_Notify_Firestore> options= new FirestoreRecyclerOptions.Builder<KPC_Notify_Firestore>()
                .setQuery(query, KPC_Notify_Firestore.class)
                .build();

        notificationRefrence.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    notifyProgress.setVisibility(View.GONE);
                }
            }
        });

        notificationAdapter = new KPC_NotificationAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.notification_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(notificationAdapter);


        notificationAdapter.setOnItemClickListner(new KPC_NotificationAdapter.OnItemClickListerner() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                KPC_Notify_Firestore nofificationList = documentSnapshot.toObject(KPC_Notify_Firestore.class);
//                String id = documentSnapshot.getId();
                String url = documentSnapshot.getString("webUrl");

                //Launch the Action to External Browser
                Intent webLink = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(webLink);
                Toast.makeText(NotificationActivity.this, "Opening", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        notificationAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notificationAdapter.stopListening();
    }

    //On Back Pressed
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
