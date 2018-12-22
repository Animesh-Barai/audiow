package ismaeldivita.podkast.data.storage.preferences.mmkv

import ismaeldivita.podkast.data.storage.preferences.Preferences
import ismaeldivita.podkast.data.storage.preferences.PreferencesKey

class MmkvPreferences : Preferences {

    override fun <T> read(key: PreferencesKey): T? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> write(key: PreferencesKey, value: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(key: PreferencesKey) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clean() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}