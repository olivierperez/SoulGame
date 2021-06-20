package fr.o80.soulgame

import java.io.File

fun resourceFile(filename: String): File {
    val file = File(filename)
    if (file.exists()) {
        return file
    }

    val resourceFile = File("SoulGameImpl/resources/$filename")
    if (resourceFile.exists()) {
        return resourceFile
    }

    throw IllegalArgumentException("Cannot find file \"${file.name}\"\n\t- at ${file.absolutePath}\n\t- at ${resourceFile.absolutePath}")
}

fun resourcePath(filename: String): String {
    return resourceFile(filename).path
}
