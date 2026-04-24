package ovh.fsp.javaspringfx

import ovh.fsp.javaspringfx.navigation.Navigator
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.stage.Stage
import org.springframework.beans.factory.getBean
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext

lateinit var appClass: Class<*>
var appTitle: String = "JavaSpringFX App"
var appWidth: Double = 800.0
var appHeight: Double = 600.0
lateinit var onStart: (Navigator) -> Unit

inline fun <reified T : Any> launchApp(
    title: String = "JavaSpringFX App",
    width: Double = 800.0,
    height: Double = 600.0,
    noinline start: (Navigator) -> Unit
) {
    appClass = T::class.java
    appTitle = title
    appWidth = width
    appHeight = height
    onStart = start
    Application.launch(FxLauncher::class.java)
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
            show()
        }
    }

    override fun stop() {
        springContext.close()
        Platform.exit()
    }
}