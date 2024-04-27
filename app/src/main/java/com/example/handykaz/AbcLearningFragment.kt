package com.example.handykaz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.handykaz.databinding.FragmentAbcLearningBinding
import com.example.handykaz.resources.VideoClass

class AbcLearningFragment : Fragment() {

    private lateinit var binding: FragmentAbcLearningBinding

    lateinit var signPicturesArray : Array<Int>
    lateinit var signNameArray : Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAbcLearningBinding.inflate(inflater, container, false)

        signPicturesArray = arrayOf(
            R.drawable.gesture_image,
            R.drawable.gesture_image,
            R.drawable.gesture_image,
            R.drawable.gesture_image,
            R.drawable.gesture_image
        )

        signNameArray = arrayOf(
            "Г",
            "Г",
            "Г",
            "Г",
            "Г"
        )

        val progressBar = binding.progressBarHorizontal
        progressBar.max = signPicturesArray.size
        progressBar.progress = 0

        var id: Int = 0

        binding.gestureImage.setImageResource(signPicturesArray[id])
        binding.gestureName.text = signNameArray[id]

        binding.nextBtn.setOnClickListener{
            id += 1
            if (id < signPicturesArray.size) {
                binding.gestureImage.setImageResource(signPicturesArray[id])
                binding.gestureName.text = signNameArray[id]
                progressBar.progress = id
            } else{
                findNavController().navigate(R.id.action_abcLearningFragment_to_abcLearningEndAnimationFragment)
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root

    }

}