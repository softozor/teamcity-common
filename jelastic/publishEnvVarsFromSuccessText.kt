package common.jelastic

import common.scripts.readScript
import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.PythonBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.python

fun BuildSteps.publishEnvVarsFromSuccessText(
    successTextFile: String,
    dockerToolsTag: String,
): PythonBuildStep {
    return python {
        name = "Publish Environment Variables From Success Text"
        command = script {
            content = readScript("common/publish_env_vars_from_success_text.py")
            scriptArguments = """
                    --success-text-file $successTextFile
                """.trimIndent()
        }
        dockerImage = "%system.docker-registry.group%/docker-tools/jelastic:$dockerToolsTag"
        dockerPull = true
        dockerImagePlatform = PythonBuildStep.ImagePlatform.Linux
        this.workingDir = workingDir
    }
}