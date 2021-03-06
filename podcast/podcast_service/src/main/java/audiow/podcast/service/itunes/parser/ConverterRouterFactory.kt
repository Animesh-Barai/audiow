package audiow.podcast.service.itunes.parser

import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import audiow.podcast.service.itunes.parser.json.Json
import audiow.podcast.service.itunes.parser.json.MoshiProvider
import audiow.podcast.service.itunes.parser.xml.TikXmlProvider
import audiow.podcast.service.itunes.parser.xml.Xml
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type

internal class ConverterRouterFactory : Converter.Factory() {

    private val jsonConverter = MoshiConverterFactory.create(MoshiProvider.instanceWithAdapters)
    private val xmlConverter = TikXmlConverterFactory.create(TikXmlProvider.instance)

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
