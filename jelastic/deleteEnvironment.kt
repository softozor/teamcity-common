package common.jelastic

import common.scripts.readScript
import jetbrains.buildServer.configs.kotlin.BuildStep
import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.PythonBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.python

fun BuildSteps.deleteEnvironment(
    envName: String,
    dockerToolsTag: String
): PythonBuildStep {
    return python {
        name = "Delete Jelastic Environment '$envName'"
        command = script {
            content = readScript("common/jelastic/delete_environment.py")
            scriptArguments = """
                    --jelastic-api-url %system.jelastic.api-url%
                    --jelastic-access-token %system.jelastic.access-token%
                    --env-name $envName
                """.trimIndent()
        }
        dockerImage = "%system.docker-registry.group%/docker-tools/jelastic:$dockerToolsTag"
        dockerPull = true
        dockerImagePlatform = PythonBuildStep.ImagePlatform.Linux
        executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
    }
}