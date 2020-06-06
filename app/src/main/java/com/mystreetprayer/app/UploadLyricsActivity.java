package com.mystreetprayer.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mystreetprayer.app.alarmclock.view.KPC_Lyrics_Firestore;
import com.mystreetprayer.app.alarmclock.view.KPC_Notify_Firestore;

import java.util.Objects;

public class UploadLyricsActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription, editTextWebUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_lyrics);

        editTextTitle = findViewById(R.id.create_lyrics_title);
        editTextDescription = findViewById(R.id.create_lyrics_description);
        editTextWebUrl = findViewById(R.id.create_lyrics_url);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Notification!");

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
        menuInflater.inflate(R.menu.lyrics_save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_lyrics:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String descriptiom = editTextDescription.getText().toString();
        String webUrl = editTextWebUrl.getText().toString();


        if(title.trim().isEmpty() || descriptiom.trim().isEmpty() || webUrl.trim().isEmpty()){
            Toast.makeText(this, "Input value cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection("Lyrics");

        notebookRef.add(new KPC_Lyrics_Firestore(title, descriptiom, webUrl));
        Toast.makeText(this, "Lyrics Uploaded", Toast.LENGTH_SHORT).show();
        finish();

    }
}

