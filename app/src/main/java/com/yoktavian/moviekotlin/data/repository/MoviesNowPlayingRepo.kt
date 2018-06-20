package com.yoktavian.moviekotlin.data.repository

import com.yoktavian.moviekotlin.BuildConfig
import com.yoktavian.moviekotlin.remote.api.ApiClient
import com.yoktavian.moviekotlin.remote.api.ApiService
import com.yoktavian.moviekotlin.remote.response.MovieResponse
import io.reactivex.Observable
import io.reactivex.functions.Function

class MoviesNowPlayingRepo : InterfaceMovie {

    private val apiClient: ApiClient = ApiService.create()
    private val language : String = "en-US"

    override fun getMoviesNowPlaying(page : Int): Observable<MovieResponse> {
        val observable : Observable<MovieResponse> = apiClient.getMovieNowPlaying(
                BuildConfig.API_KEY_MOVIE, language, page)

        return observable.flatMap(Function<MovieResponse, Observable<MovieResponse>> {
            return@Function Observable.just(it)
        })
    }
}