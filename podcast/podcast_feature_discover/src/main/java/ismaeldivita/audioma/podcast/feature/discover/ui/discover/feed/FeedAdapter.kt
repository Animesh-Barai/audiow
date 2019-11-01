package ismaeldivita.audioma.podcast.feature.discover.ui.discover.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestManager
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import ismaeldivita.audioma.core.monitoring.log.Logger
import ismaeldivita.audioma.core.util.standart.exhaustive
import ismaeldivita.audioma.design.databinding.BindableAdapter
import ismaeldivita.audioma.podcast.data.model.Artwork
import ismaeldivita.audioma.podcast.data.model.FeedSection
import ismaeldivita.audioma.podcast.data.model.FeedSection.*
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.feature.discover.R
import ismaeldivita.audioma.podcast.feature.discover.ui.discover.feed.FeedViewHolder.*

internal class FeedAdapter(
    private val imageLoader: RequestManager,
    private val callback: (Action) -> Unit
) : RecyclerView.Adapter<FeedViewHolder<*>>(),
    BindableAdapter<List<FeedSection>> {

    sealed class Action {
        class PodcastSelected(podcast: Podcast) : Action()
        class GenreSelected(genre: Genre) : Action()
    }

    private val feed: MutableList<FeedSection> = mutableListOf()
    private var glidePreloader: RecyclerView.OnScrollListener? = null

    private val genreSharedPool = RecyclerView.RecycledViewPool()
    private val bannerSharedPool = RecyclerView.RecycledViewPool()

    override fun setData(data: List<FeedSection>) {
        feed.clear()
        feed.addAll(data + data + data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.podcast_feature_discover_genre -> GenreViewHolder(
                binding = DataBindingUtil.inflate(inflater, viewType, parent, false),
                imageLoader = imageLoader,
                sharedPool = genreSharedPool
            )

            R.layout.podcast_feature_discover_banner -> BannerViewHolder(
                binding = DataBindingUtil.inflate(inflater, viewType, parent, false),
                imageLoader = imageLoader,
                sharedPool = bannerSharedPool
            )

            R.layout.podcast_feature_discover_highlight -> HighlightViewHolder(
                binding = DataBindingUtil.inflate(inflater, viewType, parent, false),
                imageLoader = imageLoader
            )
            else -> throw IllegalStateException("viewType not supported")
        }
    }

    override fun getItemCount(): Int = feed.size

    override fun getItemViewType(position: Int): Int = when (feed[position]) {
        is Banner -> R.layout.podcast_feature_discover_banner
        is Highlight -> R.layout.podcast_feature_discover_highlight
        is GenreSection -> R.layout.podcast_feature_discover_genre
    }

    override fun onBindViewHolder(vh: FeedViewHolder<*>, position: Int) {
        val item = feed[position]

        when {
            vh is BannerViewHolder && item is Banner -> vh.binding.banner = item
            vh is GenreViewHolder && item is GenreSection -> vh.binding.genreSection = item
            vh is HighlightViewHolder && item is Highlight -> vh.binding.podcast = item.podcast
            else -> Logger.e("View holder and feed item mismatched")
        }.exhaustive

        vh.binding.executePendingBindings()
    }

    override fun onViewAttachedToWindow(holder: FeedViewHolder<*>) {
        holder.itemView.requestApplyInsets()
    }

    /**
     * Attach Glide preloader optimizer to [RecyclerView.OnScrollListener]
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        glidePreloader = RecyclerViewPreloader(
            imageLoader,
            ArtworkPreloadModelProvider(),
            { artwork, _, _ -> intArrayOf(artwork.width, artwork.height) },
            30
        ).also(recyclerView::addOnScrollListener)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        glidePreloader?.let(recyclerView::removeOnScrollListener)
        super.onDetachedFromRecyclerView(recyclerView)
    }

    /**
     * This class will provide the list of images required by Glide to optimize the load
     * to achieve smooth scrolls on the feed.
     *
     * Since there is a horizontal sublist in [GenreSection] we just need to pick a few images and
     * not the entire list. Any further optimization for [GenreSection] should be
     * in [GenreSectionAdapter]
     */
    inner class ArtworkPreloadModelProvider : ListPreloader.PreloadModelProvider<Artwork> {

        override fun getPreloadItems(position: Int): List<Artwork> {
            return when (val item = feed[position]) {
                is Banner -> item.podcasts.map { it.artwork }
                is Highlight -> listOf(item.podcast.artwork)
                is GenreSection -> item.podcasts.take(6).map { it.artwork }
            }.exhaustive
        }

        override fun getPreloadRequestBuilder(item: Artwork) = imageLoader.load(item.url)

    }

}