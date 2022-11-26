package common.python

import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.script

fun BuildSteps.buildPythonPackage(dockerToolsTag: String): ScriptBuildStep {
    return script {
        name = "Build"
        scriptContent = """
                #! /bin/sh

                poetry config http-basic.pypi-group %system.package-manager.deployer.username% %system.package-manager.deployer.password%
                poetry build
            """.trimIndent()
        this.dockerImage = "%system.docker-registry.group%/docker-tools/poetry:$dockerToolsTag"
        dockerPull = true
        dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
    }
}
