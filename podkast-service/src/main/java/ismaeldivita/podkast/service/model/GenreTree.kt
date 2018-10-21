package ismaeldivita.podkast.service.model

import java.util.*

class GenreTree(val root: GenreNode) : Iterable<GenreNode> {

    override fun iterator() = object : AbstractIterator<GenreNode>() {
        val nodes = buildList(root)

        fun buildList(node: GenreNode): LinkedList<GenreNode> =
                if (node.subgenres.isEmpty()) {
                    LinkedList(listOf(node))
                } else {
                    LinkedList(listOf(node) + node.subgenres.map { buildList(it) }.flatten())
                }

        override fun computeNext() = if (nodes.isEmpty()) done() else setNext(nodes.removeFirst())

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenreTree

        if (root != other.root) return false

        return true
    }

    override fun hashCode(): Int {
        return root.hashCode()
    }

    override fun toString(): String {
        return "GenreTree(root=$root)"
    }

}
