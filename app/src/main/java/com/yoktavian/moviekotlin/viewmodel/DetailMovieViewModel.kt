package com.yoktavian.moviekotlin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.yoktavian.moviekotlin.data.model.DetailMovie
import com.yoktavian.moviekotlin.data.model.Movie
import com.yoktavian.moviekotlin.data.repository.MovieRepository
import com.yoktavian.moviekotlin.viewmodel.HomeV1ViewModel.StateOfView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class DetailMovieViewModel : ViewModel() {
    private val stateOfView : MutableLiveData<StateOfView> = MutableLiveData()
    private val movieRepo : MovieRepository = MovieRepository()
    private val movie : MutableLiveData<DetailMovie> = MutableLiveData()
    private val similarMovies : MutableLiveData<List<Movie>> = MutableLiveData()
    private var disposable : Disposable? = null
    private val compositeDisposable = CompositeDisposable()

    fun getDetailMovie(movieId : Int) : LiveData<DetailMovie> {
        setState(StateOfView.LOADING)
        disposable = movieRepo.getDetailMovie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result -> movie.value = result
                    setState(StateOfView.SUCCESS)
                }, {
                    error ->
                    if (error is HttpException || error is IOException)
                        setState(StateOfView.INTERNET_ERROR)
                    else
                        setState(StateOfView.SOMETHING_ERROR)
                })

        compositeDisposable.add(disposable)

        return movie
    }

    fun getSimilarMovie(movieId: Int) : LiveData<List<Movie>> {
        disposable = movieRepo.getSimiliarMovies(movieId, 1) // 1 is page.
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result -> similarMovies.value = result.results
                    Log.d("=>Res", "Success 2")
                }, {
                    error ->
                    Log.d("=>Error", error.message)
                })

        compositeDisposable.add(disposable)

        return similarMovies
    }

    private fun setState(state : StateOfView) {
        stateOfView.value = state
    }

    fun getState() : LiveData<StateOfView> {
        return stateOfView
    }

    override fun onCleared() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
        super.onCleared()
    }
}