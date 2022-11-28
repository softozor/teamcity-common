package common.python

import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.script

fun BuildSteps.toxPythonPackage(dockerToolsTag: String, testArgs: List<String> = listOf(), globalPythonVersion: String = "3.8"): ScriptBuildStep {
    return script {
        name = "Test"
        scriptContent = """
                #! /bin/sh

                poetry config http-basic.pypi-group %system.package-manager.deployer.username% %system.package-manager.deployer.password%

                pyenv global $globalPythonVersion
                pyenv local 3.8 3.9 3.10 3.11
                tox -- ${testArgs.joinToString(" ")}
            """.trimIndent()
        this.dockerImage = "%system.docker-registry.group%/docker-tools/python-tests:$dockerToolsTag"
        dockerPull = true
        dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
    }
}
