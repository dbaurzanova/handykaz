package com.example.handykaz

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.handykaz.databinding.FragmentProfileSettingsBinding

class ProfileSettingsFragment : Fragment() {
    private lateinit var binding: FragmentProfileSettingsBinding
    private lateinit var alertDialog: AlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileSettingsBinding.inflate(inflater, container, false)

        binding.changePasswordLayout.setOnClickListener {
            findNavController().navigate(R.id.action_profileSettingsFragment_to_changePasswordFragment)
        }

        binding.deleteProfileLayout.setOnClickListener {
            showAlertDialog()
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }


        return binding.root
    }


    private fun showAlertDialog() {

        activity?.runOnUiThread {
            // Inflate the custom layout
            val dialogView = layoutInflater.inflate(R.layout.alert_dialog_profile, null)

            // Find buttons in the custom layout
            val deleteButton = dialogView.findViewById<Button>(R.id.deleteButton)
            val notDeleteButton = dialogView.findViewById<Button>(R.id.notDeleteButton)

            // Build the AlertDialog with the custom layout
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
                .setView(dialogView)


            deleteButton.setOnClickListener {
                alertDialog.dismiss()
            }

            notDeleteButton.setOnClickListener {
                alertDialog.dismiss()
            }

            // Create the AlertDialog
            alertDialog = alertDialogBuilder.create()

            // Show the AlertDialog
            alertDialog.show()
        }
    }
}