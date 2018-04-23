package com.example.eunkong.mysampleandroid.activity3;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eunkong.myapplication.R;

/**
 * Created by eunkong on 2018. 2. 13..
 */

public class LooperThreadActivity extends Activity {

    private static final int MESSAGE_TEST_TYPE = 1000;
    private Handler mHandler;
    private Button mBtnResult;
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper_thread);

        mBtnResult = findViewById(R.id.btnResult);
        mTvResult = findViewById(R.id.tvResult);

        mBtnResult.setOnClickListener(mOnClickListener);

        // 메인 스레드
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                // 메세지 받으면 UI 처리
                if(msg.what == MESSAGE_TEST_TYPE) {
                    mTvResult.setText("카운트 완료");
                }
            }
        };

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {

                case R.id.btnResult:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            // handler post 를 통해 1초 간격으로 UI 업데이트
                            for(int i = 0; i <  10; i++) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                final int finalI = i;

                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTvResult.setText(String.valueOf(finalI));
                                    }
                                });
                            }

                            // 메세지 보내기
                            Message message = mHandler.obtainMessage(MESSAGE_TEST_TYPE);
                            mHandler.sendMessage(message);
                        }
                    }).start();


                    break;


            }
        }
    };

}
