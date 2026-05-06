pluginManagement {
    includeBuild("build-logic")
}

rootProject.name = "oolang"

include(":oolang-ast")
include(":oolang-compiler")
include(":oolang-parser")

project(":oolang-ast").projectDir = file("ast")
project(":oolang-compiler").projectDir = file("compiler")
project(":oolang-parser").projectDir = file("parser")
