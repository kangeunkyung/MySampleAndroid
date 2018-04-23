package com.example.eunkong.mysampleandroid.activity3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eunkong.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private Button btnThread;
    private Button btnCounterTask;
    private Button btnDownloadTask;
    private Button btnCallback;
    private Button btnHttpPostJson;
    private Button btnDynamicView;
    private Button btnListView;
    private Button btnExpandableListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnThread = findViewById(R.id.btnThread);
        btnCounterTask = findViewById(R.id.btnCounterTask);
        btnDownloadTask = findViewById(R.id.btnDownloadTask);
        btnCallback = findViewById(R.id.btnCallback);
        btnHttpPostJson = findViewById(R.id.btnHttpPostJson);
        btnDynamicView = findViewById(R.id.btnDynamicView);
        btnListView = findViewById(R.id.btnListView);
        btnExpandableListView = findViewById(R.id.btnExpandableListView);

        btnThread.setOnClickListener(mOnClickListener);
        btnCounterTask.setOnClickListener(mOnClickListener);
        btnDownloadTask.setOnClickListener(mOnClickListener);
        btnCallback.setOnClickListener(mOnClickListener);
        btnHttpPostJson.setOnClickListener(mOnClickListener);
        btnDynamicView.setOnClickListener(mOnClickListener);
        btnListView.setOnClickListener(mOnClickListener);
        btnExpandableListView.setOnClickListener(mOnClickListener);


    }


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                // 스레드
                case R.id.btnThread:
                    startActivity(LooperThreadActivity.class);
                    break;

                // CounterTask
                case R.id.btnCounterTask:
                    startActivity(CounterTaskActivity.class);
                    break;

                // DownloadTask
                case R.id.btnDownloadTask:
                    startActivity(DownloadTaskActivity.class);
                    break;

                // callback 함수
                case R.id.btnCallback:
                    startActivity(CallbackActivity.class);
                    break;

                // Http Josn 데이터 주고받기....서버 실패
                case R.id.btnHttpPostJson:
                    startActivity(HttpPostJsonActivity.class);
                    break;

                // 동적 뷰 생성
                case R.id.btnDynamicView:
                    startActivity(DynamicViewActivity.class);
                    break;

                // 동적 뷰 생성
                case R.id.btnListView:
                    startActivity(ListViewActivity.class);
                    break;

                // ExpandableListView
                case R.id.btnExpandableListView:
                    startActivity(ExpandableListViewActivity.class);
                    break;

            }
        }
    };


    /**
     * 액티비티 전환
     * @param className
     */
    private void startActivity(Class className) {
        Intent intent = new Intent(MainActivity.this, className);
        startActivity(intent);

    }

}
