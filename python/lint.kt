package common.python

import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.script

fun BuildSteps.lint(dockerToolsTag: String): ScriptBuildStep {
    return script {
        name = "Lint"
        scriptContent = """
                #! /bin/sh

                poetry config http-basic.pypi-group %system.package-manager.deployer.username% %system.package-manager.deployer.password%

                pyenv local 3.8 3.9 3.10 3.11
                tox -e lint
            """.trimIndent()
        dockerImage = "%system.docker-registry.group%/docker-tools/python-tests:$dockerToolsTag"
        dockerPull = true
        dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
    }
}
