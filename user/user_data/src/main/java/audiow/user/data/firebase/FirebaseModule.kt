package audiow.user.data.firebase

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module
internal class FirebaseModule {

    @Provides
    fun firebaseAuth() = FirebaseAuth.getInstance()
}