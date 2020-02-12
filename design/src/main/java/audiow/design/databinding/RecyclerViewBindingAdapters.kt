package audiow.design.databinding

import android.graphics.Rect
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import audiow.design.metrics.applySystemWindowsDecoration

interface BindableAdapter<T> {
    fun setData(data: T)
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun <T> setData(recyclerView: RecyclerView, data: T?) {
    if (data == null) return

    val adapter = recyclerView.adapter

    if (adapter is BindableAdapter<*>) {
        (adapter as BindableAdapter<T>).setData(data)
    }
}

@BindingAdapter("setHasFixedSize")
fun setHasFixedSize(recyclerView: RecyclerView, isFixedSize: Boolean) {
    recyclerView.setHasFixedSize(isFixedSize)
}

@BindingAdapter("verticalSpaceDecoration")
fun verticalSpaceDecoration(recyclerView: RecyclerView, space: Float) {
    recyclerView.addItemDecoration(
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                val spaceInt = space.toInt()

                val rect: Rect = when (position) {
                    0 -> Rect(0, spaceInt, 0, spaceInt)
                    else -> Rect(0, 0, 0, spaceInt)
                }
                outRect.set(rect)
            }
        }
    )
}

@BindingAdapter("horizontalSpaceDecoration_space", "horizontalSpaceDecoration_edge")
fun horizontalSpaceDecoration(recyclerView: RecyclerView, space: Float, edge: Float) {
    recyclerView.addItemDecoration(
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                val size = parent.adapter?.itemCount ?: 0
                val spaceInt = space.toInt()
                val edgeInt = edge.toInt()

                val rect: Rect = when (position) {
                    0 -> Rect(edgeInt, 0, spaceInt, 0)
                    size - 1 -> Rect(0, 0, edgeInt, 0)
                    else -> Rect(0, 0, spaceInt, 0)
                }
                outRect.set(rect)
            }
        }
    )
}

@BindingAdapter(
    "horizontalSystemWindowInsetsDecoration",
    "verticalSystemWindowInsetsDecoration",
    "leftSystemWindowInsetsDecoration",
    "topSystemWindowInsetsDecoration",
    "rightSystemWindowInsetsDecoration",
    "bottomSystemWindowInsetsDecoration",
    requireAll = false
)
fun insetDecoration(
    recyclerView: RecyclerView,
    horizontal: Boolean,
    vertical: Boolean,
    left: Boolean,
    top: Boolean,
    right: Boolean,
    bottom: Boolean
) {
    recyclerView.applySystemWindowsDecoration(
        applyLeft = horizontal || left,
        applyTop = vertical || top,
        applyRight = horizontal || right,
        applyBottom = vertical || bottom
    )
}
