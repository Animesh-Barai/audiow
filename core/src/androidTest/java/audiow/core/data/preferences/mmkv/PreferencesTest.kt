package audiow.core.data.preferences.mmkv

import audiow.core.data.preferences.Preferences
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
    fun write_then_read() {
        val value = "Lorem Ipsum"
        val key = Preferences.Key.String("test_key")

        preferences.write(key, value)

        assertEquals(value, preferences.read(key))
    }

    @Test
    fun remove() {
        val value = "Lorem Ipsum"
        val key = Preferences.Key.String("test_key")
        val value2 = "Lorem Ipsum 2"
        val key2 = Preferences.Key.String("test_key2")

        preferences.write(key, value)
        preferences.write(key2, value2)
        preferences.remove(key)

        assertNull(preferences.read(key))
        assertEquals(value2, preferences.read(key2))
    }

    @Test
    fun clean() {
        val value = "Lorem Ipsum"
        val key = Preferences.Key.String("test_key")
        val value2 = "Lorem Ipsum 2"
        val key2 = Preferences.Key.String("test_key2")

        preferences.write(key, value)
        preferences.write(key2, value2)
        preferences.clean()

        assertNull(preferences.read(key))
        assertNull(preferences.read(key2))
    }

}