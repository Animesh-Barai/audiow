package audiow.core.data.preferences.mmkv

import android.app.Application
import com.tencent.mmkv.MMKV
import audiow.core.common.ApplicationInitializer
import javax.inject.Inject

internal class MmkvInitiliazer @Inject constructor(): ApplicationInitializer {

    override fun initialize(application: Application) {
        MMKV.initialize(application)
    }

}