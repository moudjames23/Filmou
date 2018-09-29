package com.moudjames23.filmou.networkapi;

import com.moudjames23.filmou.model.Film;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Moud on 29-Sep-18.
 */
public interface NetworkAPI {

    @GET("manager.php")
    Call<List<Film>> movies(@Query("film") String code);

    /*@GET("movie/popular")
    Call<List<MoviesResponse>> getPopularMovies(@Query("api_key") String apiKey);*/

}
