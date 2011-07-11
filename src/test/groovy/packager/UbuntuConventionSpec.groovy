package packager

import spock.lang.Specification
import packager.commands.Downloader
import packager.fakes.TestProject
import packager.UbuntuPackagerPluginSpec.TestPackager
import org.gradle.testfixtures.ProjectBuilder


class UbuntuConventionSpec extends Specification {

    def project = ProjectBuilder.builder().build()

    def methodMissing(String name, args) {
        project."$name"(*args)
    }

    def "toCommands()"() {
        given:
        def build = new File('/build')
        project.buildDir = build
        apply plugin:'ubuntu'
        UbuntuConvention convention = project.convention.plugins.ubuntu

        def fileName = new File(archiveUri).getName()
        def to = new File(build, "ubuntu/$fileName")

        when:
        ubuntu {
            archive = archiveUri.toString()
        }

        then:
        def downloader = new Downloader(archiveUri, to)
        convention.toCommands() == [downloader]

        where:
        archiveUri    | to2
        new URI('file:/tmp/archive1.tar.gz')  | 'to'
        new URI('file:/tmp/archive2.tar.gz') | 'to2'
    }
}
