package fr.o80.soulgame

import java.io.File

fun resourceFile(filename: String): File {
    return File(filename).takeIf { it.exists() }
           ?: File("./SoulGameImpl/resources/$filename")

}

fun resource(filename: String): String {
    return File(filename).takeIf { it.exists() }?.let { filename }
           ?: "./SoulGameImpl/resources/$filename"
}
