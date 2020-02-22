package audiow.user.data.repository

import audiow.core.data.repository.Repository
import audiow.user.data.model.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor() : Repository<User> {

    override fun add(element: User): Completable = Completable.complete()

    override fun addAll(elements: List<User>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Single<List<User>> = Single.just(emptyList())

    override fun findById(id: Any): Maybe<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByIds(ids: List<Any>): Single<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(element: User): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}