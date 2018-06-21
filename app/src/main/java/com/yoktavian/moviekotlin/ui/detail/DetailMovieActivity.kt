package com.yoktavian.moviekotlin.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.yoktavian.moviekotlin.R
import com.yoktavian.moviekotlin.data.model.DetailMovie
import com.yoktavian.moviekotlin.data.model.Production
import com.yoktavian.moviekotlin.viewmodel.DetailMovieViewModel
import kotlinx.android.synthetic.main.activity_detail_movie.*
import java.lang.Exception

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var viewModel : DetailMovieViewModel
    private lateinit var movie : LiveData<DetailMovie>
    private lateinit var viewComponentHeader: ViewComponentHeader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Movie Detail"
        viewModel = ViewModelProviders.of(this).get(DetailMovieViewModel::class.java)
        viewComponentHeader = ViewComponentHeader(this)
        // subscribe movie based on id get from intent.
        subscribeMovie(intent.getIntExtra(MOVIE_ID, 0))
        observeMovie()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val MOVIE_ID : String = "movie_id"
        fun startThisActivity(context : Context, movieId : Int) {
            context.startActivity(Intent(context, DetailMovieActivity::class.java)
                    .putExtra(MOVIE_ID, movieId))
        }
    }

    private fun subscribeMovie(movieId : Int) {
        movie = viewModel.getDetailMovie(movieId)
    }

    private fun observeMovie() {
        movie.observe(this, Observer {
            viewComponentHeader.setMovieBackDrop(it!!.backdrop_path)
                    .setMoviePoster(it.poster_path)
                    .setMovieTitle(it.title)
                    .setMovieStudios(it.production_companies)
        })
    }

    class ViewComponentHeader(private val activity: DetailMovieActivity) {
        fun setMovieBackDrop(url_backdrop : String) : ViewComponentHeader {
            Glide.with(activity)
                    .load(String.format("https://image.tmdb.org/t/p/w1400_and_h450_face/$url_backdrop"))
                    .listener(object : RequestListener<String, GlideDrawable> {
                        override fun onException(e: Exception?, model: String?,
                            target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: GlideDrawable?,
                            model: String?, target: Target<GlideDrawable>?,
                            isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                            activity.backdrop_loading.visibility = View.GONE
                            return false
                        }

                    })
                    .into(activity.movie_backdrop)
            return this
        }

        fun setMoviePoster(url_poster : String) : ViewComponentHeader {
            Glide.with(activity)
                    .load(String.format("https://image.tmdb.org/t/p/w300_and_h450_bestv2/$url_poster"))
                    .into(activity.movie_poster)
            return this
        }

        fun setMovieTitle(title : String) : ViewComponentHeader {
            activity.movie_title.text = title
            return this
        }

        fun setMovieStudios(studios : List<Production>) : ViewComponentHeader {
            activity.movie_studios.text = studios[0].name
            return this
        }
    }
}
