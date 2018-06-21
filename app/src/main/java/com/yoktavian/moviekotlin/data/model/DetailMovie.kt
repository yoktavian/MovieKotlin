package com.yoktavian.moviekotlin.data.model

class DetailMovie(val backdrop_path : String, val poster_path : String,
                  val title : String, val status : String, val overview : String,
                  val production_companies : List<Production>, val genres : List<Genres>,
                  val vote_average : Float)

class Production(val name : String)

class Genres(val name : String)