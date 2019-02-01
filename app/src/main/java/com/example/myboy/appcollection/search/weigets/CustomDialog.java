package com.example.myboy.appcollection.search.weigets;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

public class CustomDialog extends Dialog {

    public Context context;


    public CustomDialog(Context context) {
        super(context);
        this.context = context;
    }
}
