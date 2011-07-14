package packager

import spock.lang.Specification
import packager.commands.Downloader
import packager.fakes.TestProject
import packager.UbuntuPackagerPluginSpec.TestPackager
import org.gradle.testfixtures.ProjectBuilder
import packager.commands.Extractor
import packager.commands.CopyOverrides
import org.gradle.api.plugins.JavaPlugin
import packager.commands.MakeDh
import static packager.commands.MakeDh.context
import org.joda.time.DateTime
import packager.commands.Command
import org.joda.time.DateTimeUtils
import packager.commands.Debuild

class UbuntuConventionSpec extends Specification {

    def project = ProjectBuilder.builder().build()
    UbuntuConvention convention

    def methodMissing(String name, args) {
        project."$name"(* args)
    }

    def setup() {
        project.buildDir = build
        apply plugin: 'ubuntu'
        convention = project.convention.plugins.ubuntu
    }

    def "toCommands()"() {
        given:
        convention.toCommandTasks = tasks.collect([]) {task-> return {task.execute()}}

        expect:
        convention.toCommands()*.name == commands

        where:
        tasks                                            | commands
        []                                               | []
        [new Task(name:'task1')]                         | ['task1']
        [new Task(name:'task1'), new Task(name:'task2')] | ['task1', 'task2']
    }

    def "toCommandTasks property"() {
        expect:
        convention.toCommandTasks == [
                convention.toDownloader,
                convention.toExtractor,
                convention.toCopyOverrides,
                convention.toMakeDh,
                convention.toDebuild
        ]
    }

    def "toMakeDh()"() {
        given:
        DateTimeUtils.currentMillisFixed = dateTime.millis

        project.version = version
        project.description = description

        ubuntu {
            archive = 'uri'
            releaseNotes = notes
            author = authorName
            email = emailAddress
            homepage = homepageAddress
            if(dependsOn) {
                depends {
                    dependsOn.each {
                        on it
                    }
                }
            }
            if(dirsToCreate) {
                dirs {
                    dirsToCreate.each {
                        dir it
                    }
                }
            }
        }

        expect:
        convention.toMakeDh() == new MakeDh(new File(project.projectDir, 'src/ubuntu/debian'), new File(work, 'debian'), context(
                name: project.name,
                version: version,
                releaseNotes: notes,
                author: authorName,
                email: emailAddress,
                homepage: homepageAddress,
                description: description,
                depends: dependsOn as SortedSet,
                dirs: dirsToCreate as SortedSet,
                time: dateTime
        ))

        where:
        version | notes    | authorName | emailAddress | homepageAddress | description | dependsOn  | dirsToCreate | dateTime
        '0.1'   | 'notes1' | 'author1'  | 'email1'     | 'web1'          | 'desc1'     | []         | []           | new DateTime()
        '0.2'   | 'notes2' | 'author2'  | 'email2'     | 'web2'          | 'desc2'     | ['a', 'b'] | ['c', 'd']   | new DateTime().minusDays(1)

    }

    def "toCopyOverrides()"() {
        expect:
        convention.toCopyOverrides() == new CopyOverrides(new File(project.projectDir, 'src/ubuntu/overrides'), work)
    }

    def "toExtractor()"() {
        given:
        ubuntu {
            archive = archiveUri.toString()
        }

        expect:
        convention.toExtractor() == new Extractor(targetArchive(archiveUri), work)

        where:
        archiveUri << [
                new URI('file:/tmp/archive1.tar.gz'),
                new URI('file:/tmp/archive2.tar.gz')
        ]
    }

    def "toDownloader()"() {
        given:
        ubuntu {
            archive = archiveUri.toString()
        }

        expect:
        convention.toDownloader() == new Downloader(archiveUri, targetArchive(archiveUri))

        where:
        archiveUri << [
                new URI('file:/tmp/archive1.tar.gz'),
                new URI('file:/tmp/archive2.tar.gz')
        ]
    }

    def "toDebuild()"() {
        expect:
        convention.toDebuild() == new Debuild(work)
    }

    private File targetArchive(uri) {
        def fileName = new File(uri).getName()
        def work = new File(build, "ubuntu")
        def targetArchive = new File(work, fileName)
        return targetArchive
    }

    private File getBuild() {
        return new File('/build')
    }

    private File getWork() {
        return new File(build, "ubuntu")
    }

    private static final class Task {
        def name
        def execute() {[name:name]}
    }
}
