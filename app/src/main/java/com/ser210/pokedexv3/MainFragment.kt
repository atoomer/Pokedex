package com.ser210.pokedexv3

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ser210.pokedexv3.databinding.FragmentMainBinding
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

/**
 * Fragment that displays the main list screen
 */
class MainFragment : Fragment() {
    // variables to keep track of the recycler view and adapter
    lateinit var pokeRecycler: RecyclerView
    lateinit var pokeAdapter: PokemonAdapter

    // view binding
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set up recycler view and adapter

        pokeAdapter = PokemonAdapter(requireContext(), Navigation.findNavController(view))
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.adapter = pokeAdapter


        // call the API to get the list of all pokemon
        val pokeApiInterface = ApiInterface.create().getAllPokemon(1026)

        // enqueue Pokemon call
        if (pokeApiInterface != null) {
            pokeApiInterface.enqueue(object : Callback<Pokemon?> {
                override fun onResponse(
                    call: Call<Pokemon?>,
                    response: Response<Pokemon?>

                ) {
                    if (response.isSuccessful) {
                        val results = response.body()?.results
                        if (results != null) {

                            // set the list of pokemon to the adapter
                            pokeAdapter.setPokemonListItems(results)
                        }

                    // error handling for null response
                    } else {
                        Log.d(
                            "onResponse",
                            "Stupid Error ${response.code()} ${response.errorBody()?.string()}"
                        )
                    }

                }

                // error handling for failure
                override fun onFailure(call: Call<Pokemon?>, t: Throwable) {
                    if (t != null) {
                        Toast.makeText(
                            requireContext(), t.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        t.message?.let { Log.d("onStupidFailure", it) }
                    }
                }
            })
        }


    }
}