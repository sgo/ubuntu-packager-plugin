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
package packager

import org.gradle.api.Project
import org.gradle.api.Plugin

final class UbuntuPackagerPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.convention.plugins.ubuntu = new UbuntuConvention(project)
        project.task('deb') << {packager.execute(project.convention.plugins.ubuntu.toCommands())}
        project.task('cleanDeb') << {
            File work = project.convention.plugins.ubuntu.workDir
            work.deleteDir()
            work.parentFile.listFiles({File f, String name ->
                ['.tar.gz', '.deb', '.build', '.dsc'].find {name.endsWith(it)} != null
            } as FilenameFilter).each {File f->
                f.delete()
            }
        }
    }

    private final Packager packager

    UbuntuPackagerPlugin() {
        this(new UbuntuPackager())
    }

    UbuntuPackagerPlugin(Packager packager) {
        this.packager = packager
    }
}
