package com.example.eunkong.mysampleandroid.activity;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eunkong.myapplication.R;
import com.example.eunkong.mysampleandroid.data.Person;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by eunkong on 2018. 3. 15..
 */

public class HttpPostJsonActivity extends Activity {

    private static final String TAG = HttpPostJsonActivity.class.getSimpleName();

    private TextView mTvConnection;
    private EditText mEtName;
    private EditText mEtCountry;
    private EditText mEtTwitter;
    private Button mbtnSend;

    private Person mPerson;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_post_json);

        mTvConnection  = findViewById(R.id.tv_connection);
        mEtName        = findViewById(R.id.et_name);
        mEtCountry     = findViewById(R.id.et_country);
        mEtTwitter     = findViewById(R.id.et_twitter);
        mbtnSend       = findViewById(R.id.btnSendPost);

        mbtnSend.setOnClickListener(mOnClickListener);


        if(isConnected()) {
            mTvConnection.setText("You are connected.");
        } else {
            mTvConnection.setText("You are NOT connected.");
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSendPost:
                    if(validate()) {
                        new HttpAsynTask().execute("http://hmkcode.appspot.com/jsonservlet");
                    }
                    break;

            }
        }
    };

    /**
     * 네트워크 연결 상태 확인
     * @return
     */
    private boolean isConnected() {
        boolean isConnected;

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            isConnected = true;
        } else {
            isConnected = false;
        }
        return isConnected;

    }

    /**
     * 데이터 유효성 확인 (데이터 빈 값 체크)
     * @return
     */
    private boolean validate() {
        if(mEtName.getText().toString().trim().equals("")) {
            return false;
        } else if(mEtCountry.getText().toString().trim().equals("")) {
            return false;
        } else if(mEtTwitter.getText().toString().trim().equals("")) {
            return false;
        } else
            return true;
    }

    public static String postSend(String url, Person person) {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        String result = "";
        String jsonStr = "";


        try {
            URL urlCon = new URL(url);

            httpURLConnection = (HttpURLConnection) urlCon.openConnection();

            JSONObject jsonObject = new JSONObject();

            jsonObject.accumulate("name,", person.getName());
            jsonObject.accumulate("country,", person.getCountry());
            jsonObject.accumulate("twitter,", person.getTwiiter());

            jsonStr = jsonObject.toString();

            Log.d(TAG, "json : " + jsonStr);

//            StringEntity entity = new String

            // Jaskosn Lib 사용 : Person Object를 JSON String 형태로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            jsonStr = objectMapper.writeValueAsString(person);

            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-type", "aaplication/json");

            // OutputStream으로 post 데이터를 넘겨주겠다는 옵션
            httpURLConnection.setDoOutput(true);
            // Inputstream으로 서버로부터 응답을 받겠다는 옵션
            httpURLConnection.setDoInput(true);


            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(jsonStr.getBytes("euc-kr"));
            outputStream.flush();

            int responseCode = httpURLConnection.getResponseCode();
            Log.d(TAG, "네트워크 통신 responseCode : " + responseCode);
            if(responseCode == HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "네트워크 통신 성공 : " + responseCode);
                inputStream = httpURLConnection.getInputStream();

                // inputStream을 String으로 변환
                if(inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                } else {
                    result = "Did not work!";
                }
            } else {
                Log.d(TAG, "네트워크 통신 실패 : " + responseCode);
            }


            Log.d(TAG, "result : " + result);
        } catch (IOException e) {
            Log.d(TAG, "exception message : " + e.getMessage());
        } catch (Exception e) {
            Log.d(TAG, "exception message : " + e.getMessage());

        } finally {
            Log.d(TAG, "finally");
            httpURLConnection.disconnect();
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        String line = "";
        String result = "";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        while((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        inputStream.close();

        return result;
    }

    private class HttpAsynTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            mPerson = new Person();
            mPerson.setName(mEtName.getText().toString());
            mPerson.setCountry(mEtCountry.getText().toString());
            mPerson.setTwiiter(mEtTwitter.getText().toString());

            Log.d(TAG, "url : " + urls[0] + ", person : " + mPerson.toString());
            return postSend(urls[0], mPerson);
        }

        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "데이터 전송 완료", Toast.LENGTH_SHORT).show();
        }
    }
}
