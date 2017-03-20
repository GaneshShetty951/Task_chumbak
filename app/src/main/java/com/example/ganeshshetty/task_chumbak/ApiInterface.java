package com.example.ganeshshetty.task_chumbak;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ganesh on 20/03/17.
 */

public interface ApiInterface {
    @GET("upcoming")
    Call<MovieResponse> getUpComingMovies (@Query("api_key") String apiKey,@Query("language") String language,@Query("page") int page);
}
