package com.yoktavian.moviekotlin.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.yoktavian.moviekotlin.R
import com.yoktavian.moviekotlin.data.model.Movie
import com.yoktavian.moviekotlin.remote.response.MovieResponse
import com.yoktavian.moviekotlin.viewmodel.HomeV1ViewModel
import com.yoktavian.moviekotlin.viewmodel.HomeV1ViewModel.StateOfView
import kotlinx.android.synthetic.main.activity_home_v1.*

class HomeV1Activity : AppCompatActivity() {

    private var moviesResponse : LiveData<MovieResponse>? = null
    private var movies : ArrayList<Movie> = ArrayList()
    private lateinit var viewModel : HomeV1ViewModel
    private lateinit var movieAdapter: MovieAdapter
    private var currentPage : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_v1)
        viewModel = ViewModelProviders.of(this).get(HomeV1ViewModel::class.java)
        list_movies.hasFixedSize()
        movieAdapter = MovieAdapter(movies)
        list_movies.adapter = movieAdapter
        observeView()
        subscribeToGetMovies()
        observeMovies()
    }

    private fun observeView() {
        viewModel.getState().observe(this, Observer {
            if (it != null) {
                when (it) {
                    StateOfView.LOADING -> startLoading()
                    StateOfView.SUCCESS -> stopLoading()
                    else -> {
                        stopLoadingWithError(it)
                    }
                }
            }
        })
    }

    private fun observeMovies() {
        moviesResponse!!.observe(this, Observer {
            movies.addAll(it!!.results)
            movieAdapter.notifyDataSetChanged()
        })
    }

    private fun subscribeToGetMovies() {
        moviesResponse = viewModel.getMovieNowPlaying(currentPage)
    }

    private fun startLoading() {
        loading.startLoading()
    }

    private fun stopLoading() {
        loading.stopLoading()
    }

    private fun stopLoadingWithError(state : StateOfView) {
        when (state) {
            StateOfView.INTERNET_ERROR -> loading
                    .stopLoadingWithError("Check your internet connection")
            else -> {
                loading.stopLoadingWithError("Server error.")
            }
        }

        loading.setReloadClickListener(View.OnClickListener {
            subscribeToGetMovies()
        })
    }
}
