package audiow.core.interactor

interface Interactor<in Input, out Output : Any> : (Input) -> Output

operator fun <T : Any> Interactor<Unit, T>.invoke(): T = this(Unit)