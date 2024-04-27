package com.example.handykaz
import VideoAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handykaz.resources.VideoClass

class VideoLessonsFragment : Fragment() {

    private lateinit var adapter : VideoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var videoArrayList : ArrayList<VideoClass>

    lateinit var titles: Array<String>
    lateinit var videoThumbNails: Array<Int>
    lateinit var watchingStates: Array<Boolean>
    lateinit var videoUrls: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_lessons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = VideoAdapter(videoArrayList) { video ->
            // Handle the item click here
            if (video.isWatched) {
                navigateToEachVideoFragment(video)
            }
        }
        recyclerView.adapter = adapter

    }

    private fun navigateToEachVideoFragment(video: VideoClass) {
        // Pass the video data to EachVideoLessonsFragment using a Bundle
        val bundle = Bundle().apply {
            putString("videoTitle", video.title)
            putInt("videoThumbnail", video.thumbnailResourceId)
            putBoolean("isWatched", video.isWatched)
            putString("videoUrl", video.videoUrl)
            putInt("videoId", video.id)
        }

        // Navigate to EachVideoLessonsFragment
        findNavController().navigate(R.id.action_videoLessonsFragment_to_eachVideoLessonFragment, bundle)
    }

    private fun dataInitialize(){

        videoArrayList = arrayListOf<VideoClass>()

        titles = arrayOf(
            "1-сабақ",
            "2-сабақ",
            "3-сабақ",
            "4-сабақ"
        )

        videoThumbNails = arrayOf(
            R.drawable.video_less_1,
            R.drawable.video_less_2,
            R.drawable.video_less_3,
            R.drawable.video_less_4
        )

        watchingStates = arrayOf(
            true,
            false,
            false,
            false
        )

        videoUrls = arrayOf(
            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/h6ukdwq-D6o?si=keOMt434bUGKbiF1\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/71juUqkBGY4?si=GDbWXfQUYK_SwCyR\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/71juUqkBGY4?si=GDbWXfQUYK_SwCyR\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/71juUqkBGY4?si=GDbWXfQUYK_SwCyR\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>"
        )

        for (i in titles.indices){

            val video = VideoClass(i, titles[i],videoThumbNails[i], watchingStates[i], videoUrls[i])

            videoArrayList.add(video)
        }
    }

}