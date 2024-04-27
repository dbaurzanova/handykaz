package com.example.handykaz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.handykaz.databinding.FragmentGamesBinding

class GamesFragment : Fragment() {

    private lateinit var binding: FragmentGamesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGamesBinding.inflate(inflater, container, false)

        binding.apply {

            playBtn.setOnClickListener{
                findNavController().navigate(R.id.action_gamesFragment_to_cameraGameFragment)

            }
        }

        return binding.root
    }

}