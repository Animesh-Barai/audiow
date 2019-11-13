package ismaeldivita.audioma.core.data.preferences.mmkv

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.tencent.mmkv.MMKV
import ismaeldivita.audioma.core.data.preferences.Preferences

class MmkvPreferencesTest : PreferencesTest() {

    override val preferences: Preferences = run {
        MMKV.initialize(ApplicationProvider.getApplicationContext() as Context)
        MmkvPreferences()
    }

}