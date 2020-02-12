package audiow.core.android.ui

import android.os.Bundle
import androidx.fragment.app.Fragment

fun <T : Fragment> T.withArgs(bundle: Bundle = Bundle(), block: Bundle.() -> Unit): T =
    apply { arguments = bundle.apply(block) }