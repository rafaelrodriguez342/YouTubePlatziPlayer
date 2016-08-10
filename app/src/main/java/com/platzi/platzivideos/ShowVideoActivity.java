package com.platzi.platzivideos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.platzi.platzivideos.model.PlayListPlatzi;
import com.platzi.platzivideos.model.VideoPlatzi;
import com.platzi.platzivideos.model.VideoState;
import com.platzi.platzivideos.utils.Constants;
import com.platzi.platzivideos.utils.Utilities;

public class ShowVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,YouTubePlayer.PlaylistEventListener, YouTubePlayer.PlayerStateChangeListener {
    private YouTubePlayerView playerView;
    private static final int RETRY_CODE = 1;
    private int position=0;
    private YouTubePlayer myYoutubePlayer;
    PlayListPlatzi lista;
    private int currentTimeMilliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        configureActionBar();
        playerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        playerView.initialize(Constants.YOUTUBE_API_KEY, this);
        position= getIntent().getIntExtra(Constants.POSITION_EXTRA_KEY, 0);
        lista=((ApplicationClass) getApplication()).playListPlatzi;
    }

    private void configureActionBar(){
        getActionBar().setTitle(getString(R.string.title_activity_show_video));
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onResume(){
        super.onResume();
        if(myYoutubePlayer!=null) {
            loadUserPlayList(myYoutubePlayer);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(myYoutubePlayer!=null){
            Log.d("onPause", "" + myYoutubePlayer.getCurrentTimeMillis());
            saveCurrentState();
        }
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean isRestored) {
        myYoutubePlayer= youTubePlayer;
        if (!isRestored) {
            loadUserPlayList(youTubePlayer);
            youTubePlayer.setPlaylistEventListener(this);
            youTubePlayer.setPlayerStateChangeListener(this);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RETRY_CODE).show();
        }
    }

    private void loadUserPlayList(YouTubePlayer youTubePlayer){
        VideoPlatzi videoPlatzi = lista.getItems().get(position);
        VideoState videoState = videoPlatzi.getVideoState(getApplicationContext());
        if(videoState==null) {
            youTubePlayer.loadPlaylist(Constants.YOUTUBE_REQUEST_PLATZI_LIST_ID, position, 0);
        }else{
        showConfirmDialog(videoState, youTubePlayer);
        }
    }

    private void saveCurrentState(){
        int initialPosition= getIntent().getIntExtra(Constants.POSITION_EXTRA_KEY, 0);
        VideoPlatzi videoPlatzi = lista.getItems().get(initialPosition);
        String currentIdVideo= position!=initialPosition?lista.getItems().get(position).getId():"";
        VideoState videoState= new VideoState(currentIdVideo,myYoutubePlayer.getCurrentTimeMillis());
        videoPlatzi.saveVideoState(videoState,getApplicationContext());
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RETRY_CODE) {
            playerView.initialize(Constants.YOUTUBE_API_KEY, this);
        }
    }

    //PlayList event listener methods

    @Override
    public void onPrevious() {
        position--;
    }

    @Override
    public void onNext() {
        position++;
    }

    @Override
    public void onPlaylistEnded() {

    }

//Player state change Listener methods

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {
        if(myYoutubePlayer.getCurrentTimeMillis()<currentTimeMilliseconds) {
            myYoutubePlayer.seekToMillis(currentTimeMilliseconds);
        }
    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {
        Utilities.showErrorAlertMsg(this, errorReason.toString());
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showConfirmDialog(final VideoState videoState,final YouTubePlayer youTubePlayer){
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.alert_notification_title))
                .setMessage(getString(R.string.alert_wish_to_continue))
                .setPositiveButton(getString(R.string.alert_positive_answer), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!videoState.getCurrentVideo().equals("")){
                            position=lista.getPositionById(videoState.getCurrentVideo());
                        }
                        currentTimeMilliseconds=videoState.getCurrentTime();
                        youTubePlayer.loadPlaylist(Constants.YOUTUBE_REQUEST_PLATZI_LIST_ID, position, videoState.getCurrentTime());
                    }

                })
                .setNegativeButton(getString(R.string.alert_negative_answer), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        youTubePlayer.loadPlaylist(Constants.YOUTUBE_REQUEST_PLATZI_LIST_ID, position, 0);
                        currentTimeMilliseconds=0;
                    }
                })
                .show();
    }
}
