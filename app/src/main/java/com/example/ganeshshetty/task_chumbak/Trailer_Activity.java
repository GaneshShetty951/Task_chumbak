package com.example.ganeshshetty.task_chumbak;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Trailer_Activity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private YouTubePlayerView trailer_view;
    private ProgressBar progressBar;
    private long movie_id;
    private String video_id;
    private static final int RECOVERY_REQUEST = 1;
    public static final String API_KEY= "AIzaSyDNumK0sm7rB88C3c9IK8bS4hhAvSx9bp0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        trailer_view=(YouTubePlayerView) findViewById(R.id.trailer_view);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        movie_id=getIntent().getLongExtra("movie_id",0);
        getVdeoId(movie_id);

        trailer_view.initialize(API_KEY,this);



    }

    private void getVdeoId(final long movie_id) {
        AsyncTask<Integer,Void,Void> task=new AsyncTask<Integer, Void, Void>() {
            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Integer... params) {
                Integer result = 0;
                HttpURLConnection urlConnection;
                URL url = null;
                try {
                    url = new URL("https://api.themoviedb.org/3/movie/" + movie_id + "/videos?api_key=7075383d8b7a0ca221d3832b63e83150&language=en-US");
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
                    Toast toast = Toast.makeText(Trailer_Activity.this, "Failed to fetch video!", Toast.LENGTH_LONG);
                    toast.show();
                }
                return null; //"Failed to fetch data!";
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressBar.setVisibility(View.GONE);
            }
        };

        task.execute();
    }

    private void parseResult(String s) {
        JSONObject response = null;
        try {
            response = new JSONObject(s);
            JSONArray trailer = response.optJSONArray("results");
            if(trailer.length()!=0)
            {
                JSONObject trailer_object=trailer.optJSONObject(0);
                video_id=trailer_object.optString("key");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        if(!b)
        {
            youTubePlayer.cueVideo(video_id);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    private PlaybackEventListener playbackEventListener=new PlaybackEventListener(){

        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };
    private PlayerStateChangeListener playerStateChangeListener=new PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
}
