package com.example.eunkong.mysampleandroid.view;

import android.content.Context;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eunkong.mysampleandroid.IOnClickListenerArrow;
import com.example.eunkong.mysampleandroid.IOnClickListenerCheckBox;
import com.example.eunkong.myapplication.R;

/**
 * Created by eunkong on 2018. 3. 21..
 */

public class DynamicLinearLayout extends LinearLayout {

    private Context mContext;

    private IOnClickListenerCheckBox mOnClickListenerCheckBox;
    private IOnClickListenerArrow mIOnClickListenerArrow;
    private LinearLayout mllUplusStockAgree;
    private LinearLayout mllUplusStockAgreeTitle;
    private LinearLayout mllUplusStockAgreeIcon;
    private ImageView mIvAgree;
    private TextView mTvUplusStockAgreeTitle;
    private ImageView mIvUplusStockAgreeIcon;

    private boolean mExpended;
    private boolean mChecked;

    public DynamicLinearLayout(Context context) {
        super(context);

        mContext = context;

        initView(context);
    }

    public void setOnClickListenerCheckBox(IOnClickListenerCheckBox IOnClickListenerCheckBox) {
        mOnClickListenerCheckBox = IOnClickListenerCheckBox;
    }

    public void setOnClickListenerArrow(IOnClickListenerArrow iOnClickListenerArrow) {
        mIOnClickListenerArrow = iOnClickListenerArrow;
    }


    public void initView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_dynamic_linearlayout, this, true);

        mllUplusStockAgree      = view.findViewById(R.id.ll_uplus_stock_agree);
        mllUplusStockAgreeTitle = view.findViewById(R.id.ll_uplus_stock_agree_title);
        mllUplusStockAgreeIcon = view.findViewById(R.id.ll_uplus_stock_agree_icon);
        mIvAgree                = view.findViewById(R.id.iv_agree);
        mTvUplusStockAgreeTitle = view.findViewById(R.id.tv_uplus_stock_agree_title);
        mIvUplusStockAgreeIcon  = view.findViewById(R.id.iv_uplus_stock_agree_icon);


        mllUplusStockAgreeTitle.setOnClickListener(mOnClickListener);
        mllUplusStockAgreeIcon.setOnClickListener(mOnClickListener);
        mTvUplusStockAgreeTitle.setText("U+스탁 이용약관");




    }

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // 제목
                case R.id.ll_uplus_stock_agree_title:

                    mOnClickListenerCheckBox.onClickListenerCheckBox(0);
                    break;

                // 화살표
                case R.id.ll_uplus_stock_agree_icon:

                    mIOnClickListenerArrow.onClickListenerArrow(0);
                    break;

            }
        }
    };

    public boolean getExpended() {
        return mExpended;
    }

    public boolean getChecked() {
        return mChecked;
    }

    /**
     * 화살표 아이콘 이미지 설정
     * @param isExpend
     */
    public void setExpended(boolean isExpend) {
        if(isExpend) {
            mIvUplusStockAgreeIcon.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.btn_arrow_06_p));
        } else {
            mIvUplusStockAgreeIcon.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.btn_arrow_06_n));
        }

        mExpended = isExpend;

    }


    /**
     * 체크박스 이미지 설정
     * @param isCheck
     */
    public void setChecked(boolean isCheck) {
        if(isCheck) {
            mIvAgree.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.btn_check_02_p));
        } else {
            mIvAgree.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.btn_check_02_n));
        }

        mChecked = isCheck;
    }

    /**
     * 제목 설정
     * @param title
     */
    public void setTitle(String title) {
        mTvUplusStockAgreeTitle.setText(title);
    }

}
