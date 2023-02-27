package common.jelastic

import common.scripts.readScript
import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.PythonBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.python

fun BuildSteps.getEnvironmentProperties(
    envName: String,
    envPropsQueries: List<Pair<String, String>>,
    dockerToolsTag: String,
): PythonBuildStep {
    val step = createEnvironment(
        envName = envName,
        manifestUrl = "https://raw.githubusercontent.com/softozor/teamcity-common/jelastic/get_env_props.yaml",
        envPropsQueries = envPropsQueries,
        dockerToolsTag = dockerToolsTag,
    )

    step.name = "Get Environment Properties From '$envName'"

    return step
}