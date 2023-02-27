package common.jelastic

import jetbrains.buildServer.configs.kotlin.BuildSteps

fun BuildSteps.exposeKubernetesApiServer(
    envName: String,
    envPropsQueries: List<Pair<String, String>>? = null,
    dockerToolsTag: String,
): BuildSteps {
    val steps = BuildSteps()

    steps.step(
        createEnvironment(
            envName = envName,
            manifestUrl = "https://raw.githubusercontent.com/softozor/teamcity-common/main/jelastic/open_kubernetes_api.yaml",
            dockerToolsTag = dockerToolsTag,
            envPropsQueries = envPropsQueries,
        )
    )

    steps.step(
        waitUntilKubernetesApiIsUp()
    )

    return steps
}