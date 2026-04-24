package ovh.fsp.javaspringfx.navigation

import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class Navigator(private val applicationContext: ApplicationContext) {

    private lateinit var contentPane: StackPane
    private var currentView: Pane? = null

    fun initializeRoot(): StackPane {
        contentPane = StackPane()
        return contentPane
    }

    fun <T : IView> navigateTo(viewClass: KClass<T>) {
        currentView?.let { contentPane.children.remove(it) }

        val view = applicationContext.getBean(viewClass.java)
        val uiPane = view.createUI()

        contentPane.children.add(uiPane)
        currentView = uiPane
    }
}