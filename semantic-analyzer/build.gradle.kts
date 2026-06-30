plugins {
    id("oolang-commons")
}

dependencies {
    api(project(":oolang-ast"))

    testImplementation(project(":oolang-parser"))
}
