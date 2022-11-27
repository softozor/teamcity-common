package common.python

import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.script

fun BuildSteps.setupBasicAuth(): ScriptBuildStep {
    return script {
        name = "Setup Basic Auth"
        scriptContent = """
                #! /bin/sh

                poetry config http-basic.pypi-group %system.package-manager.deployer.username% %system.package-manager.deployer.password%
                poetry config http-basic.pypi-hosted %system.package-manager.deployer.username% %system.package-manager.deployer.password%
            """.trimIndent()
    }
}
