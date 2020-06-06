package com.mystreetprayer.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mystreetprayer.app.alarmclock.view.KPC_Videos_Firestore;

import java.util.Objects;

public class UploadVideos extends AppCompatActivity {

    private EditText videoTitle, videoDescription, videoWebUrl, videoImageUrl, videoSortOder, videoType;
    private TextView videoCount;
    private int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_videos);

        videoTitle = findViewById(R.id.video_create_title);
        videoDescription = findViewById(R.id.video_create_description);
        videoWebUrl = findViewById(R.id.video_create_link);
        videoImageUrl = findViewById(R.id.video_create_imageurl);
        videoSortOder = findViewById(R.id.video_sort_input);
        videoCount = findViewById(R.id.videos_count);
        videoType = findViewById(R.id.video_type);



        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upload Video!");

        //Method Bind
        videoCounter();

    }

    //Back
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.notification_save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_notify) {
            saveVideoContent();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void saveVideoContent() {
        String title = videoTitle.getText().toString();
        String description = videoDescription.getText().toString();
        String webUrl = videoWebUrl.getText().toString();
        String imageUrl = videoImageUrl.getText().toString();
        String video_view_type = videoType.getText().toString();
        int sortOrder = Integer.parseInt(videoSortOder.getText().toString());

        if (title.trim().isEmpty() || description.trim().isEmpty() || webUrl.trim().isEmpty() || video_view_type.trim().isEmpty()) {
            Toast.makeText(this, "Input value cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        CollectionReference videoRef = FirebaseFirestore.getInstance()
                .collection("Videos");


        videoRef.add(new KPC_Videos_Firestore(title, description, webUrl, imageUrl, video_view_type, sortOrder));

        Toast.makeText(this, "Video Updated", Toast.LENGTH_SHORT).show();
        finish();


    }

    private void videoCounter(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Videos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    count = Objects.requireNonNull(task.getResult()).size();
                    videoCount.setText(String.valueOf(count));
                }else {
                    videoCount.setText((CharSequence) task.getException());
                    Log.d("Users", "Error getting value"+ task.getException());
                }
            }
        });
    }
}

