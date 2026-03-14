import kotlin.jvm.optionals.getOrNull

plugins {
    id("oolang-commons")
    antlr
}

val versionCatalog: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
fun catalogVersion(lib: String) =
    versionCatalog.findVersion(lib).getOrNull()?.requiredVersion
        ?: throw GradleException("Version '$lib' is not specified in the toml version catalog")

dependencies {
    antlr("org.antlr:antlr4:${catalogVersion("antlr4")}")
}

tasks.generateGrammarSource {
    packageName = "oo.parser"
    maxHeapSize = "64m"
}
