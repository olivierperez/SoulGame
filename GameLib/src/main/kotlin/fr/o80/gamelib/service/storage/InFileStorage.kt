package fr.o80.gamelib.service.storage

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.io.File

private const val FILENAME = "GameLib.save"

class InFileStorage<T>(
    private val serializer: KSerializer<T>
) : Storage<T> {

    override fun store(value: T) {
        File(FILENAME).writer().use { writer ->
            writer.write(Json.encodeToString(serializer, value))
        }
    }

    override fun get(): T? {
        return File(FILENAME)
            .takeIf { it.isFile }
            ?.reader()
            ?.use { reader ->
                Json.decodeFromString(serializer, reader.readText())
            }
    }
}
