package com.example.eunkong.mysampleandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.example.eunkong.mysampleandroid.view.CustomProgressBar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 다운로드 진행 상황을 대화상자에 표시하는 AsyncTask
 */

public class DownloadTask extends AsyncTask<String, String, String> {

    private final static String TAG = DownloadTask.class.getSimpleName();

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private CustomProgressBar mProgressBar;
    private PowerManager.WakeLock mWakeLock;



    // 파일명까지 포함한 파일 경로
    private File outputFile;
    // 파일 디렉토리 경로
    private File path;

    /**
     * DownloadTask 생성자 - 1
     * @param context
     * @param progressDialog
     */
    public DownloadTask(Context context, ProgressDialog progressDialog) {
        mContext = context;
        mProgressDialog = progressDialog;
    }

    /**
     * DownloadTask 생성자 - 1
     * @param context
     * @param progressBar
     */
    public DownloadTask(Context context, CustomProgressBar progressBar) {
        mContext = context;
        mProgressBar = progressBar;
    }

    /**
     * 파일 다운로드 시작하기 전에 프로그레스 바를 화면에 보여줌 - 2
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // PARTIAL_WAKE_LOCK 플래그를 통해 CPU가 깨어 있도록 하는 것
        // 유저가 파워버튼을 눌러 화면을 꺼도 CPU는 wakeLock이 release 되기 전까지 계속 켜져 있도록 유지
        // 그리고, 프로그레스바 생성

        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
        mWakeLock.acquire();

        // onPreExecute()에서 setMessage() 설정 안해주면 onPostExecute()에서 setMessage() 적용이 안됨..... 이유는 모르겠음 ...
        mProgressDialog.setMessage("");
        mProgressDialog.show();

//        mProgressBar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        mProgressBar.show();
    }

    /**
     * 파일 다운로드 진행 - 3
     * @param values
     * @return
     */
    @Override
    protected String doInBackground(String... values) {

        BufferedInputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;

        URL url = null;
        try {
            url = new URL(values[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();
            }

            // 파일 크기
            int fileSize = connection.getContentLength();

            // url 주소로부터 파일 다운로드 하기 위한 input stream
            input = new BufferedInputStream(url.openStream(), 8192);

            // 동영상 파일 객체 생성
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            outputFile = new File (path, "Alight.avi");

            Log.d(TAG, "path : " + outputFile.getPath());

            // sd카드에 저장하기 위한 Output Stream
            output = new FileOutputStream(outputFile);

            byte data[] = new byte[4096];
            long downloadedSize = 0;
            int count;

            // 읽은 data가 존재하면
            while ((count = input.read(data)) != -1) {

                // 백 버튼으로 취소가 가능하도록
                if(isCancelled()){
                    input.close();
                    return null;
                }
                downloadedSize += count;

                // publishing the progress
                if(fileSize > 0) {
                    float percentage = ((float) downloadedSize / fileSize ) * 100;
                    String strProgress = "Downloaded " + downloadedSize + "KB / " + fileSize + "KB(" + (int)percentage + "%)";
                    publishProgress("" + (int) (downloadedSize * 100 / fileSize), strProgress);
                    Log.i(TAG, "프로그레스 진행중 : " +strProgress);

                }

                // 파일에 데이터 기록
                output.write(data, 0, count);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } finally {

            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * 다운로드 중 프로그레스바 업데이트 - 4
     * @param progress
     */
    @Override
    protected void onProgressUpdate(String... progress) {
        super.onProgressUpdate(progress);

        // publishProgress()를 통해 들어오는 파라메터 값 설정
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(Integer.parseInt(progress[0]));     // percentage
        mProgressDialog.setMessage(progress[1]);                        // 프레그레스 진행 중 문구


    }
    /**
     * 파일 다운로드 완료 후 - 5
     * @param result
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // 다운로드 완료 하면 wakeLock 해제하고, 프로그레스 다이얼로그 닫기
        mWakeLock.release();
        mProgressDialog.dismiss();


        if(result != null) {
            // 파일 다운로드 오류
            Toast.makeText(mContext, "File Download error : " + result, Toast.LENGTH_SHORT).show();
        } else {
            // 파일 다운로드 완료
            Toast.makeText(mContext, "File Download success", Toast.LENGTH_SHORT).show();

            Intent mediaIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaIntent.setData(Uri.fromFile(outputFile));
            mContext.sendBroadcast(mediaIntent);
            playVideo(outputFile.getParent());
        }
    }

    /**
     * 동영상 재생
     * @param path
     */
    private void playVideo(String path) {
        Uri videoUri = Uri.fromFile(new File(path));
        Intent videoIntent = new Intent(Intent.ACTION_VIEW);
        videoIntent.setDataAndType(videoUri, "video/*");

        // 동영상 재생
        if(videoIntent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(Intent.createChooser(videoIntent, null));
        }

    }


}