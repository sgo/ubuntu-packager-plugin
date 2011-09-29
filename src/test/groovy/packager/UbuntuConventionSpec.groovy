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

import org.gradle.testfixtures.*
import org.joda.time.*
import packager.commands.*
import static packager.commands.MakeDh.*
import spock.lang.*

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
        convention.toCommandTasks = tasks.collect([]) {task -> return {task.execute()}}
        withUbuntuConfig()

        expect:
        convention.toCommands()*.name == commands

        where:
        tasks                                              | commands
        []                                                 | []
        [new Task(name: 'task1')]                          | ['task1']
        [new Task(name: 'task1'), new Task(name: 'task2')] | ['task1', 'task2']
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

    def "required ubuntu block configuration"() {
        given:
        convention.toCommandTasks = []
        withUbuntuConfig(config)

        expect:
        try {
            convention.toCommands()
            assert false
        } catch (AssertionError e) {
            assert e.message == message
        }

        where:
        config         | message
        'archive'      | 'An archive uri should be specified in a ubuntu configuration block. Expression: archive. Values: archive = null'
        'releaseNotes' | 'Release notes information should be specified in a ubuntu configuration block. Expression: releaseNotes. Values: releaseNotes = null'
        'author'       | 'Author should be specified in a ubuntu configuration block. Expression: author. Values: author = null'
        'email'        | 'E-mail should be specified in a ubuntu configuration block. Expression: email. Values: email = null'
        'homepage'     | 'A homepage should be specified in a ubuntu configuration block. Expression: homepage. Values: homepage = null'
    }

    def "required project configuration"() {
        given:
        project.version = ''
        convention.toCommandTasks = []
        withUbuntuConfig()

        expect:
        try {
            convention.toCommands()
            assert false
        } catch (AssertionError e) {
            assert e.message == 'The project version should be specified. Expression: project.version'
        }
    }

    def "null description should default to empty string"() {
        expect:
        convention.toContext().description == ''
    }

    private def withUbuntuConfig(config = '') {
        ubuntu {
            if(config != 'archive') archive = 'uri'
            if(config != 'releaseNotes') releaseNotes = 'notes'
            if(config != 'author') author = 'author'
            if(config != 'email') email = 'email'
            if(config != 'homepage') homepage = 'web'
            depends {
                on 'something'
            }
            dirs {
                dir 'somewhere'
            }
        }
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

        def execute() {[name: name]}
    }
}
