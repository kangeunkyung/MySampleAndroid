package com.example.eunkong.mysampleandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Random;

/**
 * Created by eunkong on 2018. 2. 14..
 */

public class CounterTask extends AsyncTask<Integer, Integer, Boolean> {
    private static final long DELAY = 100;
    private static final int PROBABAILITY_TO_FAIL_EVERY_STEP = 1;
    private static final String TAG = "CounterTask";
    private ProgressDialog mProgressDialog;
    private Random mRandom;


    public CounterTask(ProgressDialog mProgressDialog) {
        this.mProgressDialog = mProgressDialog;
        this.mProgressDialog.setMax(100);
        this.mRandom = new Random();
    }

    @Override
    protected Boolean doInBackground(Integer... params) {
        validateParams(params);

        int countMax = params[0];

        // 프로그레스바가 1초씩 증가하도록 설정 => 네트워크 속도 및 파일 다운로드 속도에 따라 증가하도록 변경이 필요한 부분.
        for(int i = 0; i < countMax; i++) {
            int progressPercent = calculateProgressPercent(i, countMax);

            // publishProgress 호출할 때마다 onProgressUpdate()에서 UI 업데이트 됨
            publishProgress(progressPercent);
            Log.i(TAG, "counter : " + Integer.toString(i));
            Log.i(TAG, "Progress published  : " + Integer.toString(progressPercent) + "%");

            // 실패한 경우
            if(sleep() == false) {
                return false;
            }

            // 실패한 경우
            if(doseFail(PROBABAILITY_TO_FAIL_EVERY_STEP)) {
                Log.i(TAG, "Background fails");
                return false;
            }
        }

        Log.i(TAG, "Background succeeds.");
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // publishProgress()를 통해 전달받은 값을PrgressDialog 변경된 위치에 적용
        mProgressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        mProgressDialog.dismiss();

        String message = null;

        if(result) {
            message = "Background succeeded";
        } else {
            message = "Background failed";
        }
        Log.i(TAG, message);

        // 완료 후 프로그레스바 결과 생성
        Dialog dialog = createAlertDialog(message);
        dialog.show();
    }

    // 프로그레스바 결과
    private Dialog createAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mProgressDialog.getContext());
        AlertDialog dialog =
                builder.setMessage(message)
                .setTitle("Result")
                .setCancelable(true)
                .create();

        return  dialog;

    }

    protected  void onCancelled() {
        super.onCancelled();
        Log.i(TAG, "Background cancelled");
    }

    // 실패 요인 : mRandom 0 ~ 100 까지 난수 발생 시켜 0이 나오면 실패
    private boolean doseFail(int probabilityToFail) {
        int prob = mRandom.nextInt(100);

        Log.i(TAG, "prob : " + prob);

        if(prob < probabilityToFail)
            return true;
        else
            return false;
    }

    // delay : 0.1초씩 프로그레스바 증가
    private boolean sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    // progress 퍼센트 계산
    private int calculateProgressPercent(int done, int max) {
        done = done * 100;
        return done / max;

    }

    // params 유효성 체크
    private void validateParams(Integer... integers) {
        if(integers == null || integers.length != 1 || integers[0] < 0) {
            throw new IllegalArgumentException();
        }
    }
}
