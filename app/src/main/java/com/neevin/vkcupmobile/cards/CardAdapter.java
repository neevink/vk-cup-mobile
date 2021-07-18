package com.neevin.vkcupmobile.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.neevin.vkcupmobile.R;
import com.neevin.vkcupmobile.vkapi.VKPost;

public class CardAdapter extends ArrayAdapter<VKPost> {
    public int lastIndex = 0;

    public CardAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        VKPost post = getItem(position);

        ImageView profilePhoto = (ImageView) convertView.findViewById(R.id.profile_photo);
        TextView profileName = (TextView) convertView.findViewById(R.id.profile_name);
        TextView publishTime = (TextView) convertView.findViewById(R.id.publish_time);
        ImageView postImage = (ImageView) convertView.findViewById(R.id.post_image);
        TextView postText = (TextView) convertView.findViewById(R.id.post_text);

        if(post.postImage == null) {
            postImage.setVisibility(View.GONE);
        }
        else {
            postImage.setVisibility(View.VISIBLE);
        }

        profilePhoto.setImageBitmap(post.profilePhoto);
        profileName.setText(post.profileName);
        publishTime.setText(post.publishTime);
        postImage.setImageBitmap(post.postImage);
        postText.setText(post.text);

        return convertView;
    }
}
