package com.platzi.platzivideos.utils;

import android.app.AlertDialog;
import android.content.Context;

import com.platzi.platzivideos.R;

/**
 * Created by Rafael on 6/08/16.
 */
public class Utilities {

    public static void showErrorAlertMsg(Context context, String msj) {
        new AlertDialog.Builder(context).setTitle(context.getString(R.string.alert_title_error)).setMessage(msj).setPositiveButton("ok", null).show();
    }
}
