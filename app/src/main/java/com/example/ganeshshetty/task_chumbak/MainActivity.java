package com.example.ganeshshetty.task_chumbak;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List<Movie> movieList;
    private static int MAX_PAGE;
    private static int PAGE=1;
    private RecyclerView mRecyclerView;
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    LinearLayoutManager layoutmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isNetworkAvailable()) {
            mRecyclerView = (RecyclerView) findViewById(R.id.movierecycler);
            progressBar = (ProgressBar)findViewById(R.id.progress_bar);
            movieList = new ArrayList<>();
            load_movie_list();
            layoutmanager=new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutmanager);
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if(layoutmanager.findLastCompletelyVisibleItemPosition()==movieList.size()-1 && movieList.get(movieList.size()-1).getPage()<=MAX_PAGE ){
                        MainActivity.PAGE+=1;
                        load_movie_list();
                    }
                }
            });
        }
        else {
            Toast toast = Toast.makeText(MainActivity.this, "Mobile internet or Wifi Should be Active !", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void load_movie_list() {
        AsyncTask<Void,Void,Void> task=new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                Integer result = 0;
                HttpURLConnection urlConnection;
                URL url = null;
                try {
                    url = new URL("https://api.themoviedb.org/3/movie/upcoming?api_key=7075383d8b7a0ca221d3832b63e83150&language=en-US&page="+MainActivity.PAGE);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    int statusCode = urlConnection.getResponseCode();

                    // 200 represents HTTP OK
                    if (statusCode == 200) {
                        BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            response.append(line);
                        }
                        parseResult(response.toString());
                        result = 1; // Successful
                    } else {
                        result = 0; //"Failed to fetch data!";
                    }
                } catch (MalformedURLException e) {
                    Log.e("MalformedURLException", e.toString());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("IOException", e.toString());
                    e.printStackTrace();
                }

                if(result==0)
                {
                    Toast toast = Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_LONG);
                    toast.show();
                }
                return null; //"Failed to fetch data!";
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                adapter = new MovieAdapter(movieList, MainActivity.this);
                mRecyclerView.setAdapter(adapter);
            }
        };
        task.execute();
    }

    private void parseResult(String s) {
        JSONObject response = null;
        try {
            response = new JSONObject(s);
            MainActivity.MAX_PAGE=response.optInt("total_pages");
            JSONArray movies = response.optJSONArray("results");
            for (int i = 0; i < movies.length(); i++) {
                JSONObject movie_object=movies.optJSONObject(i);
                movieList.add(new Movie(movie_object.optLong("id"),movie_object.optString("title"),movie_object.optString("poster_path"),response.optInt("pages")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
