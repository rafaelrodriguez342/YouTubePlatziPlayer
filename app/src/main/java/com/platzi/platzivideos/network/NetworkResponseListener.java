package com.platzi.platzivideos.network;

import org.json.JSONObject;

/**
 * Created by Rafael on 4/08/16.
 */
public interface NetworkResponseListener {
    public void onSuccessResponse(JSONObject response);
    public void onErrorResponse();
}
