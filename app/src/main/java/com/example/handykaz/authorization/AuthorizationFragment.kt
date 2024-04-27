package com.example.handykaz.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.handykaz.R
import com.example.handykaz.databinding.FragmentAuthorizationBinding

class AuthorizationFragment : Fragment() {

    private lateinit var binding: FragmentAuthorizationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)

        binding.goToSignUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_signUpFragment)
        }

        binding.goToSignInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_signInFragment)
        }

        return binding.root
    }

}