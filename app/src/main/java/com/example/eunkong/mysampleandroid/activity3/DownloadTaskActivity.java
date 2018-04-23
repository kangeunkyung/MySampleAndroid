package com.example.eunkong.mysampleandroid.activity3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eunkong.mysampleandroid.DownloadTask;
import com.example.eunkong.myapplication.R;

import java.io.File;

/**
 * Created by eunkong on 2018. 2. 14..
 */

public class DownloadTaskActivity extends Activity implements DialogInterface.OnCancelListener {

    private final int PERMISSION_REQUEST_CODE = 1000;

    private final String finalUrl = "http://webnautes.tistory.com/attachment/cfile4.uf@267BB53E58451C582BD045.avi";

    private String[] PERMISSION = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    private Button mBtnStart;

    private DownloadTask mDownloadTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadtask);

        mBtnStart = findViewById(R.id.btnStart);

        mBtnStart.setOnClickListener(mOnClickListener);



    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnStart:
                    // 퍼미션 허가 여부 확인
                    if( hasPermissions(PERMISSION) == false) {
                        // 퍼미션 요청
                        requestNecessaryPermissions(PERMISSION);
                    }

                    File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Alight.avi");

                    // 파일이 존재하면
//                    if(outputFile.exists()) {
//                        // 기존 파일 재생
//                    } else {
//                        // 파일 새로 다운로드
////
//                        // Custom ProgressBar 이용
////                        CustomProgressBar progressDialog = new CustomProgressBar(DownloadTaskActivity.this);
////                        DownloadTask ct = new DownloadTask(DownloadTaskActivity.this, progressDialog);
////                        mDownloadTask = (DownloadTask) ct.execute(finalUrl);
//
//                        // ProgressDialog 이용
//                        ProgressDialog progressDialog = createProgressDialog();
//                        DownloadTask ct = new DownloadTask(DownloadTaskActivity.this, progressDialog);
//                        mDownloadTask = (DownloadTask) ct.execute(finalUrl);
//                    }

                    // ProgressDialog 이용
                    ProgressDialog progressDialog = createProgressDialog();
                    DownloadTask ct = new DownloadTask(DownloadTaskActivity.this, progressDialog);
                    mDownloadTask = (DownloadTask) ct.execute(finalUrl);



                    break;

            }
        }
    };

    /**
     * 프로그레스 다이얼로그 생성
     * @return
     */
    private ProgressDialog createProgressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(DownloadTaskActivity.this);
        progressDialog.setProgress(0);
        progressDialog.setTitle("File Download Progress");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        return progressDialog;
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        mDownloadTask.cancel(true);
    }


    /**
     * 퍼미션 허가 상태 여부 확인
     * @param permissions
     * @return
     */
    private boolean hasPermissions(String[] permissions) {
        int isGranted;

        for(String perms : permissions) {

            isGranted = checkCallingOrSelfPermission(perms);
            if( ! (isGranted == PackageManager.PERMISSION_GRANTED) ) {
                // 퍼미션 허가 안된 경우
                return false;
            }
        }

        // 퍼미션 허가된 경우
        return true;
    }

    /**
     * 런타이 퍼미션 요청
     * @param permissions
     */
    private void requestNecessaryPermissions(String[] permissions) {
        // 마시멜로(API 23) 이상에서 런타임 퍼미션 요청
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0) {

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if((grantResults[0] == PackageManager.PERMISSION_GRANTED) == false ||
                                (grantResults[1] == PackageManager.PERMISSION_GRANTED) == false ) {
                            Toast.makeText(this, "앱을 실행하려면 퍼미션을 허가해야 합니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                }
                break;
        }
    }
}
