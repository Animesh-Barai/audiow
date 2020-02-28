package audiow.user.data.repository.user

import audiow.user.data.model.SignInMethod
import audiow.user.data.model.User
import audiow.user.data.storage.database.dao.entity.UserEntity

internal fun User.toEntity() = UserEntity(
    id = id,
    name = name,
    email = email,
    photoUrl = photoUrl,
    signInMethod = signInMethod.name
)

internal fun UserEntity.toDomain() = User(
    id = id,
    name = name,
    email = email,
    photoUrl = photoUrl,
    signInMethod = SignInMethod.valueOf(signInMethod)
)