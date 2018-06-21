package com.yoktavian.moviekotlin.ui.detail

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.yoktavian.moviekotlin.R
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w1400_and_h450_face/pmZtj1FKvQqISS6iQbkiLg5TAsr.jpg")
            .into(movie_backdrop)

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w300_and_h450_bestv2/vSNxAJTlD0r02V9sPYpOjqDZXUK.jpg")
                .into(movie_poster)

        Log.d("Movie id ", intent.getIntExtra(MOVIE_ID, 0).toString())
    }

    companion object {
        val MOVIE_ID : String = "movie_id"
        fun startThisActivity(context : Context, movieId : Int) {
            context.startActivity(Intent(context, DetailMovieActivity::class.java)
                    .putExtra(MOVIE_ID, movieId))
        }
    }
}
