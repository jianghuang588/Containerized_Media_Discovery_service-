package com.example.movie_application

import java.io.Serializable

data class NewMovie (
    val item1: String,
    val item2: String,
    val item3: String,
    val item4: String,
    val item5: String,
    val item6: String

) : Serializable {
    constructor() : this("", "", "","", "", "")
}