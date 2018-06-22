package com.yoktavian.moviekotlin.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.yoktavian.moviekotlin.R
import com.yoktavian.moviekotlin.data.model.DetailMovie
import com.yoktavian.moviekotlin.data.model.Movie
import com.yoktavian.moviekotlin.viewmodel.DetailMovieViewModel
import com.yoktavian.moviekotlin.viewmodel.HomeV1ViewModel.StateOfView
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.jetbrains.annotations.Nullable

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var viewModel : DetailMovieViewModel
    private lateinit var movie : LiveData<DetailMovie>
    private lateinit var similiarMovies : LiveData<List<Movie>>
    private lateinit var similiarMovieList : ArrayList<Movie>
    private lateinit var viewComponentHeader: ViewComponentHeader
    private lateinit var viewComponentBody: ViewComponentBody
    private var movieId : Int = 0
    private lateinit var adapter: SimiliarMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Movie Detail"
        viewModel = ViewModelProviders.of(this).get(DetailMovieViewModel::class.java)
        initializeSimiliarMovie()
        initializeComponent()
        observeStateOfView()

        // subscribe movie based on id get from intent.
        movieId = intent!!.getIntExtra(MOVIE_ID, 0)
        subscribeToGetMovieDetail()
        observeMovie()
        subscribeToGetSimiliarMovie()
        observeSimiliarMovie()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val MOVIE_ID : String = "movie_id"
        fun startThisActivity(context : Context, movieId : Int) {
            context.startActivity(Intent(context, DetailMovieActivity::class.java)
                    .putExtra(MOVIE_ID, movieId))
        }
    }

    private fun initializeSimiliarMovie() {
        similiarMovieList = ArrayList()
        grid_similiar_movies.hasFixedSize()
        adapter = SimiliarMovieAdapter(similiarMovieList)
        grid_similiar_movies.adapter = adapter
    }

    private fun initializeComponent() {
        viewComponentHeader = ViewComponentHeader(this)
        viewComponentBody = ViewComponentBody(this)
    }

    private fun subscribeToGetMovieDetail() {
        movie = viewModel.getDetailMovie(movieId)
    }

    private fun subscribeToGetSimiliarMovie() {
        similiarMovies = viewModel.getSimiliarMovie(movieId)
    }

    private fun observeMovie() {
        movie.observe(this, Observer {
            viewComponentHeader.setMovieBackDrop(it!!.backdrop_path)
                    .setMoviePoster(it.poster_path)
                    .setMovieTitle(it.title)
                    .setMovieStudios(it.production_companies[0].name)

            viewComponentBody.setMovieOverview(it.overview)
                    .setMovieGenres(it.genres)
                    .setAverageVote(it.vote_average)
        })
    }

    private fun observeSimiliarMovie() {
        similiarMovies.observe(this, Observer {
            similiarMovieList.addAll(it!!)
            adapter.notifyDataSetChanged()
        })
    }

    private fun observeStateOfView() {
        viewModel.getState().observe(this, Observer {
            when(it) {
                StateOfView.LOADING -> startLoading()
                StateOfView.SUCCESS -> stopLoading(false, null)
                StateOfView.INTERNET_ERROR -> stopLoading(true, "Check your internet connection")
                else -> {
                    stopLoading(true, "Server error.")
                }
            }
        })
    }

    private fun startLoading() {
        loading.startLoading()
    }

    private fun stopLoading(error : Boolean, @Nullable errorMessage : String?) {
        if (error) {
            loading.stopLoadingWithError(errorMessage!!)
            loading.setReloadClickListener(View.OnClickListener {
                subscribeToGetMovieDetail()
            })
        } else {
            loading.stopLoading()
        }
    }
}
