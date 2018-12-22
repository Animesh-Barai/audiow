package ismaeldivita.podkast.data.storage.preferences

import kotlin.reflect.KClass

sealed class PreferenceValue<T : Any>(val keyValue: String, val type: KClass<T>) {

    object SelectedLocale : PreferenceValue<String>("SELECTED_LOCALE", String::class)

}