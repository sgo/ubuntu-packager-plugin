package packager

import org.gradle.api.Project
import org.gradle.api.Plugin

final class UbuntuPackagerPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.convention.plugins.ubuntu = new UbuntuConvention(project)
        project.task('deb') << {packager.execute(project.convention.plugins.ubuntu.toCommands())}
        project.task('clean') << {project.convention.plugins.ubuntu.workDir.deleteDir()}
    }

    private final Packager packager

    UbuntuPackagerPlugin() {
        this(new UbuntuPackager())
    }

    UbuntuPackagerPlugin(Packager packager) {
        this.packager = packager
    }
}
