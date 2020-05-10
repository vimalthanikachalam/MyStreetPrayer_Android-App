package com.mystreetprayer.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mystreetprayer.app.alarmclock.view.KPC_Notify_Firestore;

import java.util.Objects;

public class CreateNotificationActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription, editTextWebUrl,editTextImageUrl, editTextActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);

        editTextTitle = findViewById(R.id.create_notify_title);
        editTextDescription = findViewById(R.id.create_notify_description);
        editTextWebUrl = findViewById(R.id.create_notify_url);
        editTextImageUrl = findViewById(R.id.create_image_url);
        editTextActionButton = findViewById(R.id.create_action_btn);

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
        menuInflater.inflate(R.menu.notification_save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_notify:
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
        String imageUrl = editTextImageUrl.getText().toString();
        String actionButton = editTextActionButton.getText().toString();

        if(title.trim().isEmpty() || descriptiom.trim().isEmpty() || webUrl.trim().isEmpty()){
            Toast.makeText(this, "Input value cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection("NotificationView");

        notebookRef.add(new KPC_Notify_Firestore(title, descriptiom, webUrl, imageUrl,actionButton));
        Toast.makeText(this, "Notification Added", Toast.LENGTH_SHORT).show();
        finish();

    }
}

