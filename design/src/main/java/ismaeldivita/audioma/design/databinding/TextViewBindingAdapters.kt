package ismaeldivita.audioma.design.databinding

import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter

@BindingAdapter(
    "htmlText",
    "htmlTextFormatBreakLine",
    requireAll = false
)
fun htmlText(
    view: TextView,
    text: String?,
    formatBreakLine: Boolean
) {
    if (text == null) return

    var finalText = text
    if (formatBreakLine) finalText = finalText.replace("\n", "<br/>")

    view.text = HtmlCompat.fromHtml(
        finalText, HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH
                or HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_HEADING
                or HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
                or HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST
                or HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_DIV
                or HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE
    )
}