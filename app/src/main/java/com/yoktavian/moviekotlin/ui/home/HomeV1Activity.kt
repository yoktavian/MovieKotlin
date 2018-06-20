package com.yoktavian.moviekotlin.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.yoktavian.moviekotlin.R
import com.yoktavian.moviekotlin.data.model.Movie
import com.yoktavian.moviekotlin.remote.response.MovieResponse
import com.yoktavian.moviekotlin.viewmodel.HomeV1ViewModel
import kotlinx.android.synthetic.main.activity_home_v1.*

class HomeV1Activity : AppCompatActivity() {

    private var moviesResponse : LiveData<MovieResponse>? = null
    private var movies : ArrayList<Movie> = ArrayList()
    private lateinit var viewModel : HomeV1ViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_v1)
        viewModel = ViewModelProviders.of(this).get(HomeV1ViewModel::class.java)
        list_movies.hasFixedSize()
        movieAdapter = MovieAdapter(movies)
        list_movies.adapter = movieAdapter
        subscribeMovies()
        observeMovies()
    }

    private fun observeMovies() {
        moviesResponse!!.observe(this, Observer {
            movies.addAll(it!!.results)
            movieAdapter.notifyDataSetChanged()
        })
    }

    private fun subscribeMovies() {
        moviesResponse = viewModel.getMovieNowPlaying(1)
    }
}
