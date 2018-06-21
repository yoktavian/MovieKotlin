package com.yoktavian.moviekotlin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.yoktavian.moviekotlin.data.model.DetailMovie
import com.yoktavian.moviekotlin.viewmodel.HomeV1ViewModel.StateOfView
import com.yoktavian.moviekotlin.data.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class DetailMovieViewModel : ViewModel() {
    private val stateOfView : MutableLiveData<StateOfView> = MutableLiveData()
    private val movieRepo : MovieRepository = MovieRepository()
    private val movie : MutableLiveData<DetailMovie> = MutableLiveData()
    private var disposable : Disposable? = null

    fun getDetailMovie(movieId : Int) : LiveData<DetailMovie> {
        setState(StateOfView.LOADING)
        disposable = movieRepo.getDetailMovie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result -> movie.value = result
                    setState(StateOfView.SUCCESS)
                }, {
                    error -> error.localizedMessage
                    if (error is HttpException)
                        setState(StateOfView.INTERNET_ERROR)
                    else
                        setState(StateOfView.SERVER_ERROR)
                })

        return movie
    }

    private fun setState(state : StateOfView) {
        stateOfView.value = state
    }

    fun getState() : LiveData<StateOfView> {
        return stateOfView
    }

    override fun onCleared() {
        if (disposable != null && disposable!!.isDisposed)
            disposable!!.dispose()
        super.onCleared()
    }
}