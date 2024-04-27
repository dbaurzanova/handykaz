import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.example.handykaz.R
import com.example.handykaz.resources.VideoClass

class VideoAdapter(
    private val videoList: List<VideoClass>,
    private val onItemClick: (video: VideoClass) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_list_item, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videoList[position]

        holder.videoThumbNail.setImageResource(video.thumbnailResourceId)

        holder.seekForLockedVideo.apply {
            visibility = if (video.isWatched) View.GONE else View.VISIBLE
        }

        holder.verticalSeekBar.apply {
            visibility = if (video.isWatched) View.VISIBLE else View.GONE
        }

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            onItemClick.invoke(video)
        }
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val seekForLockedVideo: View = itemView.findViewById<View>(R.id.seekForLockedVideo)
        val verticalSeekBar: View = itemView.findViewById<View>(R.id.seekForUnlockedVideo)
        val videoThumbNail: ImageView = itemView.findViewById<ImageView>(R.id.videoThumbNail)
    }
}
