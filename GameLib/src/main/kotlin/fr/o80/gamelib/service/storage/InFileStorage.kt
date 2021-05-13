package fr.o80.gamelib.service.storage

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.io.File

class InFileStorage<T>(
    private val serializer: KSerializer<T>,
    private val filename: String
) : Storage<T> {

    override fun store(value: T) {
        File(filename).writer().use { writer ->
            writer.write(Json.encodeToString(serializer, value))
        }
    }

    override fun get(): T? {
        return File(filename)
            .takeIf { it.isFile }
            ?.reader()
            ?.use { reader ->
                Json.decodeFromString(serializer, reader.readText())
            }
    }
}
