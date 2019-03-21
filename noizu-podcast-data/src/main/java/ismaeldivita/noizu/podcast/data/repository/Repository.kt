package ismaeldivita.noizu.podcast.data.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface Repository<T> {

    fun add(element: T): Completable

    fun getAll(): Single<List<T>>

    fun getById(id: Int): Maybe<T>

    fun remove(element: T): Completable

    fun clear(): Completable

}