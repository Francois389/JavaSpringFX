package io.github.francois389.javaspringfx.navigation

import javafx.scene.Parent
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
    private var currentView: Parent? = null

    fun getRoot(): Parent {
        return contentPane
    }

    @Suppress("unused")
    fun <T : IView> navigateTo(viewClass: KClass<T>) {
        currentView?.let { contentPane.children.remove(it) }

        val uiPane = findView(viewClass)

        contentPane.children.add(uiPane)
        currentView = uiPane
    }

    fun <T : IView> findView(viewClass: KClass<T>): Parent {
        val view = try {
            applicationContext.getBean(viewClass.java)
        } catch (e: BeansException) {
            logger.error("View ${viewClass.simpleName} not found in applicationContext.")
            throw e
        }
        val uiPane = view.createUI()
        return uiPane
    }


}