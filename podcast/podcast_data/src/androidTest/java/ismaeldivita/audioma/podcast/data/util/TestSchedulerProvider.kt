package ismaeldivita.audioma.podcast.data.util

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ismaeldivita.audioma.core.util.reactive.scheduler.SchedulersProvider

class TestSchedulerProvider : SchedulersProvider {

    override fun main(): Scheduler = Schedulers.trampoline()

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun computation(): Scheduler = Schedulers.trampoline()

    override fun newThread(): Scheduler = Schedulers.trampoline()

}