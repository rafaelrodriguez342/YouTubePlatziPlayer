package com.platzi.platzivideos.network;

import android.content.Context;
import android.net.Uri;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.platzi.platzivideos.R;
import com.platzi.platzivideos.utils.Constants;
import com.platzi.platzivideos.utils.Utilities;

import org.json.JSONObject;

/**
 * Created by Rafael on 4/08/16.
 */
public class VolleyHelper {
NetworkResponseListener customNetworkInterface;

    public VolleyHelper(NetworkResponseListener customNetworkInterface){
    this.customNetworkInterface=customNetworkInterface;
    }
    public void getRequest(final Context context, String pageToken){
        RequestQueue request = Volley.newRequestQueue(context);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("www.googleapis.com")
                .appendPath("youtube")
                .appendPath("v3")
                .appendPath("playlistItems")
                .appendQueryParameter("part", Constants.YOUTUBE_REQUEST_RESPONSE_TYPE)
                .appendQueryParameter("maxResults", Constants.YOUTUBE_REQUEST_MAX_RESULTS)
                .appendQueryParameter("key", Constants.YOUTUBE_API_KEY)
                .appendQueryParameter("fields", Constants.YOUTUBE_REQUEST_FIELDS)
                .appendQueryParameter("playlistId", Constants.YOUTUBE_REQUEST_PLATZI_LIST_ID)
                .appendQueryParameter("pageToken", pageToken);
        String myUrl = builder.build().toString();
        JsonObjectRequest jRequest = new JsonObjectRequest(myUrl,null,getCustomListener(),getErrorListener(context) ){


            };

        request.add(jRequest);
   }

    private Response.ErrorListener getErrorListener(final Context context){
        Response.ErrorListener errorListener;

            errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Utilities.showErrorAlertMsg(context, R.string.alert_api_description_error);
                    customNetworkInterface.onErrorResponse();
                }
            };

        return errorListener;
    }



    private Response.Listener<JSONObject> getCustomListener(){

        Response.Listener<JSONObject> listener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            customNetworkInterface.onSuccessResponse(response);
            }
        };

        return listener;
    }
}
