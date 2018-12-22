package ismaeldivita.podkast.data.storage.preferences

interface Preferences {

    fun <T : Any> read(value: PreferenceValue<T>): T?

    fun <T : Any> write(key: PreferenceValue<T>, value: T)

    fun remove(key: PreferenceValue<*>)

    fun clean()

}