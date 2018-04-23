package com.example.eunkong.mysampleandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.eunkong.mysampleandroid.IOnClickListenerChildCheckBox;
import com.example.eunkong.myapplication.R;
import com.example.eunkong.mysampleandroid.adapter.TwoDepthAgreeAdapter;
import com.example.eunkong.mysampleandroid.data.ViewChildData;
import com.example.eunkong.mysampleandroid.data.ViewGroupData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eunkong on 2018. 3. 21..
 */

public class ExpandableListViewActivity extends Activity {

    private static final String TAG = ExpandableListViewActivity.class.getSimpleName();


    private ExpandableListView mExpandableListView;
    private LinearLayout mllAgreeAll;
    private TwoDepthAgreeAdapter mTwoDepthAdapter;
    private CheckBox mCbAgreeAllCheckbox;
    private Button mBtnNext;

    private ArrayList<ViewGroupData> mGroupList = new ArrayList<>();
    private HashMap<String, ArrayList<ViewChildData>> hashMap = new HashMap<>();
    private ArrayList<ViewChildData> mChildList1 = new ArrayList<>();
    private ArrayList<ViewChildData> mChildList2 = new ArrayList<>();
    private ArrayList<ViewChildData> mChildList3 = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_listview);


        mllAgreeAll             = (LinearLayout) findViewById(R.id.ll_agree_all);
        mCbAgreeAllCheckbox     = (CheckBox) mllAgreeAll.findViewById(R.id.cb_agree_checkbox);
        mExpandableListView     = (ExpandableListView) findViewById(R.id.lvExpandableListView);
        mBtnNext                = (Button) findViewById(R.id.btnNext);

        setData();

        initView();

    }

    /**
     * 데이터 세팅
     */
    private void setData() {

        String[] agreeTerms = getResources().getStringArray(R.array.array_agree_terms);
        String[] agreeTermsUplus = getResources().getStringArray(R.array.array_agree_terms_uplus);
        String[] agreeTermsCredit = getResources().getStringArray(R.array.array_agree_terms_credit);
        String[] agreeTermsStock = getResources().getStringArray(R.array.array_agree_terms_stock);



        ViewChildData viewChildData1 = new ViewChildData(agreeTermsUplus[0], "", false, true, "Y");
        ViewChildData viewChildData2 = new ViewChildData(agreeTermsUplus[1], "", false, true, "Y");
        ViewChildData viewChildData3 = new ViewChildData(agreeTermsUplus[2], "", false, true, "Y");
        ViewChildData viewChildData4 = new ViewChildData(agreeTermsUplus[3], "", false, true, "Y");
        ViewChildData viewChildData5 = new ViewChildData(agreeTermsUplus[4], "", false, true, "N");

        mChildList1.add(viewChildData1);
        mChildList1.add(viewChildData2);
        mChildList1.add(viewChildData3);
        mChildList1.add(viewChildData4);
        mChildList1.add(viewChildData5);


        ViewChildData viewChildData6 = new ViewChildData(agreeTermsCredit[0], "", false, true, "Y");
        ViewChildData viewChildData7 = new ViewChildData(agreeTermsCredit[1], "", false, true, "Y");
        ViewChildData viewChildData8 = new ViewChildData(agreeTermsCredit[2], "", false, true, "Y");
        ViewChildData viewChildData9 = new ViewChildData(agreeTermsCredit[3], "", false, true, "Y");
        ViewChildData viewChildData10 = new ViewChildData(agreeTermsCredit[4], "", false, true, "Y");

        mChildList2.add(viewChildData6);
        mChildList2.add(viewChildData7);
        mChildList2.add(viewChildData8);
        mChildList2.add(viewChildData9);
        mChildList2.add(viewChildData10);


        ViewChildData viewChildData11 = new ViewChildData(agreeTermsStock[0], "", false, true, "Y");
        ViewChildData viewChildData12 = new ViewChildData(agreeTermsStock[1], "", false, true, "Y");
        ViewChildData viewChildData13 = new ViewChildData(agreeTermsStock[2], "", false, true, "Y");
        ViewChildData viewChildData14 = new ViewChildData(agreeTermsStock[3], "", false, true, "Y");

        mChildList3.add(viewChildData11);
        mChildList3.add(viewChildData12);
        mChildList3.add(viewChildData13);
        mChildList3.add(viewChildData14);


        // child
        for(int i = 0; i <  agreeTermsUplus.length; i++) {
            mChildList1.get(i).setTitle(agreeTermsUplus[i]);
        }

        for(int i = 0; i <  agreeTermsCredit.length; i++) {
            mChildList2.get(i).setTitle(agreeTermsCredit[i]);
        }

        for(int i = 0; i <  agreeTermsStock.length; i++) {
            mChildList3.get(i).setTitle(agreeTermsStock[i]);
        }


        for(ViewChildData child : mChildList1 ) {
            Log.d(TAG, "child item title : " + child.getTitle());
        }


        for(ViewChildData child : mChildList2 ) {
            Log.d(TAG, "child item title : " + child.getTitle());
        }


        for(ViewChildData child : mChildList3 ) {
            Log.d(TAG, "child item title : " + child.getTitle());
        }


        // group
        ViewGroupData viewGroupData1 = new ViewGroupData(agreeTerms[0],false, mChildList1);
        ViewGroupData viewGroupData2 = new ViewGroupData(agreeTerms[1],false, mChildList2);
        ViewGroupData viewGroupData3 = new ViewGroupData(agreeTerms[2],false, mChildList3);


        mGroupList.add(viewGroupData1);
        mGroupList.add(viewGroupData2);
        mGroupList.add(viewGroupData3);


    }

    /**
     * view 초기화
     */
    private void initView() {

        // ExpandableListView 초기화
        mTwoDepthAdapter  = new TwoDepthAgreeAdapter(ExpandableListViewActivity.this, mGroupList );
        mExpandableListView .setAdapter(mTwoDepthAdapter);

        // 다음 버튼 눌렀을 때 필수 약관 확인
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTwoDepthAdapter.isAgreeCheck(0)) {
                    Toast.makeText(getApplicationContext(), "필수 약관 동의 모두 체크 완료", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "필수 약관 동의 미 체크", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Log.i(TAG, "child (0,0) : " + mTwoDepthAdapter.getChild(0,0));


        // 처음에 그룹 모두 열기
        for(int i=0; i<mTwoDepthAdapter.getGroupCount(); i++) {
            mExpandableListView.expandGroup(i);
        }

        // 모든 약관 전체 동의
        mllAgreeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isCheck = mCbAgreeAllCheckbox.isChecked();

                for(int i = 0; i < mTwoDepthAdapter.getGroupCount(); i++) {

                    Log.i(TAG, "약관 전체 동의 groupPosition : " + i + ", isCheck : " + !isCheck);

                    mCbAgreeAllCheckbox.setChecked( !isCheck );
                    mTwoDepthAdapter.setGroupCheckBox(i, !isCheck);
                    mTwoDepthAdapter.setChildItemsCheckBox(i, !isCheck);

                }

            }
        });

        // group item click
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // 현재 체크 상태
                boolean isCheck = mTwoDepthAdapter.getGroup(groupPosition).isCheck();

                Log.i(TAG, "onGroupClick() - groupPosition : " + groupPosition + ", isCheck : " + !isCheck);

                mTwoDepthAdapter.setGroupCheckBox(groupPosition, !isCheck);          // group item 체크 설정
                mTwoDepthAdapter.setChildItemsCheckBox(groupPosition, !isCheck);    // child item 모두 체크 설정

                if(mTwoDepthAdapter.isAllGroupCheckBox(groupPosition, !isCheck)) {
                    mCbAgreeAllCheckbox.setChecked( true );
                } else {
                    mCbAgreeAllCheckbox.setChecked( false );
                }

                return true;
            }
        });

        // child item click
        mTwoDepthAdapter.setOnClickListenerChildCheckBox(new IOnClickListenerChildCheckBox() {
            @Override
            public void onClickListenerChildCheckBox(int groupPosition,int childPosition, boolean isCheck) {

                Log.i(TAG, "onChildClick() - groupPosition : " + groupPosition + ", isCheck : " + !isCheck);

                // child item 체크 설정
                mTwoDepthAdapter.setChildCheckBox(groupPosition, childPosition, !isCheck);


                // 모든 child가 체크되어 있으면
                if(mTwoDepthAdapter.isAllChildCheckBox(groupPosition, !isCheck)) {

                    // group 체크 설정
                    mTwoDepthAdapter.setGroupCheckBox(groupPosition, true);
                    // 모든 group 체크되어 있으면
                    if(mTwoDepthAdapter.isAllGroupCheckBox(groupPosition, !isCheck)) {
                        // 전체동의 체크 설정
                        mCbAgreeAllCheckbox.setChecked( true );
                    } else {
                        mCbAgreeAllCheckbox.setChecked( false );
                    }
                } else {
                    // group 체크 해제
                    mTwoDepthAdapter.setGroupCheckBox(groupPosition, false);

                    // 모든 group 체크되어 있으면
                    if(mTwoDepthAdapter.isAllGroupCheckBox(groupPosition, !isCheck)) {
                        // 전체동의 체크 설정
                        mCbAgreeAllCheckbox.setChecked( true );
                    } else {
                        mCbAgreeAllCheckbox.setChecked( false );
                    }
                }
            }
        });

    }



}
