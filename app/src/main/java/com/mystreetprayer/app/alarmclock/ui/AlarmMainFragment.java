package com.mystreetprayer.app.alarmclock.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mystreetprayer.app.MainActivity;
import com.mystreetprayer.app.R;
import com.mystreetprayer.app.alarmclock.adapter.AlarmsAdapter;
import com.mystreetprayer.app.alarmclock.model.Alarm;
import com.mystreetprayer.app.alarmclock.service.LoadAlarmsReceiver;
import com.mystreetprayer.app.alarmclock.service.LoadAlarmsService;
import com.mystreetprayer.app.alarmclock.util.AlarmUtils;
import com.mystreetprayer.app.alarmclock.view.DividerItemDecoration;
import com.mystreetprayer.app.alarmclock.view.EmptyRecyclerView;

import java.util.ArrayList;

import static com.mystreetprayer.app.alarmclock.ui.AddEditAlarmActivity.ADD_ALARM;
import static com.mystreetprayer.app.alarmclock.ui.AddEditAlarmActivity.buildAddEditAlarmActivityIntent;

public final class AlarmMainFragment extends Fragment
        implements LoadAlarmsReceiver.OnAlarmsLoadedListener {

    private LoadAlarmsReceiver mReceiver;
    private AlarmsAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceiver = new LoadAlarmsReceiver(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.alarm_fragment_main, container, false);

        final EmptyRecyclerView rv = v.findViewById(R.id.recycler);
        mAdapter = new AlarmsAdapter();
        rv.setEmptyView(v.findViewById(R.id.alarm_emptystate));
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new DividerItemDecoration(getContext()));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setPrayerReminder(v.findViewById(R.id.prayer_reminder));
        rv.onTimeSet(v.findViewById(R.id.successVisible));
        rv.setPrayImageView(v.findViewById(R.id.alarm_visiblestate));


        //Set Prayer Time
        Button setprayerTime = v.findViewById(R.id.prayer_reminder);
        setprayerTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmUtils.checkAlarmPermissions(getActivity());
                final Intent prayerIntent = buildAddEditAlarmActivityIntent(getContext(), ADD_ALARM);
                startActivity(prayerIntent);
            }
        });

        //Prayer Time On Set Close the Activity
        Button finishPrayerTime = v.findViewById(R.id.prayer_set_done);
        finishPrayerTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prayerIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(prayerIntent);
            }
        });

        getActivity().setTitle("Set Your Prayer Time");

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        final IntentFilter filter = new IntentFilter(LoadAlarmsService.ACTION_COMPLETE);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, filter);
        LoadAlarmsService.launchLoadAlarmsService(getContext());
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mReceiver);
    }

    @Override
    public void onAlarmsLoaded(ArrayList<Alarm> alarms) {
        mAdapter.setAlarms(alarms);
    }

}
