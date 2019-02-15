package ismaeldivita.podkast.service.dto

import java.util.*

// TODO create a abstract tree and move to core
class GenreDTOTree(val root: GenreDTO) : Iterable<GenreDTO> {

    companion object {
        const val ROOT_GENRE_ID = 26
    }

    constructor(list: List<GenreDTO>) : this(list.first { it.id == ROOT_GENRE_ID })

    override fun iterator() = object : AbstractIterator<GenreDTO>() {
        val nodes = buildList(root)

        fun buildList(node: GenreDTO): LinkedList<GenreDTO> =
                if (node.detail?.subgenres.orEmpty().isEmpty()) {
                    LinkedList(listOf(node))
                } else {
                    LinkedList(listOf(node) +
                            node.detail?.subgenres.orEmpty().map { buildList(it) }.flatten())
                }

        override fun computeNext() = if (nodes.isEmpty()) done() else setNext(nodes.removeFirst())

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenreDTOTree

        if (root != other.root) return false

        return true
    }

    override fun hashCode(): Int {
        return root.hashCode()
    }

    override fun toString(): String {
        return "GenreDTOTree(root=$root)"
    }

}
