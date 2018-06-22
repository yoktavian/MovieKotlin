package com.yoktavian.moviekotlin.ui.detail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yoktavian.moviekotlin.R
import com.yoktavian.moviekotlin.data.model.Movie

class SimiliarMovieAdapter(val movies : ArrayList<Movie>) :
    RecyclerView.Adapter<SimiliarMovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimiliarMovieHolder {
        return SimiliarMovieHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.grid_similiar_movies, parent, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: SimiliarMovieHolder, position: Int) {
        val movie = movies[position]
        holder.setMovieBackdrop(movie.backdrop_path)
                .setMovieTitle(movie.title)
                .setMovieVote(movie.vote_average)
                .setMovieRelease(movie.release_date)

        holder.itemView.setOnClickListener {
            actionGoToMovieDetail(holder.itemView.context, movie.id)
        }
    }

    private fun actionGoToMovieDetail(context : Context, movieId : Int) {
        DetailMovieActivity.startThisActivity(context, movieId)
    }
}