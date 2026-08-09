package io.github.francois389.javaspringfx_testapp

import io.github.francois389.javaspringfx.annotations.View
import io.github.francois389.javaspringfx.navigation.IView
import io.github.francois389.javaspringfx.navigation.Navigator
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox

@View
class AutreView(private val navigator: Navigator) : IView {
    override fun createUI() = VBox().apply {
        alignment = Pos.CENTER
        padding = Insets(10.0)
        children.addAll(
            Label("Autre vue"),
            Button("Continue").apply {
                setOnAction {
                    navigator.navigateTo(FinView::class)
                }
            }
        )
    }
}