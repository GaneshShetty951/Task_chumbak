package com.example.ganeshshetty.task_chumbak;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Ganesh on 22/03/17.
 */

public class CustomBindingAdapter
{
    @BindingAdapter({"bind:thumbnail"})
    public static void loadImage(ImageView imageView, String url)
    {
        Picasso.with(imageView.getContext()).load("https://image.tmdb.org/t/p/w200_and_h300_bestv2/"+url).resize(200,200).into(imageView);
    }
}