package com.example.handykaz

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import com.example.handykaz.databinding.FragmentAbcCheckYourselfBinding

class AbcCheckYourselfFragment : Fragment() {

    private lateinit var binding: FragmentAbcCheckYourselfBinding
    private var selectedButton: Button? = null
    private var selectedLetter: String? = null

    private lateinit var progressBar: ProgressBar

    lateinit var signPicturesArray: Array<Int>
    lateinit var signNameArray: Array<String>
    private var score: Int = 0

    private var currentImageIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAbcCheckYourselfBinding.inflate(inflater, container, false)

        signPicturesArray = arrayOf(
            R.drawable.gesture_image,
            R.drawable.gesture_image,
            R.drawable.gesture_image,
            R.drawable.gesture_image,
            R.drawable.gesture_image
        )

        signNameArray = arrayOf(
            "А",
            "Ә",
            "Б",
            "В",
            "Г",
            "Ғ"
        )

        progressBar = binding.progressBarHorizontal
        progressBar.max = signPicturesArray.size
        progressBar.progress = 0

        binding.gestureImage.setImageResource(signPicturesArray[currentImageIndex])
        // Set click listeners for letter buttons
        setButtonClickListener()

        // Set click listener for next button
        binding.nextBtn.setOnClickListener {
            if (selectedButton != null) {
                nextGesture()
                if (selectedLetter == signNameArray[3]){
                    score++
                }
                Log.d(TAG, "my: $selectedLetter, answer: $signNameArray[3]")
                Log.d(TAG, "score: $score")
            }
        }

        binding.skipBtn.setOnClickListener {
            nextGesture()
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }


    private fun setButtonClickListener() {
        val letterButtons = listOf(
            binding.buttonOption1,
            binding.buttonOption2,
            binding.buttonOption3,
            binding.buttonOption4,
            binding.buttonOption5,
            binding.buttonOption6
        )

        // Set click listeners for letter buttons
        for (button in letterButtons) {
            button.setOnClickListener {
                // Reset color for all buttons
                for (b in letterButtons) {
                    b.backgroundTintList = context?.getColorStateList(android.R.color.white)
                }

                // Change color of clicked button to purple
                button.backgroundTintList = context?.getColorStateList(R.color.main_baby_purple)

                // Reset color of previously selected button if any
                selectedButton?.backgroundTintList = context?.getColorStateList(android.R.color.white)

                // Set the selected button
                selectedButton = button
                selectedLetter = button.text.toString()
            }
        }

    }

    private fun nextGesture(){
        if (currentImageIndex < signPicturesArray.size - 1) {
            currentImageIndex++
            binding.gestureImage.setImageResource(signPicturesArray[currentImageIndex])
            progressBar.progress = currentImageIndex
        } else {
            val action = AbcCheckYourselfFragmentDirections.actionAbcCheckYourselfFragmentToCheckYourselfEndAnimationFragment(score)
            findNavController().navigate(action)
        }
        selectedButton?.backgroundTintList = context?.getColorStateList(android.R.color.white)
        selectedButton = null
    }

}