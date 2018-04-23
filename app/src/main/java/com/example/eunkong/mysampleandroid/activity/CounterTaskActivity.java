package com.example.eunkong.mysampleandroid.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.eunkong.mysampleandroid.CounterTask;
import com.example.eunkong.myapplication.R;

/**
 * Created by eunkong on 2018. 2. 14..
 */

public class CounterTaskActivity extends Activity implements DialogInterface.OnCancelListener {

    private Button mBtnStart;

    private CounterTask mCounterTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countertask);

        mBtnStart = findViewById(R.id.btnStart);

        mBtnStart.setOnClickListener(mOnClickListener);

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnStart:
                    ProgressDialog progressDialog = createProgressDialog();
                    progressDialog.show();

                    CounterTask ct = new CounterTask(progressDialog);
                    mCounterTask = (CounterTask) ct.execute(100);

                    break;

            }
        }
    };

    /**
     * 프로그레스 다이얼로그 생성
     * @return
     */
    private ProgressDialog createProgressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgress(0);
        progressDialog.setTitle("Progress");
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        return progressDialog;
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        mCounterTask.cancel(true);
    }
}
