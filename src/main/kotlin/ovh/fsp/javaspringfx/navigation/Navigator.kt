package ovh.fsp.javaspringfx.navigation

import javafx.scene.Parent
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import org.slf4j.LoggerFactory
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class Navigator(
    private val applicationContext: ApplicationContext
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val contentPane: StackPane = StackPane()
    private var currentView: Pane? = null

    fun getRoot(): Parent {
        return contentPane
    }

    @Suppress("unused")
    fun <T : IView> navigateTo(viewClass: KClass<T>) {
        currentView?.let { contentPane.children.remove(it) }

        val view = try {
            applicationContext.getBean(viewClass.java)
        } catch (e: BeansException) {
            logger.error("Failed to navigate to view ${viewClass.simpleName}")
            throw e
        }
        val uiPane = view.createUI()

        contentPane.children.add(uiPane)
        currentView = uiPane
    }
}