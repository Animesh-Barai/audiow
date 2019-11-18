package ismaeldivita.audioma.podcast.data.repository.genre

import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.storage.database.entity.GenreEntity
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesGenre

internal fun ItunesGenre.toEntity() = GenreEntity(id = id, name = name)

internal fun GenreEntity.toDomain() = Genre(id = id, name = name)