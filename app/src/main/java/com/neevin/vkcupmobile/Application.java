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

    private VKTokenExpiredHandler tokenTracker = new VKTokenExpiredHandler() {
        @Override
        public void onTokenExpired() {
            // когда срок действия токена истёк
            Intent intent = new Intent(Application.this, TinderNewsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };
}
