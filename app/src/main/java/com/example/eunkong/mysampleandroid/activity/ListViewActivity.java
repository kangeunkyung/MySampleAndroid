package com.example.eunkong.mysampleandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eunkong.mysampleandroid.IOnClickListenerArrow;
import com.example.eunkong.mysampleandroid.IOnClickListenerCheckBox;
import com.example.eunkong.myapplication.R;
import com.example.eunkong.mysampleandroid.view.DynamicLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eunkong on 2018. 3. 21..
 */

public class ListViewActivity extends Activity {

    private static final String TAG = ListViewActivity.class.getSimpleName();

    private DynamicLinearLayout linearLayout;
    private LinearLayout mllCreateView;
    private ListView mLvListView;
    private TextView tvTextview;

    private List<DynamicLinearLayout> dynamicLinearLayoutList;
    private List<View> viewList;

    private ArrayList<DynamicVewData> dataList;
    private DynamicViewListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        mllCreateView   = findViewById(R.id.llCreateView);
        mLvListView     = findViewById(R.id.lvListView);
        tvTextview  = findViewById(R.id.tvTextview);


        dataList = new ArrayList<>();
        viewList = new ArrayList<>();
        dynamicLinearLayoutList = new ArrayList<>();

        DynamicVewData data = new DynamicVewData();
        data.setTitle("필수 약관 동의");
        dataList.add(data);

        mAdapter = new DynamicViewListAdapter(dataList);
        mAdapter.setOnClickListenerCheckBox(new IOnClickListenerCheckBox() {
            @Override
            public void onClickListenerCheckBox(int position) {
                DynamicVewData data =  mAdapter.getItem(position);

//                boolean isChecked = dataList.get(position).ismChecked();
                boolean isChecked = data.ismChecked();
                if(isChecked) {
                    data.setTitle("111111");
//                    viewHolder.mIvUplusStockAgreeIcon.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.btn_arrow_06_n));
                    isChecked = false;
                } else {
                    data.setTitle("2222222");
//                    mAdapter.viewHolder.mIvUplusStockAgreeIcon.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.btn_arrow_06_p));
                    isChecked = true;
                }
                data.setChecked(isChecked);
                Log.d(TAG, "체크박스 설정 : " + isChecked);
                Log.d(TAG, "title : " + data.getTitle());
                mAdapter.notify();
            }
        });

        mLvListView.setAdapter(mAdapter);

    }

    class DynamicViewListAdapter implements ListAdapter {
        private ArrayList<DynamicVewData> mDataList;
        private ViewHolder viewHolder;

        private IOnClickListenerCheckBox mOnClickListenerCheckBox;
        private IOnClickListenerArrow mIOnClickListenerArrow;

        public DynamicViewListAdapter(ArrayList<DynamicVewData> dataList) {
            mDataList = dataList;
        }

        public void setOnClickListenerCheckBox(IOnClickListenerCheckBox iOnClickListenerGroupCheckBox) {
            mOnClickListenerCheckBox = iOnClickListenerGroupCheckBox;
        }

        public void setOnClickListenerArrow(IOnClickListenerArrow iOnClickListenerArrow) {
            mIOnClickListenerArrow = iOnClickListenerArrow;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            if(mDataList == null)
                return 0;
            else
                return mDataList.size();
        }

        @Override
        public DynamicVewData getItem(int position) {
            if(mDataList == null)
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = convertView;

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.layout_dynamic_linearlayout, null);

                viewHolder = new ViewHolder();


                viewHolder.mllUplusStockAgree      = view.findViewById(R.id.ll_uplus_stock_agree);
                viewHolder.mllUplusStockAgreeTitle = view.findViewById(R.id.ll_uplus_stock_agree_title);
                viewHolder.mllUplusStockAgreeIcon  = view.findViewById(R.id.ll_uplus_stock_agree_icon);
                viewHolder.mIvAgree                = view.findViewById(R.id.iv_agree);
                viewHolder.mTvUplusStockAgreeTitle = view.findViewById(R.id.tv_uplus_stock_agree_title);
                viewHolder.mIvUplusStockAgreeIcon  = view.findViewById(R.id.iv_uplus_stock_agree_icon);


                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.mllUplusStockAgreeTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mOnClickListenerCheckBox != null)
                    mOnClickListenerCheckBox.onClickListenerCheckBox(position);

//                    boolean isChecked = dataList.get(position).ismChecked();
//
//
//                    if(isChecked) {
//                        Log.d(TAG, "[체크박스 해제됨] position : " + position);
//                        viewHolder.mTvUplusStockAgreeTitle.setText("체크박스 해제됨");
//                        viewHolder.mIvAgree.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.btn_check_02_n));
//                        isChecked = false;
//                    } else {
//                        Log.d(TAG, "[체크박스 선택됨] position : " + position);
//                        viewHolder.mTvUplusStockAgreeTitle.setText("체크박스 선택됨");
//                        viewHolder.mIvAgree.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.btn_check_02_p));
//                        isChecked = true;
//                    }
//                    dataList.get(position).setChecked(isChecked);

                }
            });
            viewHolder.mllUplusStockAgreeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    mIOnClickListenerArrow.onClickListenerArrow(position);

                    boolean isExpended = dataList.get(position).ismExpended();

                    if(isExpended) {
                        Log.d(TAG, "[화살표 down] position : " + position);
                        viewHolder.mIvUplusStockAgreeIcon.setBackgroundResource(R.drawable.btn_arrow_06_n);
                        isExpended = false;
                    } else {
                        Log.d(TAG, "[화살표 up] position : " + position);
                        viewHolder.mIvUplusStockAgreeIcon.setBackgroundResource(R.drawable.btn_arrow_06_p);
                        isExpended = true;
                    }
                    dataList.get(position).setExpended(isExpended);
                    notify();
                }
            });


//            viewHolder.mIvAgree.setBackgroundResource(R.drawable.btn_check_02_n);
            viewHolder.mTvUplusStockAgreeTitle.setText(dataList.get(position).getTitle());
//            viewHolder.mIvUplusStockAgreeIcon.setBackgroundResource(R.drawable.btn_arrow_06_n);


            return view;
        }

//        private View.OnClickListener mOnClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                switch (view.getId()) {
//                    // 제목
//                    case R.id.ll_uplus_stock_agree_title:
//
//                        Log.d(TAG, "[제목 선택됨] position : " + position);
//                        mOnClickListenerCheckBox.onClickListenerCheckBox(position);
//                        break;
//
//                    // 화살표
//                    case R.id.ll_uplus_stock_agree_icon:
//                        Log.d(TAG, "[화살표 선택됨] position : " + position);
//                        mIOnClickListenerArrow.onClickListenerArrow(position);
//                        break;
//
//                }
//
//            }
//        };


        @Override
        public int getItemViewType(int position) {
            return 1;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
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
        private Drawable icon;
        private Drawable arrow;
        private String title;
        private boolean mExpended;
        private boolean mChecked;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean ismExpended() {
            return mExpended;
        }

        public void setmExpended(boolean mExpended) {
            this.mExpended = mExpended;
        }

        public boolean ismChecked() {
            return mChecked;
        }

        public void setmChecked(boolean mChecked) {
            this.mChecked = mChecked;
        }

        /**
         * 화살표 아이콘 이미지 설정
         * @param isExpend
         */
        public void setExpended(boolean isExpend) {
//            if(isExpend) {
//                mIvUplusStockAgreeIcon.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.btn_arrow_06_p));
//            } else {
//                mIvUplusStockAgreeIcon.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.btn_arrow_06_n));
//            }

            mExpended = isExpend;

        }


        /**
         * 체크박스 이미지 설정
         * @param isCheck
         */
        public void setChecked(boolean isCheck) {
//            if(isCheck) {
//                mIvAgree.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.btn_check_02_p));
//            } else {
//                mIvAgree.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.btn_check_02_n));
//            }

            mChecked = isCheck;
        }


    }

    class ViewHolder {

        public LinearLayout mllUplusStockAgree;
        public LinearLayout mllUplusStockAgreeTitle;
        public LinearLayout mllUplusStockAgreeIcon;
        public ImageView mIvAgree;
        public TextView mTvUplusStockAgreeTitle;
        public ImageView mIvUplusStockAgreeIcon;

    }
}
