package com.example.movie_application

import java.io.Serializable

data class FirebaseHolder (

    val email: String,
    val id: String,
    val rating: String

) : Serializable {
    constructor() : this("", "", "")
}