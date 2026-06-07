package io.github.francois389.javaspringfx_testapp

import io.github.francois389.javaspringfx.launchApp
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TestApp

fun main() = launchApp<TestApp>(title = "TestApp") { navigator ->
    navigator.navigateTo(TestView::class)
}