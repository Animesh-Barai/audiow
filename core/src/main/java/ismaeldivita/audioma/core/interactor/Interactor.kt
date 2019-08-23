package ismaeldivita.audioma.core.interactor

interface Interactor<in Input, out Output : Any> : (Input) -> Output