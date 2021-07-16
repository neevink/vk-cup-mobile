package com.neevin.vkcupmobile.vkapi;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class VKPost {
    public final String text;
    public final Bitmap postImage;

    public final String profileName;
    public final String publishTime;
    public final Bitmap profilePhoto;

    // Нижнее пока не добавлено в отображение
    public final int commentsCount;
    public final int likesCount;
    public final int repostsCount;

    public VKPost(String text, Bitmap postImage, String profileName, String publishTime, Bitmap profilePhoto, int commentsCount, int likesCount, int repostsCount) {
        this.text = text;
        this.postImage = postImage;
        this.profileName = profileName;
        this.publishTime = publishTime;
        this.profilePhoto = profilePhoto;
        this.commentsCount = commentsCount;
        this.likesCount = likesCount;
        this.repostsCount = repostsCount;
    }


    @NonNull
    @Override
    public String toString() {
        return text + " | comments: " + this.commentsCount + " | likes: " + this.likesCount
                + " | reposts: "  + this.repostsCount;
    }
}
