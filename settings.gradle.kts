rootProject.name = "product-order"

include("core")

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val asciidoctorConvertVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.kapt" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.jpa" -> useVersion(kotlinVersion)
                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion(springDependencyManagementVersion)
                "org.asciidoctor.jvm.convert" -> useVersion(asciidoctorConvertVersion)
            }
        }
    }
}
include("core:core-api")
findProject(":core:core-api")?.name = "core-api"
include("storage")
include("support")
include("support:logging")
findProject(":support:logging")?.name = "logging"
include("storage:db-core")
findProject(":storage:db-core")?.name = "db-core"
include("storage:redis-core")
findProject(":storage:redis-core")?.name = "redis-core"
