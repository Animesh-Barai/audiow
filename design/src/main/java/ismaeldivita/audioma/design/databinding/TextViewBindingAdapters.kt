package ismaeldivita.audioma.design.databinding

import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("htmlText")
fun htmlText(
    view: TextView,
    text: String?
) {
    if (text == null) return

    val spannable = HtmlCompat.fromHtml(
        text,
        HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH
                or HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_HEADING
                or HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
                or HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST
                or HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_DIV
                or HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE
    )

    view.text = spannable.toString()
}