package ismaeldivita.audioma.design.databinding

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import android.view.View


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

@BindingAdapter("horizontalSpaceDecoration")
fun horizontalSpaceDecoration(recyclerView: RecyclerView, space: Float) {
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
                    0 -> Rect(spaceInt, 0, spaceInt, 0)
                    else -> Rect(0, 0, spaceInt, 0)
                }
                outRect.set(rect)
            }
        }
    )
}