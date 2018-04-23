package com.example.eunkong.mysampleandroid.data;


import android.util.Log;

import java.util.ArrayList;

/**
 * 가입 및 부가서비스 약관동의 child item data
 */
public class ViewGroupData {
    private static final String TAG = ViewGroupData.class.getSimpleName();

    private String title;       // 약관 제목
    private boolean isCheck;    // 약관 동의 체크 여부
    private ArrayList<ViewChildData> childList = new ArrayList<>();

    public ViewGroupData(String title, boolean isCheck, ArrayList<ViewChildData> viewChildData) {
        this.title = title;
        this.isCheck = isCheck;
        this.childList = viewChildData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public ArrayList<ViewChildData> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<ViewChildData> childList) {
        for(int i=0; i<childList.size(); i++)
            Log.d(TAG, "setchildList() - " + childList.get(i).getTitle());
        this.childList = childList;
    }
}
