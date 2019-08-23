package ismaeldivita.audioma.core.data.preferences

import kotlin.reflect.KClass

interface Preferences {

    fun <T : Any> read(key: Key<T>): T?

    fun <T : Any> write(key: Key<T>, value: T)

    fun remove(key: Key<*>)

    fun clean()

    data class Key<T : Any>(val keyValue: String, val type: KClass<T>)

}