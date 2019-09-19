package ismaeldivita.audioma.core.util.standart

import java.lang.reflect.ParameterizedType

val <T> T.exhaustive: T
    get() = this


@Suppress("UNCHECKED_CAST")
fun <T> Any.getTypeParameterClass(index: Int = 0): Class<T> =
    (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<T>