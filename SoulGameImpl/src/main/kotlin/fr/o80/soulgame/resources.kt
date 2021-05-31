package fr.o80.soulgame

import java.io.File

fun resourceFile(filename: String): File {
    return File(filename).takeIf { it.exists() }
           ?: File("./SoulGameImpl/resources/$filename") // TODO Issue#1 https://github.com/olivierperez/SoulGame/issues/1

}

fun resource(filename: String): String { // TODO Rename resourcePath
    return File(filename).takeIf { it.exists() }?.let { filename }
           ?: "./SoulGameImpl/resources/$filename"
}
