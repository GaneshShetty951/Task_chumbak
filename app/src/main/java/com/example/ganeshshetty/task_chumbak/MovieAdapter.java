package com.example.ganeshshetty.task_chumbak;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganeshshetty.task_chumbak.databinding.CardBinding;

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

        LayoutInflater layoutInflater = LayoutInflater.from(pContext);
        CardBinding itemBinding = CardBinding.inflate(layoutInflater, parent, false);
        return new CustomViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Movie movie=movieList.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private CardBinding binding;

        public CustomViewHolder(CardBinding rowView) {
            super(rowView.getRoot());
            this.binding = rowView;
            rowView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent trailer_intent=new Intent(pContext,Trailer_Activity.class);
                    trailer_intent.putExtra("movie_id",movieList.get(getPosition()).getId());
                    pContext.startActivity(trailer_intent);
                }
            });
        }

        public void bind(Movie movie)
        {
            binding.setVariable(BR.movie,movie);
            binding.executePendingBindings();
        }

    }
}
