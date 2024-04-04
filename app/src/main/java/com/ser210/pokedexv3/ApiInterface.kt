package com.ser210.pokedexv3


import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface for the API calls
 */
interface ApiInterface {


    // calls list of all pokemon
    @GET("pokemon/")
    fun getAllPokemon(@Query("limit") limit: Int) : Call<Pokemon?>?

    // calls details of a specific pokemon
    @GET("pokemon/{id}/")
    fun getPokemonDetail(@Path("id") id: Int) : Call<PokemonDetail?>?

    companion object {
        var BASE_URL = "https://pokeapi.co/api/v2/"

        // creates the api interface object
        fun create() : ApiInterface {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}