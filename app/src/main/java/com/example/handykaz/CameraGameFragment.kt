package com.example.handykaz
import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.handykaz.databinding.FragmentCameraGameBinding
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraGameFragment : Fragment() {

    private lateinit var binding: FragmentCameraGameBinding
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var imageCapture: ImageCapture
    private val lettersArray = arrayOf("С", "О", "Ө", "К", "Қ")
    private var shuffledLetters = mutableListOf<String>()
    private var currentLetterIndex = 0
    private lateinit var currentLetter: String
    private lateinit var alertDialog: AlertDialog
    private var shouldCaptureImage = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraGameBinding.inflate(inflater, container, false)
        cameraExecutor = Executors.newSingleThreadExecutor()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (hasRequiredPermissions()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE_PERMISSIONS
            )
        }

       /* binding.checkBtn.setOnClickListener {
            if (::imageCapture.isInitialized) {
                takePhoto()
            } else {
                // Handle case where camera is not initialized
                Log.e("Camera Initialization", "Camera is not initialized yet")
            }
        }*/

        binding.finishBtn.setOnClickListener {
            findNavController().navigate(R.id.action_cameraGameFragment_to_gameEndAnimationFragment)
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        // Shuffle the letters array initially
        shuffledLetters = lettersArray.toMutableList().shuffled() as MutableList<String>

        // Show the first letter
        showNextLetter()

 }

    private fun showNextLetter() {
        // Check if all letters have been shown
        if (currentLetterIndex >= shuffledLetters.size) {
            // Shuffle the letters array again
            shuffledLetters.shuffle()
            // Reset the current index
            currentLetterIndex = 0
        }

        // Get the next letter to show
        val nextLetter = shuffledLetters[currentLetterIndex]

        // Show the letter in the TextView
        binding.letterTextView.text = nextLetter
        currentLetter = nextLetter

        // Increment the current index for the next letter
        currentLetterIndex++
    }

    private fun hasRequiredPermissions() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (hasRequiredPermissions()) {
                startCamera()
            } else {
                Log.e(TAG, "Camera permission not granted")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // ImageCapture
            imageCapture = ImageCapture.Builder().build()
            // Preview
            val preview = Preview.Builder().build()
                .also {
                    it.setSurfaceProvider(binding.cameraView.surfaceProvider)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

                // Now that the camera is started, start capturing images
                startImageCapture()

            } catch (exc: Exception) {
                // Handle exceptions
                Log.e("Error Starting Camera", "Error starting camera", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }


    private fun startImageCapture() {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                takePhoto()
            }
        }, 0, 1500)
    }

    private fun takePhoto() {
        if (!shouldCaptureImage) return // Check if image capturing is allowed

        val imageCapture = imageCapture ?: return
        val photoFile: File // Declare photoFile here

        if (isAdded) {
            photoFile = File(
                requireContext().filesDir,
                "photos.jpg"
            )
        } else {
            // Handle the situation when the fragment is not attached to an activity
            return
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Take the picture
        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.e("Image Capture", "Photo capture failed: ${exception.message}", exception)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    // Photo capture succeeded
                    if (shouldCaptureImage) { // Check again before sending to server
                        sendPhotoToServer(photoFile)
                    }
                }
            }
        )
    }


    private fun sendPhotoToServer(photoFile: File) {
        // Check if image capturing is allowed
        if (!shouldCaptureImage) return

        val client = OkHttpClient()
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file",
                "photo.jpg",
                photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            )
            .build()

        val request = Request.Builder()
            .url(SERVER_URL)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Failed to send photo to server: ${e.message}", e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val jsonString = responseBody.string()
                    val result = decodeResponse(jsonString)
                    Log.d(TAG, "Received result from server: $result")
                    if (result == currentLetter) {
                        showAlertDialog()
                    }
                }
            }
        })
    }


    private fun decodeResponse(jsonString: String): String {
        val gson = Gson()
        val response = gson.fromJson(jsonString,MLResponse::class.java)
        return response.result ?: "wrong"
    }

    private fun showAlertDialog() {
        // Stop image capturing and sending process
        shouldCaptureImage = false

        activity?.runOnUiThread {
            // Inflate the custom layout
            val dialogView = layoutInflater.inflate(R.layout.alert_dialog_game, null)

            // Find buttons in the custom layout
            val nextButton = dialogView.findViewById<Button>(R.id.nextButton)
            val finishButton = dialogView.findViewById<Button>(R.id.finishButton)

            // Build the AlertDialog with the custom layout
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
                .setView(dialogView)

            // Set click listeners for buttons
            nextButton.setOnClickListener {
                // Handle next button click
                resumeImageCapture()
                alertDialog.dismiss()
                // Show next letter
                showNextLetter()
            }
            finishButton.setOnClickListener {
                // Handle finish button click
                alertDialog.dismiss()
                findNavController().navigate(R.id.action_cameraGameFragment_to_gameEndAnimationFragment)
            }

            // Create the AlertDialog
            alertDialog = alertDialogBuilder.create()

            // Show the AlertDialog
            alertDialog.show()
        }
    }

    private fun resumeImageCapture() {
        shouldCaptureImage = true
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val TAG = "SendImageToServer"
        private const val SERVER_URL = "http://192.168.8.101:8000/upload/"
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

}
