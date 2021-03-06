package com.yoktavian.moviekotlin.data.repository

import com.yoktavian.moviekotlin.BuildConfig
import com.yoktavian.moviekotlin.data.model.DetailMovie
import com.yoktavian.moviekotlin.remote.api.ApiClient
import com.yoktavian.moviekotlin.remote.api.ApiService
import com.yoktavian.moviekotlin.remote.response.MovieResponse
import io.reactivex.Observable
import io.reactivex.functions.Function

class MovieRepository : InterfaceMovie {

    private val apiClient: ApiClient = ApiService.create()
    private val language : String = "en-US"

    override fun getMovies(page : Int): Observable<MovieResponse> {
        val observable : Observable<MovieResponse> = apiClient.getMovieNowPlaying(
                BuildConfig.API_KEY_MOVIE, language, page)

        return observable.flatMap(Function<MovieResponse, Observable<MovieResponse>> {
            return@Function Observable.just(it)
        })
    }

    override fun getSimiliarMovies(movieId: Int, page : Int): Observable<MovieResponse> {
        val observable : Observable<MovieResponse> = apiClient.getSimiliarMovies(movieId,
                BuildConfig.API_KEY_MOVIE, language, page)

        return observable.flatMap(Function<MovieResponse, Observable<MovieResponse>> {
            return@Function Observable.just(it)
        })
    }

    override fun getDetailMovie(movieId: Int): Observable<DetailMovie> {
        val observable : Observable<DetailMovie> = apiClient.getDetailMovie(movieId,
                BuildConfig.API_KEY_MOVIE, language)

        return observable.flatMap(Function<DetailMovie, Observable<DetailMovie>> {
            return@Function Observable.just(it)
        })
    }
}