package com.yoktavian.moviekotlin.data.repository

import com.yoktavian.moviekotlin.data.model.DetailMovie
import com.yoktavian.moviekotlin.remote.response.MovieResponse
import io.reactivex.Observable

interface InterfaceMovie {
    fun getMovies(page : Int) : Observable<MovieResponse>
    fun getSimiliarMovies(movieId: Int, page : Int) : Observable<MovieResponse>
    fun getDetailMovie(movieId : Int) : Observable<DetailMovie>
}