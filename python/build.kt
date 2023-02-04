package common.python

import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.script

fun BuildSteps.build(dockerToolsTag: String): ScriptBuildStep {
    return script {
        name = "Build"
        scriptContent = """
                #! /bin/sh

                poetry build
            """.trimIndent()
        dockerImage = "%system.docker-registry.group%/docker-tools/poetry:$dockerToolsTag"
        dockerPull = true
        dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
    }
}
