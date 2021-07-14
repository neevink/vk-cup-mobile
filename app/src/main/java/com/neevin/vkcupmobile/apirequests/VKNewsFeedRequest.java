package com.neevin.vkcupmobile.apirequests;

import com.neevin.vkcupmobile.apimodels.VKNewsFeed;
import com.neevin.vkcupmobile.apimodels.VKPost;
import com.vk.api.sdk.requests.VKRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class VKNewsFeedRequest extends VKRequest<VKNewsFeed> {

    public VKNewsFeedRequest() {
        super("newsfeed.get");

        // Получть только посты
        addParam("filters", "post");
        // Вернуть всего одну фотку
        // addParam("max_photos", "1");
        /*
        start_from - Идентификатор, необходимый для получения следующей страницы результатов.
        Значение, необходимое для передачи в этом параметре, возвращается в поле ответа next_from.

        count - указывает, какое максимальное число новостей следует возвращать, но не более 100.
        По умолчанию 50.
         */

        // Нужно как-то картинку получать в высоком разрешении

    }

    @Override
    public VKNewsFeed parse(JSONObject json){
        try{
            System.out.println(json.toString());

            JSONObject response = json.getJSONObject("response");

            JSONArray items = response.getJSONArray("items");
            String nextFrom = response.getString("next_from");

            List<VKPost> news = new LinkedList<VKPost>();
            for(int i = 0; i < items.length(); i++){
                JSONObject post = items.getJSONObject(i);
                String text = post.getString("text");

                // Ещё нужно зазвание и аватарка группы/страницы с которой опубликована запись
                // и время публикации

                int commentsCount = post.getJSONObject("comments").getInt("count");
                int likesCount = post.getJSONObject("likes").getInt("count");
                int repostsCount = post.getJSONObject("reposts").getInt("count");
                news.add(new VKPost(text, commentsCount, likesCount, repostsCount));
            }

            return new VKNewsFeed(news, nextFrom);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
