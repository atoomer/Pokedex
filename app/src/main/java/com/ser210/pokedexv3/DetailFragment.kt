package com.ser210.pokedexv3

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bumptech.glide.Glide
import com.ser210.pokedexv3.databinding.FragmentDetailBinding
import com.ser210.pokedexv3.databinding.FragmentMainBinding


/**
 * Fragment for displaying pokemon details
 */
class DetailFragment : Fragment() {

    // variables that will store pokemon details from api
    private var height: Int = 0
    private var name: String = ""
    private var types: ArrayList<Types> = ArrayList()
    private var weight: Int = 0
    private var typeStr: String = ""
    private var imageUrl: String = ""

    // view binding
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get id number from bundle
        val id = DetailFragmentArgs.fromBundle(requireArguments()).idNum

        // bind views
        val idView = binding.idDetail
        val nameView = binding.nameDetail
        val heightView = binding.heightDetail
        val weightView = binding.weightDetail
        val typeView = binding.typeDetail
        val imgView = binding.imageDetail

        // get pokemon details from api
        val pokeApiInterface = ApiInterface.create().getPokemonDetail(id)

        // get shared preferences for setting text color
        val sharedPreferences = requireActivity().getSharedPreferences("appPreferences", Context.MODE_PRIVATE)
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == "isTextDark") {
                val isTextDark = sharedPreferences.getBoolean("isTextDark", false)
                val textColor = if (isTextDark) Color.WHITE else Color.DKGRAY
                idView.setTextColor(textColor)
                nameView.setTextColor(textColor)
                heightView.setTextColor(textColor)
                weightView.setTextColor(textColor)
                typeView.setTextColor(textColor)

            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        // enqueue PokemonDetail call
        if (pokeApiInterface != null) {
            pokeApiInterface.enqueue(object : Callback<PokemonDetail?> {
                override fun onResponse(
                    call: Call<PokemonDetail?>,
                    response: Response<PokemonDetail?>

                ) {
                    if (response.isSuccessful) {
                        // sets response body tp corresponding variables
                        height = response.body()?.height!!
                        name = response.body()?.name!!.first().uppercase() + response.body()?.name!!.substring(1)
                        types = response.body()?.types!!
                        weight = response.body()?.weight!!
                        imageUrl = response.body()?.sprites?.frontDefault!!
                        for (type in types) {
                            typeStr += (type.type.name.first().uppercase() + type.type.name.substring(1) + " ")
                        }

                        // set text views and image view
                        idView.text = getString(R.string.id_text, id)
                        nameView.text = getString(R.string.name_text, name)
                        heightView.text = getString(R.string.height_text, height)
                        weightView.text = getString(R.string.weight_text, weight)
                        typeView.text = getString(R.string.type_text, typeStr)
                        Glide.with(requireContext())
                            .load(imageUrl)
                            .into(imgView)

        // error handling for null response
                    } else {
                        Log.d(
                            "onResponse",
                            "Stupid Error ${response.code()} ${response.errorBody()?.string()}"
                        )
                    }

                }

        // error handling for failure
                override fun onFailure(call: Call<PokemonDetail?>, t: Throwable) {
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

