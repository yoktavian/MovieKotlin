package com.yoktavian.moviekotlin.remote.api

import com.yoktavian.moviekotlin.data.model.DetailMovie
import com.yoktavian.moviekotlin.remote.response.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET("/3/movie/now_playing")
    fun getMovieNowPlaying(@Query("api_key") api_key : String,
                           @Query("language") language : String,
                           @Query("page") page : Int) : Observable<MovieResponse>

    @GET("/3/movie/{id}")
    fun getDetailMovie(@Path("id") movieId : Int,
                       @Query("api_key") api_key: String,
                       @Query("language") language: String) : Observable<DetailMovie>
}