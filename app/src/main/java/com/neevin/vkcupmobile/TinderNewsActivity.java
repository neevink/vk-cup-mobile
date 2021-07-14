package com.neevin.vkcupmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.neevin.vkcupmobile.apimodels.VKNewsFeed;
import com.neevin.vkcupmobile.apimodels.VKPost;
import com.neevin.vkcupmobile.apirequests.VKNewsFeedRequest;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKScope;
import com.vk.api.sdk.exceptions.VKApiExecutionException;
import com.vk.api.sdk.requests.VKRequest;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class TinderNewsActivity extends AppCompatActivity {

    private VKScope[] scope = new VKScope[]{
            VKScope.WALL,
            VKScope.FRIENDS,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder_news);

        // Тут делаем видимой кнопку "назад"
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Тут ставим новый вид кнопки "назад"
        actionBar.setHomeAsUpIndicator(R.drawable.back_button);

        // Тут меняем заголовок Action
        actionBar.setTitle("Новости");

        VK.login(this, Arrays.asList(scope));
    }

    // Обработка авторизации
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKAuthCallback callback = new VKAuthCallback(){
            @Override
            public void onLogin(VKAccessToken token) {
                // User passed authorization
                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();

                VK.execute(new VKNewsFeedRequest(), new VKApiCallback<VKNewsFeed>() {
                    @Override
                    public void success(VKNewsFeed result) {
                        System.out.println(result);
                    }

                    @Override
                    public void fail(@NotNull Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onLoginFailed(int errorCode) {
                // User didn't pass authorization
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        };

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    // Обрабатываем нажитие кнопки "назад"
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}