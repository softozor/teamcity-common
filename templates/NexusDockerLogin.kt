package common.templates

import jetbrains.buildServer.configs.kotlin.Template
import jetbrains.buildServer.configs.kotlin.buildFeatures.dockerSupport

object NexusDockerLogin : Template({
    name = "Nexus_Docker_Login"

    features {
        dockerSupport {
            cleanupPushedImages = true
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_3,PROJECT_EXT_2"
            }
        }
    }
})
