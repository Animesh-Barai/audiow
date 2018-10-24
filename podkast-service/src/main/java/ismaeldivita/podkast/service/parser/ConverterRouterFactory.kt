package ismaeldivita.podkast.service.parser

import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import ismaeldivita.podkast.service.parser.json.Json
import ismaeldivita.podkast.service.parser.json.MoshiProvider
import ismaeldivita.podkast.service.parser.xml.Xml
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type

internal class ConverterRouterFactory : Converter.Factory() {

    private val jsonConverter = MoshiConverterFactory.create(MoshiProvider.instanceWithAdapters)
    private val xmlConverter = TikXmlConverterFactory.create()

    override fun responseBodyConverter(
            type: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
    ): Converter<ResponseBody, *>? {

        annotations.forEach {
            when (it.annotationClass) {
                Xml::class -> return xmlConverter.responseBodyConverter(type, annotations, retrofit)
                Json::class -> return jsonConverter.responseBodyConverter(type, annotations, retrofit)
            }
        }
        return super.responseBodyConverter(type, annotations, retrofit)
    }
}
