package com.example.eunkong.mysampleandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eunkong.mysampleandroid.CallbackEvent;
import com.example.eunkong.mysampleandroid.EventRegistration;
import com.example.eunkong.myapplication.R;

/**
 * Created by eunkong on 2018. 3. 5..
 */

public class CallbackActivity extends Activity {

    private Button mBtnCallback;
    private TextView mTvCallback;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);

        mBtnCallback = findViewById(R.id.btnCallback);
        mTvCallback = findViewById(R.id.tvCallback);



        mBtnCallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 이벤트 등록
                final EventRegistration mEvent = new EventRegistration(callbackEvent);

                mEvent.doWork();
            }
        });

    }



    // 콜백함수 정의
    CallbackEvent callbackEvent = new CallbackEvent() {
        @Override
        public void callbackMethod() {
            mTvCallback.setText("콜백함수 호출");
        }
    };
}
