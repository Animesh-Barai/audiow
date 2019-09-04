package ismaeldivita.audioma.core.data.preferences

interface Preferences {

    fun <T : Any> read(key: Key<T>): T?

    fun <T : Any> write(key: Key<T>, value: T)

    fun remove(key: Key<*>)

    fun clean()

    sealed class Key<T : Any> {

        abstract val identifier: kotlin.String

        data class Long(override val identifier: kotlin.String) : Key<kotlin.Long>()
        data class Int(override val identifier: kotlin.String) : Key<kotlin.Int>()
        data class String(override val identifier: kotlin.String) : Key<kotlin.String>()
        data class Boolean(override val identifier: kotlin.String) : Key<kotlin.Boolean>()
    }

}