package io.github.francois389.javaspringfx_testapp

import io.github.francois389.javaspringfx.annotations.View
import io.github.francois389.javaspringfx.navigation.IView
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.VBox

@View
class AutreView : IView {
    override fun createUI() = VBox().apply {
        alignment = Pos.CENTER
        padding = Insets(10.0)
        children.addAll(
            Label("Autre vue")
        )
    }
}