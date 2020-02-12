package audiow.core.util.standart

import java.util.*

class Tree<T>(private val root: Node<T>) : Iterable<Tree.Node<T>> {

    class Node<T>(
        val value: T,
        val parent: Node<T>? = null,
        val children: MutableList<Node<T>> = mutableListOf()
    )

    override fun iterator(): Iterator<Node<T>> = object : AbstractIterator<Node<T>>() {
        val nodes = buildList(root)

        private fun buildList(node: Node<T>): LinkedList<Node<T>> {
            return LinkedList(
                listOf(node) + node.children.map { buildList(it) }.flatten()
            )
        }

        override fun computeNext() {
            if (nodes.isEmpty()) done() else setNext(nodes.removeFirst())
        }
    }

}

