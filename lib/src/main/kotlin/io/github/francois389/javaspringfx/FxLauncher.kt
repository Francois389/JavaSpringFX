package io.github.francois389.javaspringfx

import io.github.francois389.javaspringfx.navigation.IView
import io.github.francois389.javaspringfx.navigation.Navigator
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import org.springframework.beans.factory.getBean
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import kotlin.reflect.KClass

lateinit var appClass: Class<*>
var appTitle: String = "JavaSpringFX App"
var appWidth: Double = 800.0
var appHeight: Double = 600.0
var appIcons: List<Image>? = null
lateinit var onStart: (Navigator) -> Unit

inline fun <reified T : Any> launchApp(
    title: String,
    width: Double = 800.0,
    height: Double = 600.0,
    icons: List<String>? = null,
    startingView: KClass<out IView>
) = launchApp<T>(
    title = title,
    width = width,
    height = height,
    icons = icons,
    start = { navigator ->
        navigator.navigateTo(startingView)
    }
)

inline fun <reified T : Any> launchApp(
    title: String = "JavaSpringFX App",
    width: Double = 800.0,
    height: Double = 600.0,
    icons: List<String>? = null,
    noinline start: (Navigator) -> Unit
) {
    appClass = T::class.java
    appTitle = title
    appWidth = width
    appHeight = height
    icons
        ?.map { loadImageFromResource(T::class.java, it) }
        ?.toList()
        ?.let { appIcons = it }
    onStart = start
    Application.launch(FxLauncher::class.java)
}

fun loadImageFromResource(applicationClass: Class<*>, imagePath: String): Image {
    return applicationClass.getResourceAsStream(imagePath)?.use { inputStream ->
        Image(inputStream)
    } ?: throw IllegalArgumentException("Image resource '$imagePath' not found with class '${applicationClass.name}'")
}


class FxLauncher : Application() {
    private lateinit var springContext: ConfigurableApplicationContext

    override fun init() {
        springContext = SpringApplication.run(appClass)
    }

    override fun start(stage: Stage) {
        val navigator = springContext.getBean<Navigator>()
        val rootPane = navigator.getRoot()

        onStart(navigator)

        stage.apply {
            scene = Scene(rootPane, appWidth, appHeight)
            title = appTitle
            icons.addAll(appIcons ?: emptyList())
            show()
        }
    }

    override fun stop() {
        springContext.close()
        Platform.exit()
    }
}