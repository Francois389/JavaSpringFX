# JavaSpringFX

JavaSpringFX est une librairie Kotlin qui intègre **Spring Boot** et **JavaFX** pour faciliter le développement d'applications desktop selon le pattern **MVVM**.

Elle résout deux problèmes récurrents :

- L'injection de dépendances entre les couches View, ViewModel et Model via le contexte Spring
- La navigation entre les vues sans couplage fort

---

## Ajouter la dépendance

### Nouveau projet

Voici un `build.gradle.kts` complet pour démarrer un projet avec JavaSpringFX :

```kotlin
plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.spring") version "2.1.20"
    id("org.springframework.boot") version "3.4.4"
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.example"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.francois389:javaspringfx:0.1.0")
}

kotlin {
    jvmToolchain(21)
}

javafx {
    version = "21"
    modules("javafx.controls", "javafx.fxml")
}
```

### Projet existant

Si vous avez déjà un projet JavaFX, ajoutez simplement la dépendance dans votre bloc `dependencies` :

```kotlin
dependencies {
    implementation("io.github.francois389:javaspringfx:0.1.0")
}
```

Assurez-vous également d'avoir les plugins `kotlin("plugin.spring")` et `org.openjfx.javafxplugin` dans votre bloc `plugins`, et le repository `mavenCentral()` déclaré.

---

## Fonctionnement

### Point d'entrée

La librairie fournit la fonction `launchApp<T>()` qui démarre Spring Boot et JavaFX ensemble. `T` est votre classe annotée `@SpringBootApplication`.

```kotlin
@SpringBootApplication
class MyApp

fun main() = launchApp<MyApp>(
    title = "Mon Application",
    width = 1024.0,
    height = 768.0
) { navigator ->
    navigator.navigateTo(MainView::class)
}
```

Le lambda reçoit une instance du `Navigator` et permet de définir la première vue affichée au démarrage.

Les paramètres `title`, `width` et `height` sont optionnels et ont les valeurs par défaut suivantes :

| Paramètre | Valeur par défaut    |
|-----------|----------------------|
| `title`   | `"JavaSpringFX App"` |
| `width`   | `800.0`              |
| `height`  | `600.0`              |

---

### `@View` — Définir une vue

Annotez vos classes de vue avec `@View`. Elles doivent implémenter l'interface `IView` et définir leur interface via la méthode `createUI()`.

Le ViewModel est injecté automatiquement par Spring via le constructeur.

```kotlin
@View
class MainView(private val viewModel: MainViewModel) : IView {
    override fun createUI(): Pane {
        return VBox().apply {
            children.add(Label().apply {
                textProperty().bind(viewModel.message)
            })
        }
    }
}
```

> La méthode `createUI()` est appelée à chaque navigation vers la vue. Il n'y a pas de cache : la vue est reconstruite à chaque fois.

---

### `@ViewModel` — Définir un ViewModel

Annotez vos ViewModels avec `@ViewModel`. Ils exposent des propriétés observables JavaFX (`Property`) et contiennent la logique métier. Les services et repositories Spring y sont injectés automatiquement.

```kotlin
@ViewModel
class MainViewModel(private val userService: UserService) {
    val message = SimpleStringProperty("Bonjour !")

    fun loadData() {
        message.set(userService.getWelcomeMessage())
    }
}
```

---

### Navigation

Le `Navigator` est un bean Spring disponible par injection. Il permet de naviguer vers n'importe quelle vue à partir de sa classe, sans référencer les autres vues.

```kotlin
@View
class MainView(
    private val viewModel: MainViewModel,
    private val navigator: Navigator
) : IView {
    override fun createUI(): Pane {
        return VBox().apply {
            children.add(Button("Aller vers les paramètres").apply {
                setOnAction { navigator.navigateTo(SettingsView::class) }
            })
        }
    }
}
```

---

### Connexion à une base de données

JavaSpringFX étant basé sur Spring Boot, vous pouvez ajouter n'importe quelle dépendance de Spring.

**Exemple avec Spring Data JPA et PostgreSQL :**

```kotlin
dependencies {
    implementation("io.github.francois389:javaspringfx:0.1.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
}
```

```yaml
# src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: user
    password: secret
  jpa:
    hibernate:
      ddl-auto: update
```

Vous pouvez ensuite injecter vos repositories Spring Data directement dans vos ViewModels :

```kotlin
@ViewModel
class UserViewModel(private val userRepository: UserRepository) {
    val users = FXCollections.observableArrayList<User>()

    fun loadUsers() {
        users.setAll(userRepository.findAll())
    }
}
```

---

## Licence

**Apache License 2.0**

JavaSpringFX est distribué sous la [Apache License 2.0](LICENSE).

---

## Mentions légales

JavaSpringFX est un projet indépendant et n'est pas affilié à VMware, Inc. ni approuvé par VMware, Inc.
Spring est une marque déposée de VMware, Inc.
JavaFX est une marque déposée d'Oracle Corporation.
