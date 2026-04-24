package ovh.fsp.javaspringfx.navigation

import javafx.scene.layout.Pane

interface IView {
    fun createUI(): Pane
}