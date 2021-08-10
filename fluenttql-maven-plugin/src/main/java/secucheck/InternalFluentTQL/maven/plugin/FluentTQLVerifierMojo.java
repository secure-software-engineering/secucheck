package secucheck.InternalFluentTQL.maven.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
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
 * limitations under the License.
 */

import secucheck.FluentTQLClassLoader.ErrorModel;
import secucheck.FluentTQLClassLoader.Errors;
import secucheck.FluentTQLClassLoader.JarClassLoaderUtils;
import secucheck.InternalFluentTQL.dsl.exception.DuplicateTaintFlowQueryIDException;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the FluentTQL Mojo to verify the FluentTQL specifications project during the PACKAGE phase.
 *
 */
@Mojo(name = "verify", defaultPhase = LifecyclePhase.PACKAGE)
public class FluentTQLVerifierMojo extends AbstractMojo {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    @Component
    private MavenProject project;

    @Parameter(defaultValue = "${project.build.directory}${file.separator}${project.build.finalName}.${project.packaging}")
    private String archivePath;

    /**
     * This executes during PACKAGE phase and verifies the compiled FluentTQL specification Jar.
     *
     * @throws MojoExecutionException If there is an error in the FluentTQL Specifications
     */
    public void execute() throws MojoExecutionException {
        System.out.println("\n" +
                "          //////  " + ANSI_BLUE + "Fluent" + ANSI_RESET + "  \\\\\\\\\\\\\n" +
                "         //////   " + ANSI_GREEN + "T" + ANSI_RESET + "aint" + "    \\\\\\\\\\\\ \n" +
                "        //////              \\\\\\\\\\\\    " + ANSI_BLUE + "Fluent" +
                ANSI_RESET + ANSI_GREEN + "T" + ANSI_RESET + ANSI_YELLOW + "Q" + ANSI_RESET + ANSI_RED + "L\n" + ANSI_RESET +
                "        \\\\\\\\\\\\    " + ANSI_YELLOW + "Q" + ANSI_RESET + "uery     //////\n" +
                "         \\\\\\\\\\\\   " + ANSI_RED + "L" + ANSI_RESET + "anguage //////\n\n"
        );

        if (!"jar".equals(project.getPackaging().toLowerCase()))
            throw new MojoExecutionException("Given FluentTQL Specifications Project is not packaged as Jar. Please package it in " +
                    "Jar.");

        File file = new File(archivePath);

        if (!file.exists())
            throw new MojoExecutionException(file.getAbsolutePath() +
                    " does not exist. Please compile the project and then run FluentTQL-Verifier");

        JarClassLoaderUtils jarClassLoaderUtils = new JarClassLoaderUtils();
        try {
            jarClassLoaderUtils.loadAppAndGetFluentTQLSpecification(file.getAbsolutePath(), true);
        } catch (DuplicateTaintFlowQueryIDException e) {
            throw new MojoExecutionException(e.getMessage());
        }

        createErrorText(jarClassLoaderUtils.getErrorModel());
    }

    /**
     * This method creates the FluentTQLError.txt file in the project's root path if there is an error in the FluentTQL specifications.
     *
     * @param errors Errors
     * @throws MojoExecutionException Indicates that there is an error in the FluentTQL specification project.
     */
    private void createErrorText(Errors errors) throws MojoExecutionException {
        String projectRootPath = project.getBasedir().getAbsolutePath();

        if (errors.getErrors().size() > 0) {
            File file = new File(projectRootPath + System.getProperty("file.separator") + "FluentTQLError.txt");

            try {
                if (file.exists()) {
                    file.delete();
                    file.createNewFile();
                }

                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

                bufferedWriter.write("FluentTQL:     " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");

                for (ErrorModel errorModel : errors.getErrors()) {
                    bufferedWriter.append(errorModel.toString());
                }

                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileUtils.deleteDirectory(new File(project.getBuild().getDirectory()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            throw new MojoExecutionException("There are some errors while verifying the FluentTQL classes. Please refer to " +
                    "the \"" + file.getAbsolutePath() + "\" file for the detailed errors");
        }
    }
}
