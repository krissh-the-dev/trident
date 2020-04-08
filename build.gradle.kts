plugins {
    java

    application
}

repositories {
    jcenter()
}

dependencies {

    implementation("io.github.vincenzopalazzo:material-ui-swing:1.1.1_pre-release_6.1")

    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("ch.qos.logback:logback-core:1.2.3")
    implementation("org.slf4j:slf4j-api:1.7.25")

    testImplementation("junit:junit:4.12")
}

application {
    mainClassName = "org.trident.App"
}
