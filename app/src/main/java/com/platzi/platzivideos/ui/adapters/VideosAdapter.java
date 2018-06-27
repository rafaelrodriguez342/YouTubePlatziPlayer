package com.platzi.platzivideos.ui.adapters;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.platzi.platzivideos.R;
import com.platzi.platzivideos.model.Video;
import com.platzi.platzivideos.model.retrofit.VideoDTO;
import com.squareup.picasso.Picasso;


/**
 * Created by Rafael on 3/08/16.
 */
public class VideosAdapter extends PagedListAdapter<Video, VideosAdapter.VideoViewHolder> {
    private ClickListener clickListener;

    public VideosAdapter(ClickListener clickListener) {
        super(DIFF_CALLBACK);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_video, parent, false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        final Video video = getItem(position);
        if (video != null) {
            holder.itemView.setOnClickListener(view -> clickListener.onClick(video, holder.itemView.getContext()));
            holder.title.setText(video.getTitle());
            Picasso.get()
                    .load(video.getUrlImage())
                    .centerInside()
                    .fit()
                    .into(holder.imgThumbnail);
        }
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imgThumbnail;
        TextView title;

        VideoViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv);
            imgThumbnail = itemView.findViewById(R.id.thumbnail_img);
            title = itemView.findViewById(R.id.title_video);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private static final DiffUtil.ItemCallback<Video> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Video>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Video oldVideo, @NonNull Video newVideo) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldVideo.getId().equals(newVideo.getId());
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull Video oldVideo, @NonNull Video newVideo) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldVideo.equals(newVideo);
                }
            };

    public interface ClickListener {
        void onClick(Video video, Context context);
    }
}
