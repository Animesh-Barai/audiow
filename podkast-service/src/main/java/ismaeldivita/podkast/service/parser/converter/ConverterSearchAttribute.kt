package ismaeldivita.podkast.service.parser.converter

import ismaeldivita.podkast.service.model.SearchAttribute
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class ConverterSearchAttribute : Converter.Factory() {

    override fun stringConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
    ): Converter<SearchAttribute, String> = Converter { it.attribute }

}
