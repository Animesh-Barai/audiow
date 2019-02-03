package ismaeldivita.podkast.data.storage.preferences.mmkv

import com.tencent.mmkv.MMKV
import ismaeldivita.podkast.data.storage.preferences.Preferences
import javax.inject.Inject

class MmkvPreferences @Inject constructor(): Preferences {

    private val mmkv by lazy { MMKV.defaultMMKV() }

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    override fun <T : Any> read(key: Preferences.Key<T>): T? {
        return when (key.type) {
            String::class -> mmkv.decodeString(key.keyValue)
            Boolean::class -> mmkv.decodeBool(key.keyValue)
            Int::class -> mmkv.decodeInt(key.keyValue)
            Long::class -> mmkv.decodeLong(key.keyValue)
            else -> throw UnsupportedOperationException("Type not supported")
        } as T?
    }

    override fun <T : Any> write(key: Preferences.Key<T>, value: T) {
        when (value) {
            is String -> mmkv.encode(key.keyValue, value)
            is Boolean -> mmkv.encode(key.keyValue, value)
            is Int -> mmkv.encode(key.keyValue, value)
            is Long -> mmkv.encode(key.keyValue, value)
        }
    }

    override fun remove(key: Preferences.Key<*>) {
        mmkv.removeValueForKey(key.keyValue)
    }

    override fun clean() = with(mmkv) {
        clearMemoryCache()
        clearAll()
    }

}