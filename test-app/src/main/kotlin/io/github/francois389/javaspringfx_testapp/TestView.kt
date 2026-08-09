package io.github.francois389.javaspringfx_testapp

import io.github.francois389.javaspringfx.annotations.View
import io.github.francois389.javaspringfx.navigation.IView
import io.github.francois389.javaspringfx.navigation.Navigator
import javafx.animation.PauseTransition
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ProgressBar
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.scene.layout.Border
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Paint
import javafx.scene.text.Text
import javafx.util.Duration

@View
class TestView(
    private val viewModel: TestViewModel,
    private val navigator: Navigator
) : IView {
    override fun createUI() = VBox().apply {
        alignment = Pos.TOP_CENTER
        spacing = 10.0
        padding = Insets(10.0)

        children.addAll(
            Text().apply {
                textProperty().bind(viewModel.testSaisie)
            },
            TextField().apply {
                textProperty().bindBidirectional(viewModel.testSaisie)
            },
            Slider(0.5, 5.0, 0.5).apply {
                valueProperty().bindBidirectional(viewModel.duration)
                disableProperty().bind(viewModel.chargement)
                isShowTickMarks = true
                isShowTickLabels = true
            },
            Button().apply {
                textProperty().bind(viewModel.duration.map { "Charger une vue en ${"%.${2}f".format(it.toDouble())} secondes" })
                setOnAction {
                    autreVue.children.clear()
                    viewModel.chargement.value = true

                    PauseTransition().apply {
                        durationProperty().bind(viewModel.duration.map { Duration.seconds(it.toDouble()) })
                        viewModel.progress.bind(
                            currentTimeProperty()
                                .map { it.toSeconds() / duration.toSeconds() }
                        )
                        onFinished = {
                            viewModel.chargement.value = false
                            autreVue.children.add(
                                navigator.findView(AutreView::class)
                            )
                        }

                    }.play()

                }
            },
            chargementSpinner,
            autreVue
        )
    }

    val chargementSpinner = ProgressBar().apply {
        visibleProperty().bind(viewModel.chargement)
        managedProperty().bind(viewModel.chargement)
        progressProperty().bind(viewModel.progress)
    }

    val autreVue = Pane().apply {
        border = Border.stroke(Paint.valueOf("black"))
    }
}