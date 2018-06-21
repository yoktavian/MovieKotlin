package com.yoktavian.moviekotlin.ui.home

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yoktavian.moviekotlin.R
import com.yoktavian.moviekotlin.data.model.Movie

class MovieAdapter(private val movies: ArrayList<Movie>) : Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.grid_movies, parent, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieHolder) {
            val movie = movies[position]
            holder.setMovieImage(movie.poster_path)
                    .setMovieTitle(movie.title)
                    .setMovieOverview(movie.overview)
                    .setMovieVote(movie.vote_average)
                    .setMovieRelease(movie.release_date)
        }
    }
}