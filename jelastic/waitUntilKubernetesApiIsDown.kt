package common.jelastic

import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.script

fun BuildSteps.waitUntilKubernetesApiIsDown(): ScriptBuildStep {
    return script {
        name = "Wait Until Kubernetes API Is Down"
        scriptContent = """
            #! /bin/sh
            
            for i in ${'$'}(seq 1 120) ; do
                status_code=${'$'}(curl -s -o /dev/null -w "%{http_code}" ${'$'}KUBERNETES_API_URL/version)
                echo "status code: ${'$'}status_code"
                if [ "${'$'}status_code" = "000" ] ; then
                    break
                fi
                sleep 1
            done
            
            if [ "${'$'}i" = "120" ] ; then
              exit 1
            fi
        """.trimIndent()
    }
}
