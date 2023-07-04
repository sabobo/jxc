package com.dgby.jxc.activity.main;

public class GridItem {
    private int mImageResId;
    private String mTitle;

    public GridItem(int imageResId, String title) {
        mImageResId = imageResId;
        mTitle = title;
    }

    public int getImageResId() {
        return mImageResId;
    }

    public String getTitle() {
        return mTitle;
    }
}
