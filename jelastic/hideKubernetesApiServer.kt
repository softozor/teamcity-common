package common.jelastic

import jetbrains.buildServer.configs.kotlin.BuildSteps

fun BuildSteps.hideKubernetesApiServer(
    envName: String,
    dockerToolsTag: String,
): BuildSteps {
    val steps = BuildSteps()

    val closeKubernetesApi = createEnvironment(
        envName = envName,
        manifestUrl = "https://raw.githubusercontent.com/softozor/teamcity-common/main/jelastic/close_kubernetes_api.yaml",
        dockerToolsTag = dockerToolsTag,
    )
    closeKubernetesApi.name = "Close Kubernetes API"

    steps.step(closeKubernetesApi)
    steps.step(
        waitUntilKubernetesApiIsDown()
    )

    return steps
}