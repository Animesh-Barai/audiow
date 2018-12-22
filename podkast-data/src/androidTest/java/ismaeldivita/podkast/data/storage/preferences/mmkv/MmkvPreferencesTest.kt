package ismaeldivita.podkast.data.storage.preferences.mmkv

import androidx.test.core.app.ApplicationProvider
import com.tencent.mmkv.MMKV
import ismaeldivita.podkast.data.storage.preferences.PreferenceValue
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MmkvPreferencesTest {

    private val mmkv = run {
        MMKV.initialize(ApplicationProvider.getApplicationContext())
        MmkvPreferences()
    }

    @After
    fun tearDown() {
        mmkv.clean()
    }

    @Test
    fun writeThenRead() {
        val value = "Lorem Ipsum"
        mmkv.write(PreferenceValue.SelectedLocale, value)
        assertEquals(value, mmkv.read(PreferenceValue.SelectedLocale))
    }

    @Test
    fun remove() {
        val value = "Lorem Ipsum"
        mmkv.write(PreferenceValue.SelectedLocale, value)
        mmkv.remove(PreferenceValue.SelectedLocale)
        assertNull(mmkv.read(PreferenceValue.SelectedLocale))
    }

    @Test
    fun clean() {
        val value = "Lorem Ipsum"
        mmkv.write(PreferenceValue.SelectedLocale, value)
        mmkv.clean()
        assertNull(mmkv.read(PreferenceValue.SelectedLocale))
    }

}