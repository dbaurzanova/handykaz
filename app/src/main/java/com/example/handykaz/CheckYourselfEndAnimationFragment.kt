package com.example.handykaz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.example.handykaz.databinding.FragmentAbcLearningEndAnimationBinding
import com.example.handykaz.databinding.FragmentCheckYourselfEndAnimationBinding


class CheckYourselfEndAnimationFragment : Fragment() {

    private lateinit var binding: FragmentCheckYourselfEndAnimationBinding

    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            score = it.getInt("score")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckYourselfEndAnimationBinding.inflate(inflater, container, false)

        binding.apply {

            lottieEndAnim.setMinAndMaxProgress(0.0f, 1.0f)
            lottieEndAnim.repeatCount = LottieDrawable.INFINITE
            lottieEndAnim.repeatMode = LottieDrawable.RESTART
            lottieEndAnim.playAnimation()

            textScoreInt.text = "$score/5"
            textScorePercent.text = "${score*20}%"

            finishBtn.setOnClickListener{
                findNavController().navigate(R.id.action_checkYourselfEndAnimationFragment_to_abcFragment)
            }
        }

        return binding.root
    }

}