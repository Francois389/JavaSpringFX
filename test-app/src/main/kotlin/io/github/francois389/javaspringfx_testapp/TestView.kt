package io.github.francois389.javaspringfx_testapp

import io.github.francois389.javaspringfx.annotations.View
import io.github.francois389.javaspringfx.navigation.IView
import javafx.scene.layout.VBox
import javafx.scene.text.Text

@View
class TestView : IView {
    override fun createUI() = VBox().apply {
        Text("Test").let(children::add)
    }
}