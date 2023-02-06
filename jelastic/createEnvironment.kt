package common.jelastic

import common.scripts.readScript
import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.PythonBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.python

fun BuildSteps.createEnvironment(
    envName: String,
    manifestUrl: String,
    envPropsQueries: List<Pair<String, String>>,
    outputSuccessTextFile: String,
    jsonSettingsFile: String? = null,
    region: String? = null,
    dockerToolsTag: String,
    workingDir: String = "",
): PythonBuildStep {
    val jsonSettingsFileOption = if(jsonSettingsFile == null) "" else "--json-settings-file $jsonSettingsFile"
    val regionOption = if(region == null) "" else "--region $region"
    val envPropsQueriesOption = envPropsQueries.joinToString(" --env-prop-query ", prefix = "--env-prop-query "){"${it.first} '${it.second}'"}

    return python {
        name = "Create Jelastic Environment '$envName'"
        command = script {
            content = readScript("common/jelastic/create_environment.py")
            scriptArguments = """
                    --jelastic-api-url %system.jelastic.api-url%
                    --jelastic-access-token %system.jelastic.access-token%
                    --env-name $envName
                    --manifest-url $manifestUrl
                    $envPropsQueriesOption
                    --output-success-text-file $outputSuccessTextFile
                    $jsonSettingsFileOption
                    $regionOption
                """.trimIndent()
        }
        dockerImage = "%system.docker-registry.group%/docker-tools/jelastic:$dockerToolsTag"
        dockerPull = true
        dockerImagePlatform = PythonBuildStep.ImagePlatform.Linux
        this.workingDir = workingDir
    }
}