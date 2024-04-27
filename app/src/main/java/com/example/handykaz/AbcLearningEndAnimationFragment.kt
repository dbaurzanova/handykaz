package com.example.handykaz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.example.handykaz.databinding.FragmentAbcLearningEndAnimationBinding

class AbcLearningEndAnimationFragment : Fragment() {

    private lateinit var binding: FragmentAbcLearningEndAnimationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAbcLearningEndAnimationBinding.inflate(inflater, container, false)

        binding.apply {

            lottieEndAnim.setMinAndMaxProgress(0.0f, 1.0f)
            lottieEndAnim.repeatCount = LottieDrawable.INFINITE
            lottieEndAnim.repeatMode = LottieDrawable.RESTART
            lottieEndAnim.playAnimation()

            finishBtn.setOnClickListener{
                findNavController().navigate(R.id.action_abcLearningEndAnimationFragment_to_abcFragment)
            }
        }

        return binding.root
    }


}