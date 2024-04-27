package com.example.handykaz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.example.handykaz.databinding.FragmentCheckYourselfEndAnimationBinding
import com.example.handykaz.databinding.FragmentGameEndAnimationBinding

class GameEndAnimationFragment : Fragment() {

    private lateinit var binding: FragmentGameEndAnimationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameEndAnimationBinding.inflate(inflater, container, false)

        binding.apply {

            lottieEndAnim.setMinAndMaxProgress(0.0f, 1.0f)
            lottieEndAnim.repeatCount = LottieDrawable.INFINITE
            lottieEndAnim.repeatMode = LottieDrawable.RESTART
            lottieEndAnim.playAnimation()

            finishBtn.setOnClickListener{
                findNavController().navigate(R.id.action_gameEndAnimationFragment_to_gamesFragment)
            }
        }

        return binding.root
    }

}