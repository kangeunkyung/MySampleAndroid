package com.example.eunkong.mysampleandroid.data;

import android.webkit.WebView;

import java.util.ArrayList;


/**
 * 가입 및 부가서비스 약관동의 child item data
 */
public class ViewChildData {

    private String title;       // 약관 제목
    private String content;     // 약관 내용
    private boolean isCheck;    // 약관 동의 체크 여부
    private boolean isExpand;   // 약관 내용 펼치기/닫기
    private String isNecessary;   // 약관 내용 펼치기/닫기
//    private ArrayList<ViewChildData> childList = new ArrayList<ViewChildData>();

    public ViewChildData(String title, String content, boolean isCheck, boolean isExpand, String isNecessary) {
        this.title = title;
        this.content = content;
        this.isCheck = isCheck;
        this.isExpand = isExpand;
        this.isNecessary = isNecessary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public String isNecessary() {
        return isNecessary;
    }

    public void setNecessary(String necessary) {
        isNecessary = necessary;
    }
}
