package audiow.user.data.repository.user

import audiow.core.data.repository.Repository
import audiow.user.data.model.User
import audiow.user.data.storage.database.dao.UserDAO
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.lang.IllegalStateException
import javax.inject.Inject

internal class UserRepository @Inject constructor(
    private val userDAO: UserDAO
) : Repository<User> {

    override fun add(element: User): Completable = Completable.fromCallable {
        userDAO.upsert(element.toEntity())
    }

    override fun addAll(elements: List<User>): Completable {
        return if (elements.filter { it.isSelected }.size > 1) {
            Completable.error(IllegalStateException("Trying to insert multiple selected users"))
        } else {
            Completable.fromCallable { userDAO.upsert(elements.map { it.toEntity() }) }
        }
    }

    override fun getAll(): Single<List<User>> =
        userDAO.getAll()
            .map { users -> users.map { it.toDomain() } }

    override fun findById(id: Any): Maybe<User> =
        userDAO.findById(id as String).map { it.toDomain() }

    override fun findByIds(ids: List<Any>): Single<List<User>> =
        userDAO.findByIds(ids.map { it as String })
            .map { users -> users.map { it.toDomain() } }

    override fun remove(element: User): Completable = userDAO.delete(element.toEntity())

    override fun clear(): Completable = userDAO.deleteAll()

}