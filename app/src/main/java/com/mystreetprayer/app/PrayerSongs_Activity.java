package com.mystreetprayer.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Objects;

public class PrayerSongs_Activity extends AppCompatActivity {




    private boolean checkPermission = false;
    Uri uri;
    String songName, songUrl;
    ListView listView;
    DatabaseReference databaseReference;

    ArrayList<String> arrayListSongsName = new ArrayList<>();
    ArrayList<String> arrayListSongsUrl = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    JcPlayerView jcPlayerView;
    ArrayList<JcAudio> jcAudios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_songs);
        listView = findViewById(R.id.songListView);
        jcPlayerView = findViewById(R.id.jcplayer);

        retriveSongs();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jcPlayerView.playAudio(jcAudios.get(position));
                jcPlayerView.setVisibility(View.VISIBLE);
                jcPlayerView.createNotification(R.drawable.logo);
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Prayer Songs");

    }



    private void retriveSongs() {

        databaseReference = FirebaseDatabase.getInstance().getReference("PrayerSongs");
        databaseReference.keepSynced(true); //Check for Database updates in offline mode

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    SongsUtility songObj = ds.getValue(SongsUtility.class);
                    arrayListSongsName.add(songObj.getSongName());
                    arrayListSongsUrl.add(songObj.getSongUrl());
                    jcAudios.add(JcAudio.createFromURL(songObj.getSongName(), songObj.getSongUrl()));

                }
                arrayAdapter = new ArrayAdapter<String>(PrayerSongs_Activity.this, android.R.layout.simple_list_item_1, arrayListSongsName){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setSingleLine(true);
                        textView.setTextSize(18);
                        textView.setMaxLines(1);

                        return view;
                    }
                };
                jcPlayerView.initPlaylist(jcAudios, null);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//Back
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Menu Upload Icon Inflater
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.songs_upload_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    Upload Songs Handler
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_upload){
            if(validatePermisiion()){
                pickSong();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void pickSong() {
        Intent intent_upload = new Intent();
        intent_upload.setType("audio/*");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent_upload, 1);
    }

    //Select the Song from LocalStorage and upload to Firebase.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1){
            if(resultCode == RESULT_OK){

                assert data != null;
                uri = data.getData();
                Cursor mcursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
                assert mcursor != null;
                int indexName = mcursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                mcursor.moveToFirst();
                songName = mcursor.getString(indexName);
                mcursor.close();

                uploadSongToFirebaseStorage();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //Uploading the Song from localStorage to firebaseStorage
    private void uploadSongToFirebaseStorage() {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("PrayerSongs").child(Objects.requireNonNull(uri.getLastPathSegment()));

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlSong = uriTask.getResult();
                assert urlSong != null;
                songUrl = urlSong.toString();

                uploadDetailstoDatabase();
                progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PrayerSongs_Activity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                int currentProgess = (int)progress;
                progressDialog.setMessage("Uploaded: " +currentProgess+ "%");

            }
        });

    }

    //Uploading the activity_register_time name and url to the firebase Realtime database
    private void uploadDetailstoDatabase() {

        SongsUtility songsUtility = new SongsUtility(songName, songUrl);

        FirebaseDatabase.getInstance().getReference("PrayerSongs")
                .push().setValue(songsUtility).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(PrayerSongs_Activity.this, "Song Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PrayerSongs_Activity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            };
        });
    }

    //User Permission Validate
    private Boolean validatePermisiion(){
        Dexter.withActivity(PrayerSongs_Activity.this)
        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        .withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
            checkPermission = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
            checkPermission = false;
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();

        return checkPermission;
    }
}
