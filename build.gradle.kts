
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    idea
    application
    kotlin("jvm") version "1.3.50"
    kotlin("kapt") version "1.3.50"
    jacoco
}

jacoco {
    toolVersion = "0.8.5"
}

val props = File("version.properties")
    .readLines()
    .associate {
        val (k, v) = (it.split('=', limit = 2))
        k to v
    }.also {
        logger.warn("=========================")
        logger.warn("Project version info")
        logger.warn("$it")
        logger.warn("=========================")
    }

group = "iga"
version = props["versionName"] ?: error("No versionName in version.properties")

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    gradlePluginPortal()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
    maven { url = uri("https://kotlin.bintray.com/kotlin-js-wrappers") }
    maven { url = uri("https://kotlin.bintray.com/kotlinx") }
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")

    compile("org.litote.kmongo:kmongo:3.11.1")
    compile("org.litote.kmongo:kmongo-coroutine:3.11.1")

    compile ("org.mindrot:jbcrypt:0.4")

//    compile("org.jetbrains.exposed:exposed:0.17.6")

    compile("io.ktor:ktor-webjars:$ktor_version")
//    You can import some necessary JS libraries here.
//    compile("org.webjars:jquery:3.2.1")

    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("ch.qos.logback:logback-classic:$logback_version")
    compile("io.ktor:ktor-server-core:$ktor_version")
    compile("io.ktor:ktor-html-builder:$ktor_version")
    compile("org.jetbrains:kotlin-css-jvm:1.0.0-pre.31-kotlin-1.2.41")
    compile("io.ktor:ktor-server-host-common:$ktor_version")
    compile("io.ktor:ktor-locations:$ktor_version")
    compile("io.ktor:ktor-metrics:$ktor_version")
    compile("io.ktor:ktor-server-sessions:$ktor_version")
    compile("io.ktor:ktor-websockets:$ktor_version")
    compile("io.ktor:ktor-auth:$ktor_version")
    compile("io.ktor:ktor-gson:$ktor_version")

    compile("io.ktor:ktor-client-core:$ktor_version")
    compile("io.ktor:ktor-client-core-jvm:$ktor_version")
    compile("io.ktor:ktor-client-cio:$ktor_version")
    compile("io.ktor:ktor-client-auth-basic:$ktor_version")
    compile("io.ktor:ktor-client-json-jvm:$ktor_version")
    compile("io.ktor:ktor-client-gson:$ktor_version")
    compile("io.ktor:ktor-client-logging-jvm:$ktor_version")


    testCompile("io.ktor:ktor-server-tests:$ktor_version")
    testCompile ("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
    testCompile ("org.jetbrains.kotlin:kotlin-test-junit5:$kotlin_version")
    // testCompile("io.mockk:mockk:1.9")
    testCompile("org.hamcrest:hamcrest-all:1.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")

}

kapt {
    generateStubs = true
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")


val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}
/*

tasks.withType<JacocoReport> {
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.isEnabled = true
        html.destination = file("$buildDir/reports/coverage/html")
        xml.destination = file("$buildDir/reports/coverage/report.xml")
    }
    // afterEvaluate {
    //        classDirectories = files(classDirectories.files.collect {
    //            fileTree(dir: it,
    //                    exclude: ['tests/**',
    //                              'resources/**'])
    //        })
    //    }
}



tasks.withType<JacocoCoverageVerification> {
    violationRules {
        rule {
            limit {
                minimum = "0.01".toBigDecimal()
            }
        }
    }

    // afterEvaluate {
    //        classDirectories = files(classDirectories.files.collect {
    //            fileTree(dir: it,
    //                    exclude: ['tests/**',
    //                              'resources/**'])
    //        })
    //    }
}


val testCoverage by tasks.registering {
    group = "verification"
    description = "Runs the unit tests with coverage."

    dependsOn(":test", ":jacocoTestReport", ":jacocoTestCoverageVerification")

    val reportFile = project.file("$buildDir/reports/coverage/report.xml")

    doLast {
        val builderFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance()
        builderFactory.isValidating = false
        builderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        builderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        val docBuilder = builderFactory.newDocumentBuilder()
        val doc = docBuilder.parse(reportFile)

        val l = doc.documentElement.childNodes

        var counter : org.w3c.dom.Element? = null

        for (i in 0 until l.length) {
            val e = l.item(i)
            if (e is org.w3c.dom.Element) {
                if (e.tagName == "counter" && e.getAttribute("type") == "LINE") {
                    counter = e
                }
            }
        }

        val missed = counter!!.getAttribute("missed").toDouble()
        val covered = counter!!.getAttribute("covered").toDouble()
        val total = missed + covered
        val percentage = covered / total * 100

        println("Missed lines " + missed)
        println("Covered lines " +  covered)
        println("Total " + "%.2f".format(percentage) + "%")
    }
} */

