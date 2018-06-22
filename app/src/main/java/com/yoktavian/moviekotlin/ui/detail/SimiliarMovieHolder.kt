package com.yoktavian.moviekotlin.ui.detail

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.grid_similiar_movies.view.*

class SimiliarMovieHolder(view : View) : RecyclerView.ViewHolder(view) {
    fun setMovieBackdrop(url : String?) : SimiliarMovieHolder {
        if (url != null) {
            Glide.with(itemView.context).load(String.format(
                    "https://image.tmdb.org/t/p/w1400_and_h450_face/$url"))
                    .into(itemView.movie_image)
        }
        return this
    }

    fun setMovieTitle(title : String) : SimiliarMovieHolder {
        itemView.movie_title.text = title
        return this
    }

    fun setMovieVote(vote : Float) : SimiliarMovieHolder {
        itemView.movie_rating.text = vote.toString()
        return this
    }

    fun setMovieRelease(release : String) : SimiliarMovieHolder {
        itemView.movie_release.text = release
        return this
    }
}