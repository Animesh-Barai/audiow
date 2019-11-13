package ismaeldivita.audioma.core.data.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface Repository<T> {

    fun add(element: T): Completable

    fun addAll(elements: List<T>): Completable

    fun getAll(): Single<List<T>>

    fun onChanged(id: Any):Observable<List<T>>

    fun findById(id: Any): Maybe<T>

    fun findByIds(ids: List<Any>): Single<List<T>>

    fun onItemChanged(id: Any):Observable<T>

    fun remove(element: T): Completable

    fun clear(): Completable

}