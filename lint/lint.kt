package common.lint

import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.script

fun BuildSteps.lint(): ScriptBuildStep {
    return script {
        name = "Check Code Formatting"
        scriptContent = """
            run --all-files
        """.trimIndent()
        dockerImage = "%system.docker-registry.group%/fxinnovation/pre-commit:latest"
        dockerPull = true
        dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
    }
}