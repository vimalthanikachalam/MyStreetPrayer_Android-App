package com.mystreetprayer.app.alarmclock.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

public class EmptyRecyclerView extends RecyclerView {

    private View mEmptyView;
    private ImageView prayImageView;
    private final AdapterDataObserver mEmptyObserver = new EmptyRecyclerDataObserver();

    private Button setPrayerBtn;

    private LinearLayout onTimeSetbtn;

    private Callback mCallback;

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if(adapter != null) {
            adapter.registerAdapterDataObserver(mEmptyObserver);
        }

        mEmptyObserver.onChanged();
    }

    public void setEmptyView(View mEmptyView) {
        this.mEmptyView = mEmptyView;
    }

    public void setPrayImageView(ImageView prayImageView){
        this.prayImageView = prayImageView;
    }

    public void setPrayerReminder(Button setPrayerBtn){
        this.setPrayerBtn = setPrayerBtn;

    }

    public void onTimeSet(LinearLayout onTimeSetbtn){
        this.onTimeSetbtn = onTimeSetbtn;
    }

    private final class EmptyRecyclerDataObserver extends AdapterDataObserver {
        @Override
        public void onChanged() {
            Adapter<?> adapter =  getAdapter();
            if(adapter != null && mEmptyView != null && setPrayerBtn !=null && onTimeSetbtn !=null ) {
                if(adapter.getItemCount() == 0) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    setPrayerBtn.setVisibility(Button.VISIBLE);
                    onTimeSetbtn.setVisibility(Button.GONE);
                    prayImageView.setVisibility(ImageView.GONE);
                    EmptyRecyclerView.this.setVisibility(View.GONE);
                }
                else {
                    mEmptyView.setVisibility(View.GONE);
                    EmptyRecyclerView.this.setVisibility(View.VISIBLE);
                    setPrayerBtn.setVisibility(Button.GONE);
                    onTimeSetbtn.setVisibility(Button.VISIBLE);
                    prayImageView.setVisibility(ImageView.VISIBLE);
                    if(mCallback != null) mCallback.onEmpty();
                }
            }
        }
    }

    public interface Callback {
        void onEmpty();
    }

}