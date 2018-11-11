package ismaeldivita.podkast.application

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import ismaeldivita.podkast.application.di.DaggerApplicationComponent

class PodkastApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<PodkastApplication> =
            DaggerApplicationComponent.builder().create(this)

}
