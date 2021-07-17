package com.neevin.vkcupmobile.vkapi;

import androidx.annotation.NonNull;

// Профиль группы/человека во ВКонтакте
public class VKProfile {
    public final long id;
    public final String name;
    // photo_50 и photo_100 есть как у групп, так и у пользователей
    public final String photoURL;

    public VKProfile(long id, String name, String photoURL) {
        this.id = id;
        this.name = name;
        this.photoURL = photoURL;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("{%d, %s, %s}", id, name, photoURL);
    }
}
