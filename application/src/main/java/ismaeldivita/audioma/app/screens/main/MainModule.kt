package ismaeldivita.audioma.app.screens.main

import androidx.fragment.app.FragmentTransaction
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideFragmentTransaction(activity: MainActivity): FragmentTransaction {
        return activity.supportFragmentManager
            .apply {
                // TODO customize fragment transaction here
            }.beginTransaction()
    }
}