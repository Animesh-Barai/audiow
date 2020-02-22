package audiow.user.data.partner.firebase

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
internal class FirebaseModule {

    @Provides
    @Reusable
    fun firebaseAuth() = FirebaseAuth.getInstance()
}