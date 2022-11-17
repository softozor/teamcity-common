package common.docker

import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.DockerCommandStep
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand

fun BuildSteps.pushDockerImage(imageName: String): DockerCommandStep {
    return dockerCommand {
        name = "Push Docker Image '$imageName'"
        commandType = push {
            namesAndTags = """
                %system.docker-registry.hosted%/docker-tools/$imageName:%build.vcs.number%
            """.trimIndent()
        }
    }
}