package com.yoktavian.moviekotlin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.yoktavian.moviekotlin.data.repository.MovieRepository
import com.yoktavian.moviekotlin.remote.response.MovieResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class HomeV1ViewModel : ViewModel() {
    enum class StateOfView {
        LOADING, SERVER_ERROR, INTERNET_ERROR, SUCCESS
    }
    private val movieRepo : MovieRepository = MovieRepository()
    private var disposable : Disposable? = null
    private val movies : MutableLiveData<MovieResponse> = MutableLiveData()
    // experiment view
    private var stateOfView : MutableLiveData<StateOfView> = MutableLiveData()

    fun getMovieNowPlaying(page : Int) : LiveData<MovieResponse> {
        setState(StateOfView.LOADING)
        disposable = movieRepo.getMoviesNowPlaying(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result -> movies.value = result
                    setState(StateOfView.SUCCESS)
                }, {
                    error ->
                    if (error is HttpException || error is IOException)
                        setState(StateOfView.INTERNET_ERROR)
                    else
                        setState(StateOfView.SERVER_ERROR)
                })

        return movies
    }

    fun getState() : LiveData<StateOfView> {
        return stateOfView
    }

    private fun setState(state : StateOfView) {
        stateOfView.value = state
    }

    override fun onCleared() {
        if (disposable != null && disposable!!.isDisposed)
            disposable!!.dispose()
        super.onCleared()
    }
}