package com.neevin.vkcupmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.neevin.vkcupmobile.vkapi.VKNewsFeed;
import com.neevin.vkcupmobile.vkapi.VKNewsFeedRequest;
import com.neevin.vkcupmobile.cards.CardAdapter;
import com.neevin.vkcupmobile.cards.CardDragHandler;
import com.neevin.vkcupmobile.vkapi.VKPost;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKScope;
import com.wenchao.cardstack.CardStack;

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

        // Тут меняем заголовок Action
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        actionBar.setTitle((Html.fromHtml("<font color=\"#000\" align=\"center\">Новости</font>")));

        // Тут делаем видимой кнопку "назад"
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Тут ставим новый вид кнопки "назад"
        actionBar.setHomeAsUpIndicator(R.drawable.back_button);

        VK.login(this, Arrays.asList(scope));

        initImages();
        cardStack = (CardStack) findViewById(R.id.card_stack);
        cardStack.setContentResource(R.layout.card_layout);
        cardStack.setStackMargin(20);
        cardStack.setAdapter(cardAdapter);

        cardStack.setListener(new CardDragHandler(getApplicationContext()));
    }

    public void likeButtonHandler(View view) {
        cardStack.discardTop(1);
    }

    public void dislikeButtonHandler(View view) {
        cardStack.discardTop(0);
    }

    private CardStack cardStack;
    private CardAdapter cardAdapter;

    private void initImages(){
        cardAdapter = new CardAdapter(getApplicationContext(), 0);
        VKPost p = new VKPost("Текст тестового поста",
                null,
                "Тестовое название группы",
                "только что",
                null,
                0,
                0,
                0);
        cardAdapter.add(p);
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
                        List<VKPost> posts = result.posts;

                        for(int i = 0; i < posts.size(); i++){
                            cardAdapter.add(posts.get(i));
                        }
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