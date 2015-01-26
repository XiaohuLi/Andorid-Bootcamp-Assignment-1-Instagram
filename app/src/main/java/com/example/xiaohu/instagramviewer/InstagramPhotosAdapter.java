package com.example.xiaohu.instagramviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by xiaohu on 1/25/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos){
        super(context, android.R.layout.simple_list_item_1, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        TextView imgCaption = (TextView) convertView.findViewById(R.id.imgCaption);
        ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
        TextView imgUsername = (TextView) convertView.findViewById(R.id.imgUsername);
        TextView imgTime = (TextView) convertView.findViewById(R.id.imgTime);
        ImageView imgUser = (ImageView) convertView.findViewById(R.id.imgUser);
        TextView imgLikes = (TextView) convertView.findViewById(R.id.tVlikes);
        imgCaption.setText(photo.caption);
        imgUsername.setText(photo.username);
        imgPhoto.getLayoutParams().height = photo.imageHeight;
        imgPhoto.setImageResource(0);
        imgTime.setText(getTimeSinceCreated(photo.createdTimestamp));
        imgLikes.setText(String.valueOf(photo.likesCount) + " likes");
        Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);
        imgUser.setImageResource(0);
        imgUser.getLayoutParams().height=60;
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(10)
                .oval(false)
                .build();

        Picasso.with(getContext())
                .load(photo.profilePicUrl)
                //.fit()
                .transform(transformation)
                .into(imgUser);
        return convertView;
    }

    private String getTimeSinceCreated(long createdTimestamp) {
        java.util.Date date= new java.util.Date();
        long diff = date.getTime()/1000 - createdTimestamp;
        if(diff <= 60){
            return diff + "s";
        }else if(diff <= 3600){
            return diff/60 + "m";
        }else if(diff <= 86400){
            return diff/3600 + "h";
        }else return diff/86400 + "d" + diff%86400/3600 + "h";

    }
}
