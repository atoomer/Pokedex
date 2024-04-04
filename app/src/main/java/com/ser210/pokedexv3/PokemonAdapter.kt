package com.ser210.pokedexv3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for the RecyclerView in the MainFragment
 * @param context the context of the app
 * @param navController the NavController for the app
 */


// arrayList to store Pokemon
var pokemonList : ArrayList<Results> = ArrayList()

class PokemonAdapter(private val context: Context, var navController: NavController): RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        // inflate the layout
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return PokemonViewHolder(adapterLayout, context)
    }

    // bind the data to its spot in the recycler view
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(position)
    }

    // return the number of items in the list
    override fun getItemCount() = pokemonList.size

    // set the list of Pokemon
    fun setPokemonListItems(pokemon: ArrayList<Results>) {
        pokemonList = pokemon
        notifyDataSetChanged()
    }

    // inner class to hold the views
    inner class PokemonViewHolder(view: View, private val context: Context): RecyclerView.ViewHolder(view){

        private val nameView: TextView = view.findViewById(R.id.listName)
        private val idView: TextView = view.findViewById(R.id.idView)
        var id = 0

        init {
            // click listener for each item in the recycler view
            itemView.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(id)
                navController.navigate(action)
            }
        }
        // bind the data to the views
        fun bind(position: Int){
            val currPokemon = pokemonList.get(position)
            id = pokemonList[position].toString().substringAfter("pokemon/").substringBefore("/").toInt()
            nameView.text = currPokemon.name
            idView.text = id.toString()
        }

    }

}