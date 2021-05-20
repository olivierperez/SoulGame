package fr.o80.gamelib.service.i18n

import java.util.*

class MessagesImpl : Messages {

    private val bundle = ResourceBundle.getBundle("i18n")

    override fun get(key: String): String {
        return bundle.getString(key)
    }

    override fun get(key: String, vararg args: Any): String {
        return String.format(get(key), *args)
    }
}
