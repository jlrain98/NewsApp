package com.example.joeslie.newsapp;

/**
 * Created by Joes Lie on 19/10/2017.
 */



public class News {

    private String mTitle;

    private String mSub;

    private String mDate;

    private String mImageUrl;

    private String mUrl;
    //Constructor
    public News(String Title, String Sub, String Date, String ImageUrl) {
        mTitle = Title;
        mSub = Sub;
        mDate = Date;
        mImageUrl = ImageUrl;
    }

    //Return Val

    public String getTitle() {
        return mTitle;
    }

    public String getSub() {
        return mSub;
    }

    public String getDate() {
        return mDate;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
    public String getUrl() {
        return mUrl;
    }
}
