package fr.o80.gamelib.service

import fr.o80.gamelib.service.cursor.CursorManager
import fr.o80.gamelib.service.i18n.Messages
import fr.o80.gamelib.service.i18n.MessagesImpl

class Services(
    val cursorManager: CursorManager,
    val messages: Messages = MessagesImpl()
)
