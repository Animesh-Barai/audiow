package ismaeldivita.podkast.data.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface Repository<T> {

    fun add(item: T): Completable

    fun replace(item: T): Completable

    fun getAll(): Single<List<T>>

    fun getById(id: Int): Maybe<T>

    fun remove(item: T): Completable

    fun removeAll(): Completable

}