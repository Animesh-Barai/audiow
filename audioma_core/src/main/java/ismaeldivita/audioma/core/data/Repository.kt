package ismaeldivita.audioma.core.data

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface Repository<T> {

    fun add(element: T): Completable

    fun getAll(): Single<List<T>>

    fun remove(element: T): Completable

    fun clear(): Completable

}