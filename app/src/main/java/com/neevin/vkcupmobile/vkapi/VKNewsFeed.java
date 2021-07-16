package com.neevin.vkcupmobile.vkapi;

import androidx.annotation.NonNull;

import java.util.List;

public class VKNewsFeed {
    public final List<VKPost> posts;
    public final String nextFrom;

    public VKNewsFeed(List<VKPost> posts, String nextFrom) {
        this.posts = posts;
        this.nextFrom = nextFrom;
    }

    @NonNull
    @Override
    public String toString() {
        return posts.toString() + nextFrom;
    }
}
