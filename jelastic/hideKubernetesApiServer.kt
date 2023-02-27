package common.jelastic

import jetbrains.buildServer.configs.kotlin.BuildSteps

fun BuildSteps.hideKubernetesApiServer(
    envName: String,
    dockerToolsTag: String,
): BuildSteps {
    val steps = BuildSteps()

    steps.step(
        createEnvironment(
            envName = envName,
            manifestUrl = "https://raw.githubusercontent.com/softozor/teamcity-common/main/jelastic/close_kubernetes_api.yaml",
            dockerToolsTag = dockerToolsTag,
        )
    )
    steps.step(
        waitUntilKubernetesApiIsDown()
    )

    return steps
}