package fr.o80.gamelib.menu.renderer

import fr.o80.gamelib.dsl.Draw
import fr.o80.gamelib.menu.view.MenuView

/**
 * Based on margins adn paddings, it shows boxes around the view.
 */
fun Draw.drawDebug(view: MenuView) {
    color(1f, 0f, 0f)
    rect(
        view.bounds.left,
        view.bounds.top,
        view.bounds.right,
        view.bounds.bottom
    )
    color(.4f, 0f, 0f)
    rect(
        view.bounds.left + view.horizontalMargin,
        view.bounds.top + view.verticalMargin,
        view.bounds.right - view.horizontalMargin,
        view.bounds.bottom - view.verticalMargin
    )
    color(.4f, 0f, 0f)
    rect(
        view.bounds.left + view.horizontalMargin + view.horizontalPadding,
        view.bounds.top + view.verticalMargin + view.verticalPadding,
        view.bounds.right - view.horizontalMargin - +view.horizontalPadding,
        view.bounds.bottom - view.verticalMargin - view.verticalPadding
    )
}
