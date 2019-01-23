package ismaeldivita.podkast.data.storage.preferences.mmkv

import com.tencent.mmkv.MMKV
import ismaeldivita.podkast.data.storage.preferences.PreferenceValue
import ismaeldivita.podkast.data.storage.preferences.Preferences
import javax.inject.Inject

class MmkvPreferences @Inject constructor(): Preferences {

    private val mmkv by lazy { MMKV.defaultMMKV() }

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    override fun <T : Any> read(value: PreferenceValue<T>): T? {
        return when (value.type) {
            String::class -> mmkv.decodeString(value.keyValue)
            Boolean::class -> mmkv.decodeBool(value.keyValue)
            Int::class -> mmkv.decodeInt(value.keyValue)
            else -> throw UnsupportedOperationException("Type not supported")
        } as T?
    }

    override fun <T : Any> write(key: PreferenceValue<T>, value: T) {
        when (value) {
            is String -> mmkv.encode(key.keyValue, value)
            is Boolean -> mmkv.encode(key.keyValue, value)
            is Int -> mmkv.encode(key.keyValue, value)
        }
    }

    override fun remove(key: PreferenceValue<*>) {
        mmkv.removeValueForKey(key.keyValue)
    }

    override fun clean() = with(mmkv) {
        clearMemoryCache()
        clearAll()
    }

}