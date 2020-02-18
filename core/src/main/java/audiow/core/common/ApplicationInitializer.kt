package audiow.core.common

import android.app.Application

interface ApplicationInitializer {

    fun initialize(application: Application)

}