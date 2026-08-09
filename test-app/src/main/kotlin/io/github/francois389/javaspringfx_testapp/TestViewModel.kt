package io.github.francois389.javaspringfx_testapp

import io.github.francois389.javaspringfx.annotations.ViewModel
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty

@ViewModel
class TestViewModel {

    val testSaisie = SimpleStringProperty("Texte")
    val duration = SimpleDoubleProperty(2.0)
    val chargement = SimpleBooleanProperty(false)
    val progress = SimpleDoubleProperty(0.0)
}
