package common.jelastic

import jetbrains.buildServer.configs.kotlin.BuildSteps

fun BuildSteps.exposeKubernetesApiServer(
    envName: String,
    envPropsQueries: List<Pair<String, String>>? = null,
    dockerToolsTag: String,
): BuildSteps {
    val steps = BuildSteps()

    val openApiStep = createEnvironment(
        envName = envName,
        manifestUrl = "https://raw.githubusercontent.com/softozor/teamcity-common/main/jelastic/open_kubernetes_api.yaml",
        dockerToolsTag = dockerToolsTag,
        envPropsQueries = envPropsQueries,
    )
    openApiStep.name = "Open Kubernetes API"

    steps.step(openApiStep)

    steps.step(
        waitUntilKubernetesApiIsUp()
    )

    return steps
}