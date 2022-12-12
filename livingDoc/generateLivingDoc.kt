package common.livingDoc

import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.ProjectFeatures
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.projectFeatures.BuildReportTab
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab

fun BuildSteps.generateLivingDocumentation(
    systemUnderTestName: String,
    pathToFeaturesDir: String,
    picklesReportDir: String,
    dockerTag: String,
): ScriptBuildStep {
    return script {
        name = "Generate Pickles Report"
        scriptContent = """
                #! /bin/sh
                
                /root/.dotnet/tools/pickles --feature-directory="$pathToFeaturesDir" \
                  --output-directory="$picklesReportDir" \
                  --system-under-test-name="$systemUnderTestName" \
                  --system-under-test-version="%build.vcs.number%" \
                  --documentation-format=dhtml \
                  --language=en \
                  --exp \
                  --et="wip" \
                  --enableComments=false
            """.trimIndent()
        dockerImage = "%system.docker-registry.group%/docker-tools:$dockerTag"
        dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
        dockerPull = true
    }
}

fun addLivingDocArtifacts(build: BuildType, reportDir: String, livingDocZip: String) {
    build.artifactRules += ", $reportDir => $livingDocZip"
}

fun ProjectFeatures.livingDocTab(livingDocZip: String): BuildReportTab {
    return buildReportTab {
        title = "Living Documentation"
        startPage = "$livingDocZip!Index.html"
    }
}