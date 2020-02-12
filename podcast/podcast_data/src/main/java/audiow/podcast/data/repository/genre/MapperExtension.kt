package audiow.podcast.data.repository.genre

import audiow.podcast.data.model.Genre
import audiow.podcast.data.storage.database.entity.GenreEntity
import audiow.podcast.service.itunes.model.ItunesGenre

internal fun ItunesGenre.toEntity() = GenreEntity(id = id, name = name)

internal fun GenreEntity.toDomain() = Genre(id = id, name = name)