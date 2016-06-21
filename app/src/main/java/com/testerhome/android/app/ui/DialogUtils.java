package com.testerhome.android.app.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by Bin Li on 2016/6/21.
 */
public class DialogUtils {
    private DialogUtils() {}

    public static ProgressDialog createProgressDialog(Context context) {
        return new ProgressDialog(context);
    }

    public static AlertDialog.Builder createAlertDialogBuilder(Context context) {
        return new AlertDialog.Builder(context);
    }
}
