/*
 * Copyright 2010 the original author or authors.
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
package packager.fakes

import org.gradle.api.*
import org.gradle.api.artifacts.*
import org.gradle.api.artifacts.dsl.*
import org.gradle.api.file.*
import org.gradle.api.initialization.dsl.*
import org.gradle.api.invocation.*
import org.gradle.api.logging.*
import org.gradle.api.plugins.*
import org.gradle.api.specs.*
import org.gradle.api.tasks.*
import org.gradle.process.*

final class TestProject implements Project {

    private final String name
    private final String version

    TestProject() {

    }

    TestProject(String name, String version) {
        this.name = name
        this.version = version
    }

    Project getRootProject() {
        return null
    }

    File getRootDir() {
        return null
    }

    File buildDir
    File buildDir() {
        buildDir
    }

    void setBuildDir(Object o) {
        buildDir = o
    }

    String getBuildDirName() {
        return null
    }

    void setBuildDirName(String s) {

    }

    File getBuildFile() {
        return null
    }

    Project getParent() {
        return null
    }

    String getName() {
        name
    }

    String getDescription() {
        return null
    }

    void setDescription(String s) {

    }

    Object getGroup() {
        return null
    }

    void setGroup(Object o) {

    }

    Object getVersion() {
        version
    }

    void setVersion(Object o) {

    }

    Object getStatus() {
        return null
    }

    void setStatus(Object o) {

    }

    Map<String, Project> getChildProjects() {
        return null
    }

    Set<Project> getDependsOnProjects() {
        return null
    }

    Project getProject() {
        return null
    }

    Set<Project> getAllprojects() {
        return null
    }

    Set<Project> getSubprojects() {
        return null
    }

    Project usePlugin(String s) {
        return null
    }

    Project usePlugin(Class<? extends Plugin> aClass) {
        return null
    }

    Task task(String s) {
        def task = new TestTask(s)
        tasks << task
        return task
    }

    Task task(Map<String, ?> stringMap, String s) {
        return null
    }

    Task task(Map<String, ?> stringMap, String s, Closure closure) {
        return null
    }

    Task task(String s, Closure closure) {
        return null
    }

    Task createTask(String s) {
        return null
    }

    Task createTask(String s, Action<? super Task> action) {
        return null
    }

    Task createTask(Map<String, ?> stringMap, String s) {
        return null
    }

    Task createTask(Map<String, ?> stringMap, String s, Action<? super Task> action) {
        return null
    }

    Task createTask(String s, Closure closure) {
        return null
    }

    Task createTask(Map<String, ?> stringMap, String s, Closure closure) {
        return null
    }

    String getPath() {
        return null
    }

    List<String> getDefaultTasks() {
        return null
    }

    void setDefaultTasks(List<String> strings) {

    }

    void defaultTasks(String... strings) {

    }

    void dependsOn(String s) {

    }

    void dependsOn(String s, boolean b) {

    }

    Project evaluationDependsOn(String s) {
        return null
    }

    Project childrenDependOnMe() {
        return null
    }

    Project dependsOnChildren() {
        return null
    }

    Project dependsOnChildren(boolean b) {
        return null
    }

    Project findProject(String s) {
        return null
    }

    Project project(String s) {
        return null
    }

    Project project(String s, Closure closure) {
        return null
    }

    Map<Project, Set<Task>> getAllTasks(boolean b) {
        return null
    }

    Set<Task> getTasksByName(String s, boolean b) {
        tasks.findAll {it.name == s} as Set<Task>
    }

    Task getTask(String name) {
        tasks.find {it.name == name}
    }

    File getProjectDir() {
        return null
    }

    File file(Object o) {
        return null
    }

    File file(Object o, PathValidation pathValidation) {
        return null
    }

    URI uri(Object o) {
        return null
    }

    String relativePath(Object o) {
        return null
    }

    ConfigurableFileCollection files(Object... objects) {
        return null
    }

    ConfigurableFileCollection files(Object o, Closure closure) {
        return null
    }

    ConfigurableFileTree fileTree(Object o) {
        return null
    }

  @Override
  ConfigurableFileTree fileTree(final Object o, final Closure closure) {
    return null
  }

  ConfigurableFileTree fileTree(Map<String, ?> stringMap) {
        return null
    }

    ConfigurableFileTree fileTree(Closure closure) {
        return null
    }

    FileTree zipTree(Object o) {
        return null
    }

    FileTree tarTree(Object o) {
        return null
    }

    File mkdir(Object o) {
        return null
    }

    boolean delete(Object... objects) {
        return false
    }

    ExecResult javaexec(Closure closure) {
        return null
    }

    ExecResult exec(Closure closure) {
        return null
    }

    String absolutePath(String s) {
        return null
    }

    String absoluteProjectPath(String s) {
        return null
    }

    String relativeProjectPath(String s) {
        return null
    }

    AntBuilder getAnt() {
        return null
    }

    AntBuilder createAntBuilder() {
        return null
    }

    AntBuilder ant(Closure closure) {
        return null
    }

    ConfigurationContainer getConfigurations() {
        return null
    }

    void configurations(Closure closure) {

    }

    ArtifactHandler getArtifacts() {
        return null
    }

    void artifacts(Closure closure) {

    }

    Convention getConvention() {
        convention
    }

    int depthCompare(Project project) {
        return 0
    }

    int getDepth() {
        return 0
    }

    TaskContainer getTasks() {
        return null
    }

    void subprojects(Action<? super Project> action) {

    }

    void subprojects(Closure closure) {

    }

    void allprojects(Action<? super Project> action) {

    }

    void allprojects(Closure closure) {

    }

    void beforeEvaluate(Action<? super Project> action) {

    }

    void afterEvaluate(Action<? super Project> action) {

    }

    void beforeEvaluate(Closure closure) {

    }

    void afterEvaluate(Closure closure) {

    }

    boolean hasProperty(String s) {
        return false
    }

    Map<String, ?> getProperties() {
        return null
    }

    Object property(String s) {
        return null
    }

    Logger getLogger() {
        return null
    }

    Gradle getGradle() {
        return null
    }

    LoggingManager getLogging() {
        return null
    }

    void disableStandardOutputCapture() {

    }

    void captureStandardOutput(LogLevel logLevel) {

    }

    Object configure(Object o, Closure closure) {
        return null
    }

    Iterable<?> configure(Iterable<?> iterable, Closure closure) {
        return null
    }

    def <T> Iterable<T> configure(Iterable<T> tIterable, Action<? super T> action) {
        return null
    }

    RepositoryHandler getRepositories() {
        return null
    }

    void repositories(Closure closure) {

    }

    RepositoryHandler createRepositoryHandler() {
        return null
    }

    DependencyHandler getDependencies() {
        return null
    }

    void dependencies(Closure closure) {

    }

    PluginContainer getPlugins() {
        return null
    }

    ScriptHandler getBuildscript() {
        return null
    }

    void buildscript(Closure closure) {

    }

    WorkResult copy(Closure closure) {
        return null
    }

    CopySpec copySpec(Closure closure) {
        return null
    }

    void apply(Closure closure) {

    }

    void apply(Map<String, ?> stringMap) {

    }

    ProjectState getState() {
        return null
    }

    def <T> NamedDomainObjectContainer<T> container(Class<T> tClass) {
        return null
    }

    def <T> NamedDomainObjectContainer<T> container(Class<T> tClass, NamedDomainObjectFactory<T> tNamedDomainObjectFactory) {
        return null
    }

    def <T> NamedDomainObjectContainer<T> container(Class<T> tClass, Closure closure) {
        return null
    }

  @Override
  ExtensionContainer getExtensions() {
    return null
  }

  @Override
  org.gradle.api.resources.ResourceHandler getResources() {
    return null
  }

  int compareTo(Project t) {
        return 0
    }

    private static final class TestTask implements Task {

        private final String name
        private final Closure closure

        TestTask(String name) {
            this.name = name
            this.closure = {}
        }

        TestTask(Closure c) {
            this.name = ''
            this.closure = c
        }

        String getName() {
            name
        }

        def execute() {
            closure()
        }

        Project getProject() {
            return null
        }

        List<Action<? super Task>> getActions() {
            return null
        }

        void setActions(List<Action<? super Task>> actions) {

        }

        TaskDependency getTaskDependencies() {
            return null
        }

        Set<Object> getDependsOn() {
            return null
        }

        void setDependsOn(Iterable<?> iterable) {

        }

        Task dependsOn(Object... objects) {
            return null
        }

        void onlyIf(Closure closure) {

        }

        void onlyIf(Spec<? super Task> spec) {

        }

        void setOnlyIf(Closure closure) {

        }

        void setOnlyIf(Spec<? super Task> spec) {

        }

        TaskState getState() {
            return null
        }

        void setDidWork(boolean b) {

        }

        boolean getDidWork() {
            return false
        }

        String getPath() {
            return null
        }

        Task doFirst(Action<? super Task> action) {
            return null
        }

        Task doFirst(Closure closure) {
            return null
        }

        Task doLast(Action<? super Task> action) {
            return null
        }

        Task doLast(Closure closure) {
            return null
        }

        Task leftShift(Closure closure) {
            def task = new TestTask(closure)
            tasks << task
            return task
        }

        Task deleteAllActions() {
            return null
        }

        boolean getEnabled() {
            return false
        }

        void setEnabled(boolean b) {

        }

        Task configure(Closure closure) {
            return null
        }

        AntBuilder getAnt() {
            return null
        }

        Logger getLogger() {
            return null
        }

        LoggingManager getLogging() {
            return null
        }

        Task disableStandardOutputCapture() {
            return null
        }

        Task captureStandardOutput(LogLevel logLevel) {
            return null
        }

        Object property(String s) {
            return null
        }

        boolean hasProperty(String s) {
            return false
        }

        Convention getConvention() {
            return null
        }

        String getDescription() {
            return null
        }

        void setDescription(String s) {

        }

        String getGroup() {
            return null
        }

        void setGroup(String s) {

        }

        boolean dependsOnTaskDidWork() {
            return false
        }

        TaskInputs getInputs() {
            return null
        }

        TaskOutputs getOutputs() {
            return null
        }

        File getTemporaryDir() {
            return null
        }

        int compareTo(Task t) {
            return 0
        }

        private def tasks = []

        @Override
        ExtensionContainer getExtensions() {
            return null
        }
    }

    private def tasks = []
    private Convention convention = new TestConvention()

    private static final class TestConvention implements Convention {
        private def plugins = [:]

        Map<String, Object> getPlugins() {
            plugins
        }

        def <T> T getPlugin(Class<T> tClass) {
            return null
        }

        def <T> T findPlugin(Class<T> tClass) {
            return null
        }

      @Override
      org.gradle.api.internal.DynamicObject getExtensionsAsDynamicObject() {
        return null
      }

      boolean hasProperty(String s) {
            return false
        }

        Map<String, ?> getProperties() {
            return null
        }

        boolean hasMethod(String s, Object... objects) {
            return false
        }

        Object invokeMethod(String s, Object... objects) {
            return null
        }

      @Override
      void add(final String s, final Object o) {
      }

      @Override
      void add(final String s, final Class<?> aClass, final Object... objects) {
      }

      @Override
      def <T> T getByType(final Class<T> tClass) {
        return null
      }

      @Override
      def <T> T findByType(final Class<T> tClass) {
        return null
      }

      @Override
      Object getByName(final String s) {
        return null
      }

      @Override
      Object findByName(final String s) {
        return null
      }
    }
}
