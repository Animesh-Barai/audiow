package ismaeldivita.podkast.data.storage.preferences.mmkv

import ismaeldivita.podkast.data.storage.preferences.Preferences
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

abstract class PreferencesTest {

    protected abstract val preferences: Preferences

    @After
    fun tearDown() {
        preferences.clean()
    }

    @Test
    fun writeThenRead() {
        val value = "Lorem Ipsum"
        val key = Preferences.Key("test_key", String::class)

        preferences.write(key, value)

        assertEquals(value, preferences.read(key))
    }

    @Test
    fun remove() {
        val value = "Lorem Ipsum"
        val key = Preferences.Key("test_key", String::class)
        val value2 = "Lorem Ipsum 2"
        val key2 = Preferences.Key("test_key2", String::class)

        preferences.write(key, value)
        preferences.write(key2, value2)
        preferences.remove(key)

        assertNull(preferences.read(key))
        assertEquals(value2, preferences.read(key2))
    }

    @Test
    fun clean() {
        val value = "Lorem Ipsum"
        val key = Preferences.Key("test_key", String::class)
        val value2 = "Lorem Ipsum 2"
        val key2 = Preferences.Key("test_key2", String::class)

        preferences.write(key, value)
        preferences.write(key2, value2)
        preferences.clean()

        assertNull(preferences.read(key))
        assertNull(preferences.read(key2))
    }

}