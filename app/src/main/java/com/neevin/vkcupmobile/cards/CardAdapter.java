package com.neevin.vkcupmobile.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.neevin.vkcupmobile.R;

public class CardAdapter extends ArrayAdapter<Integer> {
    public CardAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_content);
        imageView.setImageResource(getItem(position));
        return convertView;
    }
}
