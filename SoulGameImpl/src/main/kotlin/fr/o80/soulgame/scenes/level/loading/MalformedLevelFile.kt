package fr.o80.soulgame.scenes.level.loading

class MalformedLevelFile(cause: String): Exception("A file is malformed because: $cause")
