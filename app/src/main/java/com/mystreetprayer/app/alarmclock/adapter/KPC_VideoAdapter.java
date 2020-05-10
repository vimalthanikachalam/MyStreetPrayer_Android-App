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
import com.mystreetprayer.app.alarmclock.view.KPC_Videos_Firestore;
import com.squareup.picasso.Picasso;

public class KPC_VideoAdapter extends FirestoreRecyclerAdapter<KPC_Videos_Firestore, KPC_VideoAdapter.VideoHolder> {

    private OnItemClickListerner listerner;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public KPC_VideoAdapter(@NonNull FirestoreRecyclerOptions<KPC_Videos_Firestore> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull KPC_VideoAdapter.VideoHolder holder, int position, @NonNull KPC_Videos_Firestore model) {
        holder.textViewTitle.setText(model.getVideoTitle());
        holder.textViewDescription.setText(model.getVideoDescription());
        holder.textViewwebViewUrl.setText(model.getVideoUrl());
        holder.textViewimageViewUrl.setText(model.getVideoImage());
        holder.textViewSort.setText(String.valueOf(model.getSort()));

        if(model.getVideoImage() != null && ! model.getVideoImage().isEmpty()){
            Picasso.get().load(getItem(position).getVideoImage()).error(R.drawable.video_error)
                    .placeholder(R.drawable.video_placeholder).into(holder.imageViewBnnerImage);
        }else{
            Picasso.get().load(R.drawable.video_default).into(holder.imageViewBnnerImage);
        }


    }

    @NonNull
    @Override
    public KPC_VideoAdapter.VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videos_card,
                parent, false);
        return new VideoHolder(view);


    }

    class VideoHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewwebViewUrl;
        TextView textViewimageViewUrl;
        TextView textViewSort;
        ImageView imageViewBnnerImage;



         VideoHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.video_view_title);
            textViewDescription = itemView.findViewById(R.id.video_view_description);
            textViewwebViewUrl = itemView.findViewById(R.id.video_web_url);
            textViewimageViewUrl = itemView.findViewById(R.id.video_image_url);
            imageViewBnnerImage = itemView.findViewById(R.id.video_image);
            textViewSort = itemView.findViewById(R.id.video_sort_order);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listerner !=null){
                        listerner.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListerner{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListerVideo(OnItemClickListerner listerner) {
        this.listerner = listerner;
    }
}
