package com.yoktavian.moviekotlin.ui.detail

import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.CheckBox
import com.yoktavian.moviekotlin.R
import com.yoktavian.moviekotlin.data.model.Genres
import kotlinx.android.synthetic.main.activity_detail_movie.*

class ViewComponentBody(private val activity: DetailMovieActivity) {
    fun setMovieOverview(overview : String) : ViewComponentBody {
        activity.movie_overview.text = overview
        return this
    }

    fun setMovieGenres(genres : List<Genres>) : ViewComponentBody {
        val container : ViewGroup = activity.genre_container
        for (genre : Genres in genres) {
            val genreCheckbox = CheckBox(activity)
            genreCheckbox.text = genre.name
            genreCheckbox.isChecked = true
            genreCheckbox.setTextColor(ContextCompat.getColor(activity, R.color.colorDarkGrey))
            container.addView(genreCheckbox)
        }
        return this
    }

    fun setAverageVote(vote : Float) : ViewComponentBody {
        activity.movie_vote.text = vote.toString()
        return this
    }
}