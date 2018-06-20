package com.yoktavian.moviekotlin.ui.home

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.grid_movies.view.*

class MovieHolder(val view : View) : RecyclerView.ViewHolder(view) {
    fun setMovieImage(url : String) : MovieHolder {
        Glide.with(view.context).load(url).into(view.movie_image)
        return this
    }

    fun setMovieTitle(title : String) : MovieHolder {
        view.movie_title.text = title
        return this
    }

    fun setMovieGenre(genres : List<String>) : MovieHolder {
        view.movie_genre.text = genres[0]
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
        view.movie_release.text = release
        return this
    }
}