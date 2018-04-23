package com.example.eunkong.mysampleandroid.activity;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.eunkong.mysampleandroid.IOnClickListenerArrow;
import com.example.eunkong.mysampleandroid.IOnClickListenerCheckBox;
import com.example.eunkong.myapplication.R;
import com.example.eunkong.mysampleandroid.view.DynamicLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eunkong on 2018. 3. 21..
 */

public class DynamicViewActivity extends Activity {

    private static final String TAG = DynamicViewActivity.class.getSimpleName();

    private DynamicLinearLayout linearLayout;
    private LinearLayout mllCreateView;
    private ListView mLvListView;
    private Button mBtnCreateView;
    private Button mBtnRemoveView;

    private List<DynamicLinearLayout> dynamicLinearLayoutList;
    private List<View> viewList;

    private ArrayList<DynamicVewData> dataList;
    private DynamicViewListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_view);

        mllCreateView = findViewById(R.id.llCreateView);
        mLvListView = findViewById(R.id.lvListView);
        mBtnCreateView = findViewById(R.id.btnCreateView);
        mBtnRemoveView = findViewById(R.id.btnRemoveView);

        mBtnCreateView.setOnClickListener(onClickListener);
        mBtnRemoveView.setOnClickListener(onClickListener);


//        dataList = new ArrayList<>();
        viewList = new ArrayList<>();
        dynamicLinearLayoutList = new ArrayList<>();

        DynamicVewData data = new DynamicVewData();
        data.setTitle("필수 약관 동의");
        dataList.add(data);

//        mAdapter = new DynamicViewListAdapter(dataList);


    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                // view 추가
                case R.id.btnCreateView:

                    linearLayout = new DynamicLinearLayout(getApplicationContext());
                    mllCreateView.addView(linearLayout);

                    viewList.add(mllCreateView);
                    dynamicLinearLayoutList.add(linearLayout);

                    Log.d(TAG, "view index count : " + viewList.size());
                    linearLayout.setOnClickListenerCheckBox(new IOnClickListenerCheckBox() {
                        @Override
                        public void onClickListenerCheckBox(int position) {
                            Log.d(TAG, "체크박스 설정(" + position + ") : " + linearLayout.getChecked());
                            if (linearLayout.getChecked()) {
                                linearLayout.setChecked(false);
                            } else {
                                linearLayout.setChecked(true);
                            }
                        }
                    });

                    linearLayout.setOnClickListenerArrow(new IOnClickListenerArrow() {
                        @Override
                        public void onClickListenerArrow(int position) {
                            Log.d(TAG, "화살표 이미지 설정(" + position + ") : " + linearLayout.getExpended());

                            if (linearLayout.getExpended()) {
                                linearLayout.setExpended(false);
                            } else {
                                linearLayout.setExpended(true);
                            }
                        }
                    });
                    break;

                // view 삭제
                case R.id.btnRemoveView:

                    int removeIndex = viewList.size() - 1;
                    Log.d(TAG, "last view index : " + removeIndex);
                    if (removeIndex > -1) {
                        mllCreateView.removeViewAt(removeIndex);
                        viewList.remove(removeIndex);
                        dynamicLinearLayoutList.remove(removeIndex);
                    }
                    break;
            }
        }
    };

    class DynamicViewListAdapter implements ListAdapter {
        private ArrayList<DynamicVewData> mDataList;

        public DynamicViewListAdapter(ArrayList<DynamicVewData> dataList) {
            mDataList = dataList;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            if (mDataList == null)
                return 0;
            else
                return mDataList.size();
        }

        @Override
        public DynamicVewData getItem(int position) {
            if (mDataList == null)
                return null;
            else
                return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }
    }

    class DynamicVewData {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
