package com.neevin.vkcupmobile.apimodels;

import androidx.annotation.NonNull;

public class VKPost {
    public final String text;

    public final int commentsCount;
    public final int likesCount;
    public final int repostsCount;

    public VKPost(String text, int commentsCount, int likesCount, int repostsCount){
        this.text = text;
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
