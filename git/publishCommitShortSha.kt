package common.git

import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.script

fun BuildSteps.publishCommitShortSha(): ScriptBuildStep {
    return script {
        name = "Publish Commit Short SHA"
        scriptContent = """
            #! /bin/sh

            GIT_COMMIT_SHA=%build.vcs.number%
            GIT_COMMIT_SHORT_SHA=${'$'}(echo ${'$'}GIT_COMMIT_SHA | cut -c-8)

            echo "##teamcity[setParameter name='build.vcs.number' value='${'$'}{GIT_COMMIT_SHORT_SHA}']"
            echo "##teamcity[setParameter name='env.GIT_COMMIT_SHORT_SHA' value='${'$'}{GIT_COMMIT_SHORT_SHA}']"
        """.trimIndent()
    }
}
