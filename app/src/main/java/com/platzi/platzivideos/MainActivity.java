package com.platzi.platzivideos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.platzi.platzivideos.network.NetworkUtilities;
import com.platzi.platzivideos.network.VolleyHelper;
import com.platzi.platzivideos.model.PlayListPlatzi;
import com.platzi.platzivideos.utils.Constants;
import com.platzi.platzivideos.network.NetworkResponseListener;
import com.platzi.platzivideos.utils.RecyclerTouchListener;
import com.platzi.platzivideos.utils.Utilities;
import com.platzi.platzivideos.utils.VideosAdapter;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  implements NetworkResponseListener{

    private RecyclerView videoListView;
    private Context mContext;
    private VideosAdapter videosAdapter;
    private PlayListPlatzi playList;
    private VolleyHelper volleyHelper;
    private boolean isListAvailable =false;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeView();
        setListeners();
    }

    private void initializeView(){
        playList = new PlayListPlatzi();
        videoListView = (RecyclerView)findViewById(R.id.video_list);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.videos_list_refresh);
        mLayoutManager = new LinearLayoutManager(mContext);
        videoListView.setLayoutManager(mLayoutManager);
        volleyHelper = new VolleyHelper(this);
        requestPlayList();
    }

    private void setListeners(){
        videoListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                   int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (isListAvailable) {
                        if ((visibleItemCount + pastVisiblesItems) >= (totalItemCount - 3)) {
                            isListAvailable = false;
                            requestPlayList();
                        }
                    }
                }
            }
        });

        videoListView.addOnItemTouchListener(new RecyclerTouchListener(mContext, videoListView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(mContext, ShowVideoActivity.class);
                intent.putExtra(Constants.POSITION_EXTRA_KEY, position);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                playList.setNextPageToken("");
                requestPlayList();
            }
        });

    }

    private void reloadPlayListView(){
        isListAvailable =true;
        videosAdapter = new VideosAdapter(playList,mContext);
        if(videoListView.getAdapter()== null) {
            videoListView.setAdapter(videosAdapter);
        }else {
            videosAdapter.notifyItemInserted(playList.getItems().size() - 1);
        }
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
    }

    private void requestPlayList(){
        if(NetworkUtilities.isNetworkAvailable(mContext)) {
            volleyHelper.getRequest(mContext, playList.getNextPageToken());
        }else{
            Utilities.showErrorAlertMsg(mContext, R.string.alert_no_network_description_error);
        }

    }
    //Interface para Recycler view click Listener

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
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

    @Override
    public void onSuccessResponse(JSONObject response) {
    playList.addNewVideos(response);
    reloadPlayListView();
    }

    @Override
    public void onErrorResponse() {

    }
}
