package audiow.core.android.ui.activity

import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.finishWithResult(result: Int) {
    setResult(result)
    finish()
}