pluginManagement {
    includeBuild("build-logic")
}

rootProject.name = "oolang"

include(":oolang-ast")
include(":oolang-parser")

project(":oolang-ast").projectDir = file("ast")
project(":oolang-parser").projectDir = file("parser")
