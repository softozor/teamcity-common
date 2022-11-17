package common.docker

import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.DockerCommandStep
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand

fun BuildSteps.buildDockerImage(imageName: String): DockerCommandStep {
    return dockerCommand {
        name = "Build Docker Image '$imageName'"
        commandType = build {
            source = file {
                path = "$imageName/Dockerfile"
            }
            contextDir = imageName
            platform = jetbrains.buildServer.configs.kotlin.buildSteps.DockerCommandStep.ImagePlatform.Linux
            namesAndTags =
                "%system.docker-registry.hosted%/docker-tools/$imageName:%build.vcs.number%"
            commandArgs = "--pull --build-arg DOCKER_REGISTRY=%system.docker-registry.group%/"
        }
    }
}