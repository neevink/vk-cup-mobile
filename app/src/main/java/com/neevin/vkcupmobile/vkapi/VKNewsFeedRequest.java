package com.neevin.vkcupmobile.vkapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.vk.api.sdk.requests.VKRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
    public VKNewsFeed parse(JSONObject json) throws JSONException, IOException {
        try{
            System.out.println(json.toString());

            JSONObject response = json.getJSONObject("response");

            JSONArray items = response.getJSONArray("items");
            String nextFrom = response.getString("next_from");

            JSONArray profiles = response.getJSONArray("profiles");
            JSONArray groups = response.getJSONArray("groups");

            List<VKPost> news = new LinkedList<VKPost>();
            for(int i = 0; i < items.length(); i++){
                JSONObject post = items.getJSONObject(i);
                String text = post.getString("text");

                // Ещё нужно зазвание и аватарка группы/страницы с которой опубликована запись
                // и время публикации

                // Выяснилось, что вместо того, чтобы передовать пустой список attachments VK API
                // просто убирает это поле
                if(!post.has("attachments"))
                    continue;

                JSONArray arr = post.getJSONArray("attachments");
                System.out.println(arr.toString());

                String photoURL = firstPhotoURLFromAttachments(post.getJSONArray("attachments"));

                Bitmap postImage = null;
                if(photoURL != null){
                    postImage = loadByURL(photoURL);
                }


                /*
                Чтобы получить изображения и названия профелей/групп, нужно спарсить
                поля profiles - JSONArray
                и groups - JSONArray



                 */

                String profileName = "Название профиля";


                int date = post.getInt("date");
                String publishTime = "" + date;

                int commentsCount = post.getJSONObject("comments").getInt("count");
                int likesCount = post.getJSONObject("likes").getInt("count");
                int repostsCount = post.getJSONObject("reposts").getInt("count");
                news.add(
                        new VKPost(
                                text,
                                postImage,
                                profileName,
                                publishTime,
                                null, // profilePhoto
                                commentsCount,
                                likesCount,
                                repostsCount
                        )
                );
            }

            return new VKNewsFeed(news, nextFrom);
        }
        catch (Exception e){
            throw e;
        }
        //return null;
    }

    private String firstPhotoURLFromAttachments(JSONArray attachments) throws JSONException {
        try {
            for (int i = 0; i < attachments.length(); i++) {
                JSONObject element = attachments.getJSONObject(i);

                // Если прикреплённая штука - это фото, то парсим
                if(element.getString("type").equals("photo")){
                    JSONArray photoSizes = element.getJSONObject("photo").getJSONArray("sizes");

                    // Берём url фотки с лучшим качеством :)
                    String url = photoSizes.getJSONObject(photoSizes.length()-1).getString("url");
                    return url;
                }
            }
        }
        catch (Exception exc){
            //exc.printStackTrace();
            throw exc;
        }
        return null;
    }

    // Загрузить изображение по url, возвращает null в случае ошибки
    private Bitmap loadByURL(String urlString) throws IOException {
        try{
            URL url = new URL(urlString);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return image;
        }
        catch (Exception exc){
            // Надеюсь такого никогда не случится
            exc.printStackTrace();
            throw exc;
        }
        //return null;
    }
}
