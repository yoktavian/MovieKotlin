package com.yoktavian.moviekotlin.ui.home

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.grid_movies.view.*

class MovieHolder(val view : View) : RecyclerView.ViewHolder(view) {
    fun setMovieImage(url : String) : MovieHolder {
        Glide.with(view.context)
                .load(String.format("http://image.tmdb.org/t/p/w185/$url"))
                .into(view.movie_image)
        return this
    }

    fun setMovieTitle(title : String) : MovieHolder {
        view.movie_title.text = title
        return this
    }

    fun setMovieVote(vote : Float) : MovieHolder {
        view.movie_rating.text = vote.toString()
        return this
    }

    fun setMovieOverview(overview : String) : MovieHolder {
        view.movie_overview.text = overview
        return this
    }

    fun setMovieRelease(release : String) : MovieHolder {
        view.movie_release.text = String.format("Release date : $release")
        return this
    }
}