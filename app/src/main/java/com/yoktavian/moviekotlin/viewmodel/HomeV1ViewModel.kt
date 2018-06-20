package com.yoktavian.moviekotlin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.yoktavian.moviekotlin.data.repository.MoviesNowPlayingRepo
import com.yoktavian.moviekotlin.remote.response.MovieResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeV1ViewModel : ViewModel() {
    private val movieRepo : MoviesNowPlayingRepo = MoviesNowPlayingRepo()
    private val disposable : Disposable? = null
    private val movies : MutableLiveData<MovieResponse> = MutableLiveData()
    private var moviesResponse : Observable<MovieResponse>? = null

    fun getMovieNowPlaying(page : Int) : LiveData<MovieResponse> {
        moviesResponse = movieRepo.getMoviesNowPlaying(page)
        moviesResponse!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    result -> movies.value = result
                }

        return movies
    }

    override fun onCleared() {
        if (disposable != null && !disposable.isDisposed)
            disposable.dispose()
        super.onCleared()
    }
}