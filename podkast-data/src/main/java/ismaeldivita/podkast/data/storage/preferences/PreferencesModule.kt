package ismaeldivita.podkast.data.storage.preferences

import dagger.Binds
import dagger.Module
import ismaeldivita.podkast.data.storage.preferences.mmkv.MmkvPreferences
import javax.inject.Singleton

@Module
internal abstract class PreferencesModule {

    @Binds
    @Singleton
    internal abstract fun bindPreferences(mmkvPreferences: MmkvPreferences): Preferences

}