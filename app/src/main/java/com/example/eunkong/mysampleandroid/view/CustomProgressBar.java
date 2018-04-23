package com.example.eunkong.mysampleandroid.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;

import com.example.eunkong.myapplication.R;

/**
 * Created by eunkong on 2018. 2. 19..
 */

public class CustomProgressBar extends Dialog {
    public CustomProgressBar(@NonNull Activity activity) {
        super(activity);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_custom);
    }




}
