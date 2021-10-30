package eu.anifantakis.elections

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import eu.anifantakis.elections.main.ElectionAdapter
import eu.anifantakis.elections.network.NETWORK_STATUS

/**
 * Binded at the RecyclerView of activity_main.xml
 * This binding adapter will update the RecyclerView items when the election results list live data gets updated
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ElectionResult>?){
    val adapter = recyclerView.adapter as ElectionAdapter
    adapter.submitList(data)
}

@BindingAdapter("swipeManager")
fun swipeManager(swipeControl: SwipeRefreshLayout, status: NETWORK_STATUS){
    when (status){
        NETWORK_STATUS.CONNECTED -> swipeControl.isRefreshing = false
        NETWORK_STATUS.DISCONNECTED -> swipeControl.isRefreshing = false
        NETWORK_STATUS.INITIALIZING -> swipeControl.isRefreshing = true
    }
}

@BindingAdapter("loadUrl")
fun bindPictureOfDay(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .into(imageView)
    }
}