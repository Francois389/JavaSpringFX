package ovh.fsp.javaspringfx.navigation

import javafx.scene.Parent

interface IView {
    fun createUI(): Parent
}