package com.platzi.platzivideos.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import com.platzi.platzivideos.R;
import com.platzi.platzivideos.model.Video;
import com.platzi.platzivideos.model.VideoInfo;
import com.platzi.platzivideos.utils.Constants;
import com.platzi.platzivideos.utils.DialogHelper;
import com.platzi.platzivideos.viewModels.VideoDetailViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Activity for Display a specific Video.
 */
public class VideoDetailActivity extends DaggerAppCompatActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaylistEventListener, YouTubePlayer.PlayerStateChangeListener {
    private static final String VIDEO_EXTRA_KEY = "videoExtraKey";
    private static final int RETRY_CODE = 1001;
    private YouTubePlayer myYoutubePlayer;
    private int currentTimeMilliseconds;
    private VideoDetailViewModel viewModel;
    private YouTubePlayerSupportFragment youTubePlayerFragment;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoDetailViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        configureActionBar();
        youTubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(Constants.YOUTUBE_API_KEY, this);
        viewModel.initWithVideo(getIntent().getParcelableExtra(VIDEO_EXTRA_KEY));
        observeLiveData();
    }

    public static Intent createNewIntent(Context context, Video video) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra(VIDEO_EXTRA_KEY, video);
        return intent;
    }

    private void configureActionBar() {
        if (getActionBar() != null) {
            getActionBar().setTitle(getString(R.string.title_activity_show_video));
            getActionBar().setDisplayShowHomeEnabled(false);
            getActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.reloadVideo();
    }

    private void observeLiveData() {
        viewModel.getVideoInfoResultLiveData().observe(this, videoState -> {
            if (videoState != null) {
                loadUserVideo(videoState);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveCurrentState();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean isRestored) {
        myYoutubePlayer = youTubePlayer;
        if (!isRestored) {
            viewModel.reloadVideo();
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

    private void loadUserVideo(VideoInfo videoInfo) {
        if (myYoutubePlayer != null) {
            if (videoInfo.getVideoState() == null) {
                myYoutubePlayer.loadVideo(videoInfo.getVideo().getId(), 0);
            } else {
                showConfirmDialog(videoInfo, myYoutubePlayer);
            }
        }
    }

    private void saveCurrentState() {
        if (myYoutubePlayer != null) {
            viewModel.saveVideoState(myYoutubePlayer.getCurrentTimeMillis());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RETRY_CODE) {
            youTubePlayerFragment.initialize(Constants.YOUTUBE_API_KEY, this);
        }
    }

    //PlayList event listener methods

    @Override
    public void onPrevious() {

    }

    @Override
    public void onNext() {

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
        if (myYoutubePlayer.getCurrentTimeMillis() < currentTimeMilliseconds) {
            myYoutubePlayer.seekToMillis(currentTimeMilliseconds);
        }
    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {
        DialogHelper.showErrorAlertMsg(this, errorReason.toString());
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

    public void showConfirmDialog(final VideoInfo videoInfo, final YouTubePlayer youTubePlayer) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.alert_notification_title))
                .setMessage(getString(R.string.alert_wish_to_continue))
                .setPositiveButton(getString(R.string.alert_positive_answer), (dialog, which) -> {
                    currentTimeMilliseconds = videoInfo.getVideoState().getCurrentTime();
                    youTubePlayer.loadVideo(videoInfo.getVideo().getId(), currentTimeMilliseconds);
                })
                .setNegativeButton(getString(R.string.alert_negative_answer), (dialog, which) -> {
                    youTubePlayer.loadVideo(videoInfo.getVideo().getId(), 0);
                    currentTimeMilliseconds = 0;
                })
                .show();
    }
}
