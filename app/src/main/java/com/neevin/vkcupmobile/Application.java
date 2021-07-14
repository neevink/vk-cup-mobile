package com.neevin.vkcupmobile;

import android.content.Intent;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKTokenExpiredHandler;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VK.initialize(this);
        VK.addTokenExpiredHandler(tokenTracker);
    }

    // Раз уж прям для всего приложения инициализуем VK
    // , то и сделаем обработку невалидного access token тут
    private VKTokenExpiredHandler tokenTracker = new VKTokenExpiredHandler() {
        @Override
        public void onTokenExpired() {
            // token expired
            Intent intent = new Intent(Application.this, TinderNewsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };
}
