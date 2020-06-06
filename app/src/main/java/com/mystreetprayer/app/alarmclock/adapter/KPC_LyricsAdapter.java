package com.mystreetprayer.app.alarmclock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mystreetprayer.app.R;
import com.mystreetprayer.app.alarmclock.view.KPC_Lyrics_Firestore;
import com.mystreetprayer.app.alarmclock.view.KPC_Notify_Firestore;
import com.squareup.picasso.Picasso;

public class KPC_LyricsAdapter extends FirestoreRecyclerAdapter<KPC_Lyrics_Firestore, KPC_LyricsAdapter.LyricsHolder> {

    private OnItemClickListerner listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public KPC_LyricsAdapter(@NonNull FirestoreRecyclerOptions<KPC_Lyrics_Firestore> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull LyricsHolder holder, int position, @NonNull KPC_Lyrics_Firestore model) {

        holder.lyricsViewTitle.setText(model.getLyrics_title());
        holder.lyricsViewDescription.setText(model.getLyrics_description());
        holder.lyricsViewUrl.setText(model.getLyrics_webUrl());

    }

    @NonNull
    @Override
    public LyricsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyrics_card,
                parent, false);
        return new LyricsHolder(view);
    }




    class LyricsHolder extends RecyclerView.ViewHolder {

        TextView lyricsViewTitle;
        TextView lyricsViewDescription;
        TextView lyricsViewUrl;


        LyricsHolder(@NonNull View itemView) {
            super(itemView);

            lyricsViewTitle = itemView.findViewById(R.id.lyrics_view_title);
            lyricsViewDescription = itemView.findViewById(R.id.lyrics_view_description);
            lyricsViewUrl = itemView.findViewById(R.id.lyrics_web_url);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListerner{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListner(OnItemClickListerner listener){
        this.listener = listener;
    }

}
