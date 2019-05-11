package com.platzi.platzivideos.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.platzi.platzivideos.R;
import com.platzi.platzivideos.ui.adapters.VideosAdapter;
import com.platzi.platzivideos.viewModels.GalleryViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * GalleryActivity containing the Video list.
 */
public class GalleryActivity extends DaggerAppCompatActivity {

    @Inject
    Context applicationContext;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private VideosAdapter videosAdapter;
    private GalleryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GalleryViewModel.class);
        observeLiveData();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeView();
    }

    private void initializeView() {
        RecyclerView galleryRecyclerView = findViewById(R.id.video_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(applicationContext);
        galleryRecyclerView.setLayoutManager(mLayoutManager);
        videosAdapter = new VideosAdapter((video, context) -> {
            Intent intent = VideoDetailActivity.createNewIntent(context, video);
            context.startActivity(intent);
        });
        galleryRecyclerView.setAdapter(videosAdapter);
    }

    private void observeLiveData() {
        viewModel.getVideoListLiveData().observe(this, platziList -> {
            if (platziList != null) {
                videosAdapter.submitList(platziList);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
