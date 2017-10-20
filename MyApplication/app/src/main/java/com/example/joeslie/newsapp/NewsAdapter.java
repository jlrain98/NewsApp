package com.example.joeslie.newsapp;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Joes Lie on 19/10/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        News my_news = getItem(position);

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        //TitleText
        TextView TitleText = (TextView) listItemView.findViewById(R.id.title);
        TitleText.setText(my_news.getTitle());

        //SubText
        TextView SubText = (TextView) listItemView.findViewById(R.id.subs);
        SubText.setText(my_news.getSub());

        TextView DateText = (TextView) listItemView.findViewById(R.id.date);
        DateText.setText(my_news.getDate());

        ImageView ImageImg = (ImageView) listItemView.findViewById(R.id.image);


        Picasso.with(getContext()).load(my_news.getImageUrl()).into(ImageImg);

        return listItemView;
    }
}
