package fr.o80.gamelib.service.i18n

interface Messages {
    operator fun get(key: String): String
    operator fun get(key: String, vararg args: Any): String
}
