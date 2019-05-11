package com.platzi.platzivideos.utils;

import android.app.AlertDialog;
import android.content.Context;

import com.platzi.platzivideos.R;

/**
 * class for common and reusable Dialog methods.
 */
public class DialogHelper {

    public static void showErrorAlertMsg(Context context, String msj) {
        new AlertDialog.Builder(context).setTitle(context.getString(R.string.alert_title_error)).setMessage(msj).setPositiveButton("ok", null).show();
    }
}
