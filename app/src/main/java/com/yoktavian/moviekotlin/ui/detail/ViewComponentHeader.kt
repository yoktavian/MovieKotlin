package com.yoktavian.moviekotlin.ui.detail

import com.bumptech.glide.Glide
import com.yoktavian.moviekotlin.data.model.Production
import kotlinx.android.synthetic.main.activity_detail_movie.*

class ViewComponentHeader(private val activity: DetailMovieActivity) {
    fun setMovieBackDrop(url_backdrop : String?) : ViewComponentHeader {
        if (url_backdrop != null) {
            Glide.with(activity)
                    .load(String.format("https://image.tmdb.org/t/p/w1400_and_h450_face/$url_backdrop"))
                    .into(activity.movie_backdrop)
        }
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
        when {
            studios.isNotEmpty() -> activity.movie_studios.text = studios[0].name
        }
        return this
    }
}