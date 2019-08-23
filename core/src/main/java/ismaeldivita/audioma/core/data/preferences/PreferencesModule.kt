package ismaeldivita.audioma.core.data.preferences

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ismaeldivita.audioma.core.android.ApplicationInitializer
import ismaeldivita.audioma.core.data.preferences.mmkv.MmkvInitiliazer
import ismaeldivita.audioma.core.data.preferences.mmkv.MmkvPreferences
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