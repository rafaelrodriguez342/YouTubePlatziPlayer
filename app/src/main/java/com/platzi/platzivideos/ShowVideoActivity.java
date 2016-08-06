package com.platzi.platzivideos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.platzi.platzivideos.utils.Constants;
import com.platzi.platzivideos.utils.Utilities;

public class ShowVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,YouTubePlayer.PlaylistEventListener, YouTubePlayer.PlayerStateChangeListener {
    private YouTubePlayerView playerView;
    private static final int RETRY_CODE = 1;
    private int position=0;
    private YouTubePlayer myYoutubePlayer;
    private int currentTimeMilliseconds=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setTitle(getString(R.string.title_activity_show_video));
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        playerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        playerView.initialize(Constants.YOUTUBE_API_KEY, this);
        position= getIntent().getIntExtra(Constants.POSITION_EXTRA_KEY,0);
    }


    @Override
    protected void onResume(){
        super.onResume();
        if(myYoutubePlayer!=null) {
            Log.d("onResume",currentTimeMilliseconds+"");
            myYoutubePlayer.loadPlaylist(Constants.YOUTUBE_REQUEST_PLATZI_LIST_ID, position, currentTimeMilliseconds);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(myYoutubePlayer!=null){
            Log.d("onPause",""+myYoutubePlayer.getCurrentTimeMillis());
            currentTimeMilliseconds=myYoutubePlayer.getCurrentTimeMillis();
        }
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean isRestored) {
        myYoutubePlayer= youTubePlayer;
        if (!isRestored) {
            youTubePlayer.loadPlaylist(Constants.YOUTUBE_REQUEST_PLATZI_LIST_ID, position, currentTimeMilliseconds);
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
        Log.d("onVideoStarted",""+currentTimeMilliseconds);
        myYoutubePlayer.seekToMillis(currentTimeMilliseconds);
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
}
