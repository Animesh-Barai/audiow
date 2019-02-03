package ismaeldivita.podkast.data.storage.preferences.mmkv

import androidx.test.core.app.ApplicationProvider
import com.tencent.mmkv.MMKV
import ismaeldivita.podkast.data.storage.preferences.Preferences

class MmkvPreferencesTest : PreferencesTest() {

    override val preferences: Preferences = run {
        MMKV.initialize(ApplicationProvider.getApplicationContext())
        MmkvPreferences()
    }

}