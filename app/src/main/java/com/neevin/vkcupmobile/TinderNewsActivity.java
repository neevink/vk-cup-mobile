package com.neevin.vkcupmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

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
    private CardStack cardStack;
    private CardAdapter cardAdapter;

    // Разрешения, которые нужны приложению
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

        cardAdapter = new CardAdapter(getApplicationContext(), 0);
        cardStack = (CardStack) findViewById(R.id.card_stack);
        cardStack.setContentResource(R.layout.card_layout);
        cardStack.setStackMargin(20);
        cardStack.setAdapter(cardAdapter);

        cardStack.setListener(new CardDragHandler(getApplicationContext(), cardAdapter));

        if(!VK.isLoggedIn()){
            VK.login(this, Arrays.asList(scope));
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("cardAdapter.getCount = " + cardAdapter.getCount());
                    // Из cardAdapter не удаляются элементы
                    if(VK.isLoggedIn() && cardAdapter.getCount() - cardAdapter.lastIndex + 1 <= 30 && canSend){
                        pullNewPosts(10);
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {}
                }
            }
        });
        t.start();
    }

    public void likeButtonHandler(View view) {
        cardStack.discardTop(1);
    }

    public void dislikeButtonHandler(View view) {
        cardStack.discardTop(0);
    }

    // Обработка авторизации
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKAuthCallback callback = new VKAuthCallback(){
            @Override
            public void onLogin(VKAccessToken token) {
                // User passed authorization
            }

            @Override
            public void onLoginFailed(int errorCode) {
                // User didn't pass authorization
                AlertDialog alertDialog = new AlertDialog.Builder(TinderNewsActivity.this).create();
                alertDialog.setTitle("Ошибка входа");
                alertDialog.setMessage("Чтобы получить ленту новостей, обязательно нужно войти в аккаунт.");
                alertDialog.show();
            }
        };

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String startFrom = null;

    // Квота на количество запросав одновременно
    private boolean canSend = true;

    private void pullNewPosts(int postsCount){

        canSend = false;
        VK.execute(new VKNewsFeedRequest(postsCount, startFrom), new VKApiCallback<VKNewsFeed>() {
            @Override
            public void success(VKNewsFeed result) {
                canSend = true;
                List<VKPost> posts = result.posts;
                startFrom = result.nextFrom;
                cardAdapter.addAll(posts);
            }

            @Override
            public void fail(@NotNull Exception e) {
                canSend = true;
                e.printStackTrace();
            }
        });
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