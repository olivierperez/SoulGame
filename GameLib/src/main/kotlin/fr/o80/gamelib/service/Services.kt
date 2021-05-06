package fr.o80.gamelib.service

import fr.o80.gamelib.service.cursor.CursorManager
import fr.o80.gamelib.service.storage.Storage

class Services(
    val cursorManager: CursorManager,
    val storage: Storage
)
