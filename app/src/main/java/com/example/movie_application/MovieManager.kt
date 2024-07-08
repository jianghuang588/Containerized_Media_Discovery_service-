package com.example.movie_application

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class MovieManager {

    private val httpClientConnection: OkHttpClient

    init {

        val constructor = OkHttpClient.Builder()
        val loggingHandler = HttpLoggingInterceptor()
        loggingHandler.level = HttpLoggingInterceptor.Level.BODY
        httpClientConnection = constructor.build()

        constructor.connectTimeout(10, TimeUnit.SECONDS)
        constructor.readTimeout(10, TimeUnit.SECONDS)
        constructor.writeTimeout(10, TimeUnit.SECONDS)
    }

    fun retrieveMovie(): MutableCollection<Movie> {

        val inquire = Request.Builder()
            // https://rapidapi.com/Murad123/api/moviesverse1/playground/apiendpoint_3b7525eb-2e14-4387-8444-eced0ae825d1
            // The api key information is coming from above website.
            .url("https://moviesverse1.p.rapidapi.com/get-by-genre?genre=action")
            .addHeader("x-rapidapi-key", "1c8a45190bmsh3418fb3ab2b3a50p119faejsn1c9e8cf8ac12")
            .addHeader("x-rapidapi-host", "moviesverse1.p.rapidapi.com")
            .build()
        val reply = httpClientConnection.newCall(inquire).execute()
        val container: MutableCollection<Movie> = mutableListOf()
        val returnValue: String? = reply.body?.string()

        if (!returnValue.isNullOrEmpty() && reply.isSuccessful) {

            val javascriptObjectNotation: JSONObject = JSONObject(returnValue)
            val movieData: JSONArray = javascriptObjectNotation.getJSONArray("movies")

            for (index in 0 until movieData.length()) {

                val currMovieInformation = movieData.getJSONObject(index)
                val movieImg = currMovieInformation.getString("posterImage")
                val titleOfMovie = currMovieInformation.getString("title")
                val movieYear = currMovieInformation.getString("year")
                val timeline = currMovieInformation.getString("timeline")
                val description = currMovieInformation.getString("description")
                val rate = currMovieInformation.get("imdbRating")
                val tweet = Movie(
                    item1 = movieImg,
                    item2 = "Movie Title: $titleOfMovie",
                    item3 = "Publish Year: $movieYear",
                    item4 = "Timeline: $timeline",
                    item5 = "Description: $description",
                    item6 = "IMBb Rate: $rate"
                )
                container.add(tweet)
            }
        }
        return container
    }

    fun retrievePopularMovie(): MutableCollection<NewMovie> {

        val inquire = Request.Builder()
            // https://rapidapi.com/Murad123/api/moviesverse1/playground/apiendpoint_2a3fa849-920f-40f6-8696-edede3021e64
            // The api key information is coming from above website.
            .url("https://moviesverse1.p.rapidapi.com/top-250-movies")
            .addHeader("x-rapidapi-key", "1c8a45190bmsh3418fb3ab2b3a50p119faejsn1c9e8cf8ac12")
            .addHeader("x-rapidapi-host", "moviesverse1.p.rapidapi.com")
            .build()
        val reply = httpClientConnection.newCall(inquire).execute()
        val container: MutableCollection<NewMovie> = mutableListOf()
        val returnValue: String? = reply.body?.string()

        if (!returnValue.isNullOrEmpty() && reply.isSuccessful) {

            val javascriptObjectNotation: JSONObject = JSONObject(returnValue)
            val moveInformation: JSONArray = javascriptObjectNotation.getJSONArray("movies")

            for (index in 0 until moveInformation.length()) {

                val currMovieInformation = moveInformation.getJSONObject(index)
                val movieImg = currMovieInformation.getString("image")
                val titleOfMovie = currMovieInformation.getString("title")
                val movieYear = currMovieInformation.getString("year")
                val timeline = currMovieInformation.getString("timeline")
                val rate = currMovieInformation.getString("rating")
                val tweet = NewMovie(
                    item1 = movieImg,
                    item2 = "Movie Title: $titleOfMovie",
                    item3 = "Publish Year: $movieYear",
                    item4 = "Timeline: $timeline",
                    item5 = "Movie Rate: $rate",
                    item6 = ""
                )
                container.add(tweet)
            }
        }
        return container
    }

    fun retrieveUpcomingMovie(): MutableCollection<UpcomingHolder> {

        val inquire = Request.Builder()
            // https://rapidapi.com/Murad123/api/moviesverse1/playground/apiendpoint_95b7a559-25f6-4676-9bc4-7fe7fb1f541e
            // The api key information is coming from above website.
            .url("https://moviesverse1.p.rapidapi.com/upcoming-movies")
            .addHeader("x-rapidapi-key", "1c8a45190bmsh3418fb3ab2b3a50p119faejsn1c9e8cf8ac12")
            .addHeader("x-rapidapi-host", "moviesverse1.p.rapidapi.com")
            .build()
        val reply = httpClientConnection.newCall(inquire).execute()
        val container: MutableCollection<UpcomingHolder> = mutableListOf()
        val returnValue: String? = reply.body?.string()

        if (!returnValue.isNullOrEmpty() && reply.isSuccessful) {

            val javascriptObjectNotation: JSONObject = JSONObject(returnValue)
            val moveInformation: JSONArray = javascriptObjectNotation.getJSONArray("movies")

            for (index in 0 until moveInformation.length()) {

                val currMovieInformation = moveInformation.getJSONObject(index)
                val currDate = currMovieInformation.getString("date")
                val listData = currMovieInformation.getJSONArray("list")

                for (secondIndex in 0 until listData.length()) {

                    val movieInformation = listData.getJSONObject(secondIndex)
                    val title = movieInformation.getString("title")
                    val movieImage = if (movieInformation.has("image")) movieInformation.getString("image") else "image == null"
                    val movieType = if (movieInformation.getJSONArray("categories").length() > 0) movieInformation.getJSONArray("categories").getString(0) else "movieType == null"
                    val popularActor = if (movieInformation.getJSONArray("staring").length() > 0) movieInformation.getJSONArray("staring").getString(0) else "popularActor == null"
                    val tweet = UpcomingHolder(
                        item1 = movieImage,
                        item2 = "Movie Title: $title",
                        item3 = "Movie Date: $currDate",
                        item4 = "Movie Type: $movieType",
                        item5 = "Popular Actor: $popularActor",
                        item6 = ""
                    )
                    container.add(tweet)
                }
            }
        }
        return container
    }

    @SuppressLint("SuspiciousIndentation")
    fun retrieveSearchMovie(searchText: String): MutableCollection<UpcomingHolder> {
        // https://rapidapi.com/Murad123/api/moviesverse1/playground/apiendpoint_c305d5fa-c022-4c48-8498-dceada370c18
        // The api information come from above website
        val json = "{\"query\":\"$searchText\"}"
        val body = RequestBody.create(null, json)

        val inquire = Request.Builder()
            .url("https://moviesverse1.p.rapidapi.com/search-movies-by-query")
            .post(body)
            .addHeader("x-rapidapi-key", "1c8a45190bmsh3418fb3ab2b3a50p119faejsn1c9e8cf8ac12")
            .addHeader("x-rapidapi-host", "moviesverse1.p.rapidapi.com")
            .addHeader("Content-Type", "application/json")
            .build()
        val reply = httpClientConnection.newCall(inquire).execute()
        val container: MutableCollection<UpcomingHolder> = mutableListOf()
        val returnValue: String? = reply.body?.string()

        if (!returnValue.isNullOrEmpty() && reply.isSuccessful) {
            val javascriptObjectNotation: JSONObject = JSONObject(returnValue)
            val moveInformation = javascriptObjectNotation.getJSONArray("d")
            for (index in 0 until moveInformation.length()) {

                val currMovieInformation = moveInformation.getJSONObject(index)
                val currDate = currMovieInformation.getJSONObject("i")
                val imageUrl = currDate.getString("imageUrl")
                val movieTitle = currMovieInformation.getString("l")
                val qID = currMovieInformation.getString("qid")
                val mainActor = currMovieInformation.getString("s")
                val splitMainCharacter = mainActor.split(",").firstOrNull() ?: "nothing exist"
                val movieRank = currMovieInformation.getString("rank")

                val tweet = UpcomingHolder(
                        item1 = imageUrl,
                        item2 = "Movie Title: $movieTitle",
                        item3 = "Media Type: $qID",
                        item4 = "Main Actor: $splitMainCharacter",
                        item5 = "Movie Rank: $movieRank",
                        item6 = ""
                    )
                    container.add(tweet)
                }
            }
            return container
    }

}