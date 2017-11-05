/*
 * Copyright 2015 Intershop Communications AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.intershop.gradle.jaxb.extension

import groovy.transform.CompileStatic
import org.gradle.api.Named
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.FileCollection
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.util.GUtil

/**
 * Java to schema extension
 * This is the configuration for schema generation.
 */
@CompileStatic
class JavaToSchema implements Named {

    private final Project project
    final String name

    /**
     * Java files are the base for the generation
     */
    private final Property<FileCollection> javaFiles

    Provider<FileCollection> getJavaFilesProvider() {
        return javaFiles
    }

    FileCollection getJavaFiles() {
        return javaFiles.get()
    }

    void setJavaFiles(FileCollection javaFiles) {
        this.javaFiles.set(javaFiles)
    }

    /**
     * A map of name space configurations
     */
    private final Property<Map<String, String>> namespaceconfigs

    Provider<Map<String, String>> getNamespaceconfigsProvider() {
        return namespaceconfigs
    }

    Map<String, String> getNamespaceconfigs() {
        return namespaceconfigs.get()
    }

    void setNamespaceconfigs(Map<String, String> namespaceconfigs) {
        this.namespaceconfigs.set(namespaceconfigs)
    }

    /**
     * Special parameters see schemagen documentation
     */
    private final Property<String> episode

    Provider<String> getEpisodeProvider() {
        return episode
    }

    String getEpisode() {
        return episode.get()
    }

    void setEpisode(String episode) {
        this.episode.set(episode)
    }

    /**
     * Output path
     */
    private final Property<Directory> outputDir

    Provider<Directory> getOutputDirProvider() {
        return outputDir
    }

    Directory getOutputDir() {
        return outputDir.get()
    }

    void setOutputDir(Directory outputDir) {
        this.outputDir.set(outputDir)
    }

    JavaToSchema(Project project, String name) {
        this.project = project
        this.name = name

        javaFiles = project.objects.property(FileCollection)
        namespaceconfigs = project.objects.property(Map)
        episode = project.objects.property(String)
        outputDir = project.objects.property(Directory)

        outputDir.set(project.getLayout().getBuildDirectory().
                dir("${JaxbExtension.CODEGEN_DEFAULT_OUTPUTPATH}/${JaxbExtension.JAXB_SCHEMAGEN_OUTPUTPATH}/${name.replace(' ', '_')}"))
    }

    /**
     * Calculates the task name
     *
     * @return task name with prefix jaxbSchemaGen
     */
    String getTaskName() {
        return "jaxbSchemaGen" + GUtil.toCamelCase(name)
    }
}
