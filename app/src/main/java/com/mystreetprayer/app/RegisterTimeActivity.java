package com.mystreetprayer.app;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

import io.opencensus.tags.Tag;


public class RegisterTimeActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    public static final String TAG = "TAG";
    EditText username, useremail, userphone, useraddress, userchurch, userpastorname, usermessage;
    Button mTimePicker, getPrayerData;
    TimePickerDialog.OnTimeSetListener mOnTimeSetListener;
    DatabaseReference databaseReference;
    PrayerTimeData prayerTimeData;
    Spinner registerSpinner;
    private String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_time);



        //Get Register ID's
        username = (EditText) findViewById(R.id.user_name);
        useremail = (EditText) findViewById(R.id.user_email);
        userphone = (EditText) findViewById(R.id.user_phone);
        useraddress = (EditText) findViewById(R.id.user_address);
        userchurch = (EditText) findViewById(R.id.user_churchname);
        userpastorname = (EditText) findViewById(R.id.user_pastorname);
        usermessage = (EditText) findViewById(R.id.user_message);
        getPrayerData = (Button) findViewById(R.id.get_register_data);
        prayerTimeData = new PrayerTimeData();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserPrayerTime");




        getPrayerData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = username.getText().toString().trim();
                final String email = useremail.getText().toString().trim();
                final String phone = userphone.getText().toString().trim();
                final String address = useraddress.getText().toString().trim();
                final String church = userchurch.getText().toString().trim();
                final String pastorname = userpastorname.getText().toString().trim();
                final String message = usermessage.getText().toString().trim();
                final String registerdtime = mTimePicker.getText().toString().trim();
                final String registerddays = registerSpinner.getSelectedItem().toString().trim();
                final String usergender = gender;

                if(TextUtils.isEmpty(name)){
                    username.setError("Name is Required.");
                    Toast.makeText(RegisterTimeActivity.this, "Name is Required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    userphone.setError("Mobile No is Required.");
                    Toast.makeText(RegisterTimeActivity.this, "Mobile No is Required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(address)){
                    useraddress.setError("Address is Required.");
                    Toast.makeText(RegisterTimeActivity.this, "Address is Required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(church)){
                    userchurch.setError("Church Name is Required.");
                    Toast.makeText(RegisterTimeActivity.this, "Church Name is Required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(pastorname)){
                    userpastorname.setError("Pastor Name is Required.");
                    Toast.makeText(RegisterTimeActivity.this, "Pastor Name is Required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(registerdtime)){
                    mTimePicker.setError("Please Set Prayer Time");
                    Toast.makeText(RegisterTimeActivity.this, "Please Set Prayer Time", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(RegisterTimeActivity.this, "Successfully Registered!", Toast.LENGTH_LONG).show();
                        prayerTimeData.setUsernameData(username.getText().toString().trim());
                        prayerTimeData.setUseremailData(useremail.getText().toString().trim());
                        prayerTimeData.setUserphoneData(userphone.getText().toString().trim());
                        prayerTimeData.setUseraddressData(useraddress.getText().toString().trim());
                        prayerTimeData.setUserchurchData(userchurch.getText().toString().trim());
                        prayerTimeData.setUserpastornameData(userpastorname.getText().toString().trim());
                        prayerTimeData.setUsermessageData(usermessage.getText().toString().trim());
                        prayerTimeData.setUserTimeData(mTimePicker.getText().toString().trim());
                        prayerTimeData.setUserpraydaysData(registerSpinner.getSelectedItem().toString().trim());
                        prayerTimeData.setUsergenderData(gender);
                        databaseReference.push().setValue(prayerTimeData);



//                    databaseReference.push().setValue(prayerTimeData).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.d(TAG, "onSuccess: Registration Successful! "+ username);
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d(TAG, "onFailure: " + e.toString());
//
//                        }
//                    });
                }
            }
        });

        //User Time Picker
        mTimePicker = findViewById(R.id.user_Time_Picker);
        mTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCalender = Calendar.getInstance();
                int hour = mCalender.get(Calendar.HOUR_OF_DAY);
                int minutes = mCalender.get(Calendar.MINUTE);

                TimePickerDialog mTimepickerDialog = new TimePickerDialog(RegisterTimeActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mOnTimeSetListener, hour, minutes, false);

                Objects.requireNonNull(mTimepickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mTimepickerDialog.show();
            }
        });

        mOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timeSet = "";
                if (hourOfDay > 12) {
                    hourOfDay -= 12;
                    timeSet = "PM";
                } else if (hourOfDay == 0) {
                    hourOfDay += 12;
                    timeSet = "AM";
                } else if (hourOfDay == 12){
                    timeSet = "PM";
                }else{
                    timeSet = "AM";
                }
                String min = "";
                if (minute < 10)
                    min = "0" + minute ;
                else
                    min = String.valueOf(minute);

                // Append in a StringBuilder
                String registerdTime = new StringBuilder().append(hourOfDay).append(':')
                        .append(min ).append(" ").append(timeSet).toString();
                mTimePicker.setText(registerdTime);
                Toast.makeText(RegisterTimeActivity.this, "Pray for 1 hour from "+registerdTime, Toast.LENGTH_SHORT).show();
            }
        };


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register Prayer Time");

        registerSpinner = (Spinner) findViewById(R.id.time_spinner);

        ArrayAdapter<String> regiterAdapter = new ArrayAdapter<String>(RegisterTimeActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.spinner_register_time));
        regiterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        registerSpinner.setAdapter(regiterAdapter);
        registerSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position == 0){

            onNothingSelected(parent);
        }else {
            String userRegisterTime = parent.getItemAtPosition(position).toString();
            Toast.makeText(this, userRegisterTime, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//Back
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    gender = "Male";
                    break;
            case R.id.radio_female:
                if (checked)
                    gender = "Female";
                    break;
        }
    }

}


