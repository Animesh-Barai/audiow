package ismaeldivita.podkast.data.storage.preferences

interface Preferences {

    fun <T> read(key: PreferencesKey): T?

    fun <T> write(key: PreferencesKey, value: T)

}