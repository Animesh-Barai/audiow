package ismaeldivita.audioma.podcast.data.repository.feed

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.util.time.RFC822DateParser
import ismaeldivita.audioma.podcast.data.model.Feed
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.storage.database.dao.FeedDAO
import toDomain
import javax.inject.Inject

internal class FeedRepository @Inject constructor(
    private val dao: FeedDAO,
    private val genreRepository: Repository<Genre>,
    private val dateParser: RFC822DateParser
) : Repository<Feed> {

    override fun add(element: Feed) = Completable.fromCallable {
        dao.insert(element.toEntity(), element.episodes.map { it.toEntity(element.podcast.id) })
    }

    override fun addAll(elements: List<Feed>) = Completable.fromCallable {
        val feedPair = elements.map {
            it.toEntity() to it.episodes.map { ep -> ep.toEntity(it.podcast.id) }
        }
        dao.insert(feedPair.map { it.first }, feedPair.map { it.second }.flatten())
    }

    override fun getAll(): Single<List<Feed>> =
        Singles.zip(dao.getAllFeeds(), genreRepository.getAll()) { feeds, genreList ->
            feeds.map { it.toDomain(it.toPodcastDomain(genreList), dateParser) }
        }

    override fun findById(id: Any): Maybe<Feed> =
        dao.findById(id as Long)
            .flatMap { feed ->
                genreRepository.getAll().map { genreList ->
                    feed.toDomain(feed.toPodcastDomain(genreList), dateParser)
                }.toMaybe()
            }

    override fun findByIds(ids: List<Any>): Single<List<Feed>> =
        dao.findByIds(ids.filterIsInstance<Long>())
            .flatMap { feeds ->
                genreRepository.getAll().map { genreList ->
                    feeds.map { feed ->
                        feed.toDomain(feed.toPodcastDomain(genreList), dateParser)
                    }
                }
            }

    override fun remove(element: Feed): Completable = dao.delete(element.toEntity())

    override fun clear(): Completable = dao.deleteAll()

    override fun onItemChanged(id: Any): Observable<Feed> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onChanged(id: Any): Observable<List<Feed>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}