package com.yoktavian.moviekotlin.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
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
        subscribeMovies()
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

    private fun subscribeMovies() {
        moviesResponse = viewModel.getMovieNowPlaying(currentPage)
    }

    private fun startLoading() {
        loading.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        loading.visibility = View.GONE
    }

    private fun stopLoadingWithError(state : StateOfView) {
        when (state) {
            StateOfView.INTERNET_ERROR -> showAlert("Check your internet connection")
            else -> {
                showAlert("Server error.")
            }
        }
    }

    private fun showAlert(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
