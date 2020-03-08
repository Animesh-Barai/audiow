package audiow.user.data.repository

import audiow.core.common.SignOutCallback
import audiow.core.data.repository.Repository
import audiow.core.data.repository.RepositoryWatcher
import audiow.user.data.model.Subscription
import audiow.user.data.model.User
import audiow.user.data.repository.subscription.SubscriptionRepository
import audiow.user.data.repository.subscription.SubscriptionWatcher
import audiow.user.data.repository.user.UserRepository
import audiow.user.data.repository.user.UserSignOutCallback
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.multibindings.IntoSet

@Module
internal interface RepositoryModule {

    @Binds
    @Reusable
    fun bindUserRepository(repository: UserRepository): Repository<User>

    @Binds
    @Reusable
    fun bindSubscriptionRepository(repository: SubscriptionRepository): Repository<Subscription>

    @Binds
    @Reusable
    fun bindSubscriptionWatcher(watcher: SubscriptionWatcher): RepositoryWatcher<Subscription>

    @Binds
    @IntoSet
    fun bindSignOutCallback(callback: UserSignOutCallback): SignOutCallback
}