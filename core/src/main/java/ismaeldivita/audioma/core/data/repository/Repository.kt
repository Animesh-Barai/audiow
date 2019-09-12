package ismaeldivita.audioma.core.data.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface Repository<T> {

    fun add(element: T): Completable

    fun addAll(elements: List<T>): Completable

    fun getAll(): Single<List<T>>

    fun findById(id: Any): Maybe<T>

    fun findByIds(vararg ids: Any): Single<List<T>>

    fun remove(element: T): Completable

    fun clear(): Completable

}