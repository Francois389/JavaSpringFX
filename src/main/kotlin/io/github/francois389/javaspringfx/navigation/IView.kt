package io.github.francois389.javaspringfx.navigation

import javafx.scene.Parent

interface IView {
    fun createUI(): Parent
}