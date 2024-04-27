package com.example.handykaz.authorization

import CyrillicInputFilter
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.handykaz.R
import com.example.handykaz.databinding.FragmentSignUpBinding
import com.google.android.material.textfield.TextInputLayout

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var editTextUsername: EditText
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var editTextPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.signInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_abcFragment)
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextUsername = binding.editTextUserName
        editTextUsername.typeface = Typeface.DEFAULT

        editTextUsername.filters = arrayOf(CyrillicInputFilter())

        editTextUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this example
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for this example
            }

            override fun afterTextChanged(s: Editable?) {
                updateDrawable()
            }
        })

       // textInputLayoutPassword = binding.textInputLayoutPassword
        editTextPassword = binding.editTextUserPassword

        // Set an OnClickListener for the start icon (eye icon)
        /*
        textInputLayoutPassword.setStartIconOnClickListener {
            togglePasswordVisibility()
        }
         */
        // Initialize the drawable
        updateDrawable()
    }

    private fun updateDrawable() {
        val isValidCyrillic = isInputValidCyrillic()

        val drawableRes = if (isValidCyrillic) R.drawable.auth_check_mark_draw else R.drawable.auth_x_mark_draw
        val drawable = resources.getDrawable(drawableRes)

        // Set drawable bounds
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)

        // Set the compound drawables (left, top, right, bottom) only if there is text
        if (editTextUsername.text.isNotEmpty()) {
            editTextUsername.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
        } else {
            // If there is no text, clear the drawables
            editTextUsername.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
        }
    }

    private fun isInputValidCyrillic(): Boolean {
        val cyrillicPattern = "[а-яА-Я]*".toRegex()
        return cyrillicPattern.matches(editTextUsername.text)
    }

    private fun togglePasswordVisibility() {
        val inputType = editTextPassword.inputType

        // Toggle between password and text visibility
        editTextPassword.inputType =
            if (inputType == android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                android.text.InputType.TYPE_CLASS_TEXT or
                        android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }

        // Move the cursor to the end of the text
        editTextPassword.setSelection(editTextPassword.text.length)

        // Toggle the start icon (eye icon) drawable
        val startIconDrawableRes =
            if (inputType == android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                R.drawable.auth_eye_disable_mark
            } else {
                R.drawable.auth_eye_mark
            }
        textInputLayoutPassword.setStartIconDrawable(startIconDrawableRes)
    }
}