package com.platzi.platzivideos.utils;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.platzi.platzivideos.R;
import com.platzi.platzivideos.model.PlayListPlatzi;
import com.platzi.platzivideos.model.VideoPlatzi;
import com.squareup.picasso.Picasso;


/**
 * Created by Rafael on 3/08/16.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {

    private PlayListPlatzi playListPlatzi;
    private Context context;

    public VideosAdapter(PlayListPlatzi playListPlatzi,Context context){
        this.playListPlatzi=playListPlatzi;
        this.context=context;

    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_video, parent, false);
        VideoViewHolder videoViewHolder = new VideoViewHolder(v);
        return videoViewHolder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        VideoPlatzi video = playListPlatzi.getItems().get(position);
        holder.title.setText(video.getTitle());

        Picasso.with(context)
                .load(video.getUrlImage())
                .centerInside()
                .fit()
                .into(holder.imgThumbnail);
    }

    @Override
    public int getItemCount() {
        return playListPlatzi.getItems().size();
    }

    public static  class VideoViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imgThumbnail;
        TextView title;

        VideoViewHolder(View itemView){
            super(itemView);
            cardView =(CardView) itemView.findViewById(R.id.cv);
            imgThumbnail=(ImageView) itemView.findViewById(R.id.thumbnail_img) ;
            title= (TextView) itemView.findViewById(R.id.title_video);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
