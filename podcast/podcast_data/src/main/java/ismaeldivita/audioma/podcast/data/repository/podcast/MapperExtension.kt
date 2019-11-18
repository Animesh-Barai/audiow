import ismaeldivita.audioma.podcast.data.model.Artwork
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastArtworkEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastWrapperEntity
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcast

internal fun ItunesPodcast.toDomain(genres: List<Genre>) = Podcast(
    id = id,
    title = title,
    artistName = artistName,
    rssUrl = rssUrl,
    artworkList = artworkList.map { Artwork(it.url, it.width, it.height) },
    primaryGenre = genres.first { it.id == primaryGenreId },
    genreList = genreListId.map { id -> genres.first { it.id == id } },
    explicit = explicit
)

internal fun PodcastWrapperEntity.toDomain(genreList: List<Genre>) = Podcast(
    id = podcast.id,
    title = podcast.title,
    artistName = podcast.artistName,
    rssUrl = podcast.rssUrl,
    artworkList = artworkList.map { Artwork(it.url, it.width, it.height) },
    primaryGenre = genreList.first { it.id == podcast.primaryGenre },
    genreList = genreList.filter { genreIds.contains(it.id) },
    explicit = podcast.explicit
)

internal fun Podcast.toEntity() = PodcastEntity(
    id = id,
    title = title,
    artistName = artistName,
    primaryGenre = primaryGenre.id,
    rssUrl = rssUrl,
    explicit = explicit
)

internal fun Podcast.toArtworkEntityList() = artworkList.map {
    PodcastArtworkEntity(
        url = it.url,
        width = it.width,
        height = it.height,
        podcastIdFk = id
    )
}

internal fun Podcast.toWrapperEntity() = PodcastWrapperEntity(
    podcast = toEntity(),
    artworkList = toArtworkEntityList(),
    genreIds = genreList.map { it.id }
)

