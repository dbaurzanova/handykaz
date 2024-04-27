package com.example.handykaz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.handykaz.databinding.FragmentAbcBinding

class AbcFragment : Fragment() {

    private lateinit var binding: FragmentAbcBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAbcBinding.inflate(inflater, container, false)

        binding.goToAbcLearning.setOnClickListener {
            findNavController().navigate(R.id.action_abcFragment_to_abcLearningFragment)
        }

        binding.goToAbcCheckYourself.setOnClickListener {
            findNavController().navigate(R.id.action_abcFragment_to_abcCheckYourselfFragment)
        }

        return binding.root
    }

}