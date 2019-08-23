package ismaeldivita.audioma.core.util.reactive

import io.reactivex.Scheduler

interface SchedulersProvider {

    fun main(): Scheduler

    fun io(): Scheduler

    fun computation(): Scheduler

    fun newThread(): Scheduler

}