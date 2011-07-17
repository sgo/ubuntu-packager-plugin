package packager

import org.gradle.api.Project
import org.gradle.api.Plugin

final class UbuntuPackagerPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.convention.plugins.ubuntu = new UbuntuConvention(project)
        project.task('deb') << {packager.execute(project.convention.plugins.ubuntu.toCommands())}
        project.task('clean') << {
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
