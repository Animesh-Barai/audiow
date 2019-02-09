package ismaeldivita.podkast.data.storage.preferences

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ismaeldivita.podkast.core.android.ApplicationInitializer
import ismaeldivita.podkast.data.storage.preferences.mmkv.MmkvInitiliazer
import ismaeldivita.podkast.data.storage.preferences.mmkv.MmkvPreferences
import javax.inject.Singleton

@Module
internal abstract class PreferencesModule {

    @Binds
    @Singleton
    internal abstract fun bindPreferences(mmkvPreferences: MmkvPreferences): Preferences

    @Binds
    @IntoSet
    internal abstract fun mmkvInitializer(initializer: MmkvInitiliazer): ApplicationInitializer
}