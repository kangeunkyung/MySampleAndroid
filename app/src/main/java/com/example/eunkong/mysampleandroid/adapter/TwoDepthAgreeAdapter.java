package com.example.eunkong.mysampleandroid.adapter;


import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eunkong.mysampleandroid.IOnClickListenerChildCheckBox;
import com.example.eunkong.myapplication.R;
import com.example.eunkong.mysampleandroid.data.ViewChildData;
import com.example.eunkong.mysampleandroid.data.ViewGroupData;

import java.util.ArrayList;

/**
 * 회원가입 및 부가서비스 약관 ExpandableListview Adapter
 */
public class TwoDepthAgreeAdapter implements ExpandableListAdapter {
    private static final String TAG = TwoDepthAgreeAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<ViewGroupData> mGroupList;
    private LayoutInflater mInflater;
    private DataSetObservable mDataSetObservable;

    private IOnClickListenerChildCheckBox mIOnClickListenerChildCheckBox;


    public TwoDepthAgreeAdapter(Context context, ArrayList<ViewGroupData> groupList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mGroupList = groupList;
        mDataSetObservable = new DataSetObservable();

    }

    public void setOnClickListenerChildCheckBox(IOnClickListenerChildCheckBox iOnClickListenerChildCheckBox) {
        mIOnClickListenerChildCheckBox = iOnClickListenerChildCheckBox;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);

    }

    @Override
    public int getGroupCount() {
        if(mGroupList == null)
            return 0;
        else
            return mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(mGroupList == null || mGroupList.get(groupPosition).getChildList() == null)
            return 0;
        else
            return mGroupList.get(groupPosition).getChildList().size();
    }

    @Override
    public ViewGroupData getGroup(int groupPosition) {
        if(mGroupList == null) {
            return null;
        } else {
            return mGroupList.get(groupPosition);
        }
    }

    @Override
    public ViewChildData getChild(int groupPosition, int childPosition) {
        ViewGroupData groupData = mGroupList.get(groupPosition);

        if(groupData == null)
            return null;

        ArrayList<ViewChildData> childList = groupData.getChildList();

        Log.i(TAG, "getChild() - child item title : " + childList.get(childPosition).getTitle());
        if(childList == null)
            return null;
        else
            return childList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

        Log.i(TAG, "getGroupView() groupPosition : " + groupPosition );
        final ViewGroupData groupData = getGroup(groupPosition);
        final ViewHolderGroup viewHolder;

        View convertView = view;

        if(convertView == null) {
            viewHolder = new ViewHolderGroup();
            convertView = mInflater.inflate(R.layout.listitem_view_group, parent, false);
            viewHolder.llTitle = (LinearLayout) convertView.findViewById(R.id.lv_agree_title);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_agree_title);
            viewHolder.ivCheck = (CheckBox) convertView.findViewById(R.id.cb_agree_checkbox);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolderGroup) convertView.getTag();
        }

        viewHolder.tvTitle.setText(groupData.getTitle());

        Log.d(TAG, "groupPosition : " + groupPosition + ", isCheck() : " + groupData.isCheck());
        if(groupData.isCheck()) {
            viewHolder.ivCheck.setChecked(true);
        } else {
            viewHolder.ivCheck.setChecked(false);
        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, final View view, ViewGroup parent) {

        Log.i(TAG, "getchildView() groupPosition : " + groupPosition + ", childPosition : " + childPosition);
        final ViewChildData childData = getChild(groupPosition, childPosition);

        final ViewHolderChild viewHolder;

        View convertView = view;

        if(convertView == null) {
            viewHolder = new ViewHolderChild();
            convertView = mInflater.inflate(R.layout.listitem_view_child, null);

            viewHolder.llAgreeContent = (LinearLayout) convertView.findViewById(R.id.lv_agree_contents);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_agree_title);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_agree_contents);
            viewHolder.ivCheck = (CheckBox) convertView.findViewById(R.id.cb_agree_checkbox);
            viewHolder.ivExpand = (ImageView) convertView.findViewById(R.id.iv_agree_more);

            viewHolder.llCheck = (LinearLayout) convertView.findViewById(R.id.ll_agree_checkbox);
            viewHolder.llExpand = (LinearLayout) convertView.findViewById(R.id.ll_agree_more_bg);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolderChild) convertView.getTag();
        }


        // 데이터 세팅
        viewHolder.tvTitle.setText(getChild(groupPosition, childPosition).getTitle());
        viewHolder.tvContent.setText(getChild(groupPosition, childPosition).getContent());

        if(childData.isCheck()) {
            viewHolder.ivCheck.setChecked(true);
        } else {
            viewHolder.ivCheck.setChecked(false);
        }


        if(childData.isExpand()) {
            viewHolder.ivExpand.setBackgroundResource(R.drawable.btn_arrow_06_n);
            viewHolder.llAgreeContent.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivExpand.setBackgroundResource(R.drawable.btn_arrow_06_p);
            viewHolder.llAgreeContent.setVisibility(View.GONE);
        }



        // 제목 레이아웃
        viewHolder.llCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mIOnClickListenerChildCheckBox != null) {
                    mIOnClickListenerChildCheckBox.onClickListenerChildCheckBox(groupPosition, childPosition, viewHolder.ivCheck.isChecked());
                }
                Log.d(TAG, "child item position : " + childPosition + ",  isCheck : " + childData.isCheck());

            }
        });


        // 약관 내용 펼치기/닫기
        viewHolder.llExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExpand = childData.isExpand();
                Log.d(TAG, "child item position : " + childPosition + ",  isExpand : " + isExpand);

                if(isExpand) {
                    viewHolder.ivExpand.setBackgroundResource(R.drawable.btn_arrow_06_p);
                    viewHolder.llAgreeContent.setVisibility(View.GONE);

                } else {
                    viewHolder.ivExpand.setBackgroundResource(R.drawable.btn_arrow_06_n);
                    viewHolder.llAgreeContent.setVisibility(View.VISIBLE);
                }

                childData.setExpand( !isExpand );

            }
        });


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    /**
     * 모든 group item 동의 체크 여부 확인
     * @param groupPosition
     * @param isCheck
     */
    public boolean isAllGroupCheckBox(int groupPosition, boolean isCheck) {
        boolean result = false;
        for(int i = 0; i < getGroupCount(); i++) {
            Log.d(TAG, "isAllGroupCheckBox() - i : " + i + ", groupPosition : " + groupPosition + ", isCheck : " + getGroup(i).isCheck());

            if(getGroup(i).isCheck() == false) {
                result = false;
                break;
            } else {
                result = true;
            }
        }

        return result;

    }

    /**
     * group 내 모든 child item 동의 체크 설정
     * @param groupPosition
     * @param isCheck
     */
    public void setChildItemsCheckBox(int groupPosition, boolean isCheck) {
        for(int i = 0; i < getChildrenCount(groupPosition); i++) {
            Log.d(TAG, "setChildItemsCheckBox() - groupPosition : " + groupPosition + ", isCheck : " + isCheck);
            getChild(groupPosition, i).setCheck(isCheck);
        }
        mDataSetObservable.notifyChanged();

    }


    /**
     * group item 동의 체크 설정
     * @param isCheck
     */
    public void setGroupCheckBox(int groupPosition, boolean isCheck) {
        Log.d(TAG, "setGroupCheckBox() - isCheck : " + isCheck);
        getGroup(groupPosition).setCheck(isCheck);
        mDataSetObservable.notifyChanged();

    }



    /**
     * child item 동의 체크 설정
     * @param isCheck
     */
    public void setChildCheckBox(int groupPosition,int childPosition, boolean isCheck) {

        getChild(groupPosition, childPosition).setCheck( isCheck );
        mDataSetObservable.notifyChanged();

    }

    /**
     * group 내 모든 item 체크되어 있는지 확인
     * @return
     */
    public boolean isAllChildCheckBox(int groupPosition, boolean isCheck) {
        boolean result = false;

        // TODO : group 내 모든 item 체크되어 있는지 확인
        for(int i = 0; i < getChildrenCount(groupPosition); i++) {
            if(getChild(groupPosition, i).isCheck() == false) {
                result = false;
                Log.d(TAG, "isAllChildCheckBox() - getChild(" + i + ") : " + result);
                break;
            } else {
                result = true;
            }
        }

        Log.d(TAG, "isAllChildCheckBox() - groupPosition : " + groupPosition + ", result : " + result);

        return result;
    }



    /**
     * 필수 동의 약관 체크
     * @return
     */
    public boolean isAgreeCheck(int groupPosition) {

        boolean result = false;

        for(int i = 0; i < getChildrenCount(groupPosition); i++) {
            if("Y".equalsIgnoreCase(getChild(groupPosition, i).isNecessary())) {
                Log.d(TAG, "isAgreeCheck() - isCheck(" + i + ") : " + getChild(groupPosition, i).isCheck() + ", result : " + result);
                if(getChild(groupPosition, i).isCheck()) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            } else {
                result = true;
            }
        }

        return result;

    }




    // 자식 뷰
    class ViewHolderChild {

        public LinearLayout llAgreeContent;
        public TextView tvTitle;
        public TextView tvContent;
        public CheckBox ivCheck;
        public ImageView ivExpand;

        public LinearLayout llCheck;        // 버튼 선택 범위
        public LinearLayout llExpand;       // 버튼 선택 범위

    }

    // 그룹 뷰
    class ViewHolderGroup {

        public LinearLayout llTitle;
        public TextView tvTitle;
        public CheckBox ivCheck;



    }

}
