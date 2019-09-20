package ismaeldivita.audioma.design.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

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