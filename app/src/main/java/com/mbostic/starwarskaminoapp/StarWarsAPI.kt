package com.mbostic.starwarskaminoapp

import com.mbostic.starwarskaminoapp.planetDetails.LikeResponse
import com.mbostic.starwarskaminoapp.planetDetails.Planet
import com.mbostic.starwarskaminoapp.residentDetails.Resident
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StarWarsAPI {

    companion object {

        private const val BASE_URL = "https://private-anon-4004315e79-starwars2.apiary-mock.com"

        /** Builds and returns the StarWarsAPI */
        fun getAPI(): StarWarsAPI {
            return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(OkHttpClient.Builder().build())
                .build().create(StarWarsAPI::class.java)
        }
    }

    /** Fetches the planet with the given [id] */
    @GET("/planets/{id}")
    fun getPlanet(@Path("id") id: Int): Call<Planet>

    /** Fetches the resident with the given [residentId] */
    @GET("residents/{id}")
    fun getResident(@Path("id") residentId: Int): Call<Resident>

    /** Fetches the number of likes for the planet with the given [planetId] */
    @POST("/planets/{id}/like")
    fun likePlanet(@Path("id") planetId: Int): Call<LikeResponse>

}