package com.ser210.pokedexv3

import com.google.gson.annotations.SerializedName

//data class for list of all Pokemon
data class Pokemon(
    val count: Int,
    val next: String,
    val previous: String,
    val results: ArrayList<Results>
)

// data class for each Pokemon in list
data class Results(
    val name: String,
    val url: String
)

// data class for individual Pokemon details
data class PokemonDetail(
    val height: Int,
    val id: Int,
    val name: String,
    val types: ArrayList<Types>,
    val weight: Int,
    val sprites: Sprites
)

// data class for Pokemon types
data class Types(
    val slot: Int,
    val type: Type
)

// data class for each type
data class Type(
    val name: String
)

// data class for Pokemon sprites
data class Sprites(
    @SerializedName("front_default") val frontDefault: String
)

