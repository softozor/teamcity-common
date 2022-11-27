package common.python

import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.script

fun BuildSteps.publishPythonPackageToHosted(dockerToolsTag: String): ScriptBuildStep {
    return script {
        name = "Publish"
        scriptContent = """
                #! /bin/sh

                set -e

                poetry publish -r pypi-hosted
            """.trimIndent()
        this.dockerImage = "%system.docker-registry.group%/docker-tools/poetry:$dockerToolsTag"
        dockerPull = true
        dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
    }
}
