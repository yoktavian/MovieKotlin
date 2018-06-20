package com.yoktavian.moviekotlin.data.model

class Movie(val vote_count : Int, val id : Int, val vote_average : Float, val title : String,
            val poster_path : String, val genre_ids : List<Int>, val overview : String,
            val release_date : String, val backdrop_path : String)