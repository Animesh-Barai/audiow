package ismaeldivita.noizu.podcast.data.storage.preferences.mmkv

import android.app.Application
import com.tencent.mmkv.MMKV
import ismaeldivita.noizu.core.android.ApplicationInitializer
import javax.inject.Inject

internal class MmkvInitiliazer @Inject constructor(): ApplicationInitializer {

    override fun initialize(application: Application) {
        MMKV.initialize(application)
    }

}