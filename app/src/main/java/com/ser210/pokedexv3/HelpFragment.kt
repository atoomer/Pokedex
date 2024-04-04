package com.ser210.pokedexv3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ser210.pokedexv3.databinding.FragmentHelpBinding

/**
 * Fragment that displays the help screen
 */
class HelpFragment : Fragment() {

    // view binding
    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }
}