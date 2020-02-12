package audiow.core.data.repository

import io.reactivex.Observable

interface RepositoryWatcher<T> {

    fun onChanged(): Observable<List<T>>

    fun onItemChanged(id: Any): Observable<T>
}