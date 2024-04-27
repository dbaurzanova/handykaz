package com.example.handykaz

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageButton
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import androidx.lifecycle.ViewModelProvider
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController

class EachVideoLessonFragment : Fragment() {

    private lateinit var webView: WebView
    private lateinit var videoTitleText : TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_each_video_lesson, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data from the arguments
        val videoTitle = arguments?.getString("videoTitle")
        val videoThumbnail = arguments?.getInt("videoThumbnail", 0)
        val isWatched = arguments?.getBoolean("isWatched", false)
        val videoUrl = arguments?.getString("videoUrl")
        val videoId = arguments?.getInt("videoId")

        // Use the data as needed in your fragment
        videoTitleText = view.findViewById(R.id.videoLessonTitle)
        videoTitleText.text = videoTitle

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBarHorizontal)
        progressBar.progress = videoId!!.toInt() + 1

        webView = view.findViewById(R.id.videoLessonWebView)
        webView.loadData(videoUrl.toString(), "text/html", "utf-8")
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.setBackgroundColor(0)

        val nextBtn = view.findViewById<Button>(R.id.nextVideoBtn)
        nextBtn.setOnClickListener{
        }


        view.findViewById<ImageButton>(R.id.backBtn).setOnClickListener {
            findNavController().navigateUp()
        }

    }


}