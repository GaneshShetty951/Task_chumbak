package com.example.ganeshshetty.task_chumbak;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

/**
 * Created by Ganesh Shetty on 09-03-2017.
 */

public class Movie {
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
    @SerializedName("page")
    int page;
    @SerializedName("id")
    long id;
    @SerializedName("title")
    String title;
    @SerializedName("poster_path")
    String thumbnail;

    public Movie(long id, String title, String thumbnail,int page) {
        this.id = id;
        this.title = title;
        this.thumbnail = "https://image.tmdb.org/t/p/w200_and_h300_bestv2/"+thumbnail;
        this.page=page;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }



    @BindingAdapter({"bind:thumnbnail"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load("https://image.tmdb.org/t/p/w200_and_h300_bestv2/"+imageUrl)
                .placeholder(R.drawable.image)
                .into(view);
    }

}
