tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(":storage:db-core"))
    implementation(project(":storage:redis-core"))
    implementation(project(":support:logging"))
}