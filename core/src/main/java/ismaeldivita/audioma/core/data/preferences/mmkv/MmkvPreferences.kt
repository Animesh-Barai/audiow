package ismaeldivita.audioma.core.data.preferences.mmkv

import com.tencent.mmkv.MMKV
import ismaeldivita.audioma.core.data.preferences.Preferences
import ismaeldivita.audioma.core.util.standart.exhaustive
import javax.inject.Inject

internal class MmkvPreferences @Inject constructor() : Preferences {

    private val mmkv by lazy { MMKV.defaultMMKV() }

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    override fun <T : Any> read(key: Preferences.Key<T>): T? {
        return when (key) {
            is Preferences.Key.String -> mmkv.decodeString(key.identifier)
            is Preferences.Key.Boolean -> mmkv.decodeBool(key.identifier)
            is Preferences.Key.Int -> mmkv.decodeInt(key.identifier)
            is Preferences.Key.Long -> mmkv.decodeLong(key.identifier)
        }.exhaustive as T?
    }

    override fun <T : Any> write(key: Preferences.Key<T>, value: T) {
        when (key) {
            is Preferences.Key.String -> mmkv.encode(key.identifier, value as String)
            is Preferences.Key.Boolean -> mmkv.encode(key.identifier, value as Boolean)
            is Preferences.Key.Int -> mmkv.encode(key.identifier, value as Int)
            is Preferences.Key.Long -> mmkv.encode(key.identifier, value as Long)
        }.exhaustive
    }

    override fun remove(key: Preferences.Key<*>) {
        mmkv.removeValueForKey(key.identifier)
    }

    override fun clean() = with(mmkv) {
        clearMemoryCache()
        clearAll()
    }

}