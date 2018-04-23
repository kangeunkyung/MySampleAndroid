package com.example.eunkong.mysampleandroid;

import android.util.Log;

/**
 * Created by eunkong on 2018. 3. 5..
 */

public class EventRegistration {

    private static final String TAG = EventRegistration.class.getSimpleName();

    private CallbackEvent mCallbackEvent;

    // 콜백함수 이벤트 등록
    public EventRegistration(CallbackEvent callbackEvent) {
        mCallbackEvent = callbackEvent;
    }

    // 콜백함수 이벤트 정의
    public void doWork() {
        Log.d(TAG, "doWork()");

        mCallbackEvent.callbackMethod();
    }
}
