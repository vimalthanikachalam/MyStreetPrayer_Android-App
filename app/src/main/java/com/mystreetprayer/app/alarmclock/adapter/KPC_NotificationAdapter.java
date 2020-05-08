package com.mystreetprayer.app.alarmclock.adapter;

import android.util.Log;
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
import com.mystreetprayer.app.alarmclock.view.KPC_Notify_Firestore;
import com.squareup.picasso.Picasso;

public class KPC_NotificationAdapter extends FirestoreRecyclerAdapter<KPC_Notify_Firestore, KPC_NotificationAdapter.NotificationHolder> {

    private OnItemClickListerner listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public KPC_NotificationAdapter(@NonNull FirestoreRecyclerOptions<KPC_Notify_Firestore> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NotificationHolder holder, int position, @NonNull KPC_Notify_Firestore model) {

        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDescription.setText(model.getDescription());
        holder.textViewPriority.setText(String.valueOf(model.getPriority()));
        holder.webViewUrl.setText(model.getWebUrl());
        holder.imageViewUrl.setText(model.getImageUrl());


        if(model.getActionBtn() != null && ! model.getActionBtn().isEmpty()){
            holder.actionButton.setText(model.getActionBtn());
        }else {
            holder.actionButton.setText(R.string.read_more);
        }

        if(model.getImageUrl() != null && ! model.getImageUrl().isEmpty()) {
            Picasso.get().load(getItem(position).getImageUrl()).error(R.drawable.verse_img_13)
                    .placeholder(R.drawable.ic_notify_default).into(holder.bannerImage);
        }
        else{
            Picasso.get().load(R.drawable.ic_notify_default).into(holder.bannerImage);
        }
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card,
                parent, false);
        return new NotificationHolder(view);

    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class NotificationHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewPriority;
        TextView webViewUrl;
        TextView imageViewUrl;
        TextView actionButton;
        ImageView bannerImage;


        NotificationHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            webViewUrl = itemView.findViewById(R.id.notification_web_url);
            imageViewUrl = itemView.findViewById(R.id.notification_image_url);
            actionButton = itemView.findViewById(R.id.action_button);
            bannerImage = itemView.findViewById(R.id.notify_image);


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
