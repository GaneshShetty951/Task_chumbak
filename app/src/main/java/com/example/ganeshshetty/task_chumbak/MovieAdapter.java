package com.example.ganeshshetty.task_chumbak;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ganesh Shetty on 09-03-2017.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CustomViewHolder> {
    private List<Movie> movieList;
    private Context pContext;

    public MovieAdapter(List<Movie> movieList, Context pContext) {
        this.movieList = movieList;
        this.pContext = pContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Movie movie=movieList.get(position);

        holder.title.setText(movie.getTitle());
        if (!TextUtils.isEmpty("https://image.tmdb.org/t/p/w200_and_h300_bestv2/"+movie.getThumbnail())) {
            Picasso.with(pContext).load("https://image.tmdb.org/t/p/w200_and_h300_bestv2/"+movie.getThumbnail())
                    .error(R.drawable.image)
                    .placeholder(R.drawable.image)
                    .into(holder.thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView title;
        protected ImageView thumbnail;
        public CustomViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.thumbnail=(ImageView) itemView.findViewById(R.id.thumbnail);
            this.title=(TextView) itemView.findViewById(R.id.title);
        }


        @Override
        public void onClick(View v) {
            Intent trailer_intent=new Intent(pContext,Trailer_Activity.class);
            trailer_intent.putExtra("movie_id",movieList.get(getPosition()).getId());
            pContext.startActivity(trailer_intent);
        }
    }
}
