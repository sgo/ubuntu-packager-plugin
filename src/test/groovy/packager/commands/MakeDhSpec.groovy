package packager.commands

import spock.lang.Specification

import static packager.commands.MakeDh.context
import org.joda.time.DateTime

import static org.joda.time.DateTimeZone.forOffsetHours
import packager.commands.makedh.Context
import packager.commands.MakeDh.RequiredConfigurationException

class MakeDhSpec extends Specification {

    def test = new File('build/makedh')
    def work = new File(test, 'work')
    def sources = new File(test, 'sources')

    def setup() {
        test.deleteDir()
        sources.mkdirs()
        work.mkdirs()
    }

    def "the execute method should execute all configured closures"() {
        given:
        def command = makedh()
        command.tasks = tasks.collect {task-> return {task.execute()}}

        when:
        command.execute()

        then:
        allTasksExecuted(tasks)

        where:
        tasks << [
                [new Task()],
                [new Task(), new Task()]
        ]
    }

    def "tasks to be executed should be stored in the tasks property"() {
        given:
        def command = makedh()

        expect:
        command.tasks == [
                command.copyRequiredUserFiles,
                command.copyOptionalUserFiles,
                command.generateSourceFormat,
                command.generateChangelog,
                command.generateControl,
                command.generateDirs,
                command.generateRules
        ]
    }

    def "required files should be provided by the user"() {
        given:
        userProvidedFiles(files)
        def expected = [source('copyright'), source('MakeDhSpec.install')]

        expect:
        try {
            makedh().copyRequiredUserFiles()
            target('copyright').text == source('copyright').text
            target('MakeDhSpec.install').text == source('MakeDhSpec.install').text
        } catch(RequiredConfigurationException e) {
            assert e.missingFiles == expected - files.collect {source(it)}
        }

        where:
        files << [
                [],
                ['copyright'],
                ['MakeDhSpec.install'],
                ['copyright', 'MakeDhSpec.install'],
                ['MakeDhSpec.install', 'copyright']
        ]
    }

    def "optional configuration files should be copied if present"() {
        given:
        source(optional).text = optional

        when:
        makedh().copyOptionalUserFiles()

        then:
        target(optional).text == source(optional).text

        where:
        optional << [
                'MakeDhSpec.default',
                'MakeDhSpec.init',
                'MakeDhSpec.postinst',
                'MakeDhSpec.postrm'
        ]
    }

    def "source/format should be generated"() {
        given:
        def command = makedh()

        when:
        command.generateSourceFormat()

        then:
        sourceFormatGenerated()
    }

    def "dirs should be generated"() {
        given:
        def command = makedh(dirs:dirs)

        when:
        command.generateDirs()

        then:
        assert target('dirs').text == dirs.join('\n')

        where:
        dirs << [
                ['/var/lib/spec'] as SortedSet,
                ['/usr/bin/test', '/home/test'] as SortedSet
        ]
    }

    def "rules should be generated"() {
        given:
        def command = makedh()

        when:
        command.generateRules()

        then:
        rulesGenerated()
    }

    def "changelog should be generated"() {
        given:
        def command = makedh(name:name, version:version, releaseNotes:releaseNotes, author:author, email:email, time:time)

        when:
        command.generateChangelog()

        then:
        target('changelog').text == """$name ($version) unstable; urgency=low

  * $releaseNotes

 -- $author <$email>  ${time.toString('E, dd MMM yyyy HH:mm:ss Z')}"""

        where:
        name            | version | releaseNotes       | author      | email               | time
        'MakeDhSpec'    | '0.1'   | 'Initial Release.' | 'thinkerIT' | 'info@thinkerit.be' | new DateTime(2011, 7, 9, 9, 31, 47, 0, forOffsetHours(2))
        'DifferentName' | '1.2'   | 'Newer Release'    | 'spec'      | 'test@example.com'  | new DateTime(2012, 5, 19, 10, 1, 5, 0, forOffsetHours(-1))
    }

    def "control should be generated"() {
        given:
        def command = makedh(name:name, author:author, email:email, homepage:homepage, description:description, depends:depends)

        when:
        command.generateControl()

        then:
        target('control').text == """Source: $name
Section: unknown
Priority: extra
Maintainer: $author <$email>
Build-Depends: debhelper (>= 7)
Standards-Version: 3.8.3
Homepage: $homepage

Package: $name
Architecture: any
Depends: \${shlibs:Depends}, \${misc:Depends}${depends ? ", ${depends.join(', ')}" : ''}
Description: $name
 $description
"""

        where:
        name            | author      | email               | homepage                  | description   | depends
        'MakeDhSpec'    | 'thinkerIT' | 'info@thinkerit.be' | 'http://www.thinkerit.be' | 'description' | [] as SortedSet
        'MakeDhSpec'    | 'thinkerIT' | 'info@thinkerit.be' | 'http://www.thinkerit.be' | 'description' | ['mysql'] as SortedSet
        'DifferentName' | 'spec'      | 'test@example.org'  | 'http://www.example.org'  | 'some other'  | ['mysql', 'apache'] as SortedSet
    }

    private MakeDh makedh(Map args=[:]) {
        def map = [
                name: 'MakeDhSpec',
                version: '0.1',
                releaseNotes: 'Initial Release.',
                author: 'thinkerIT',
                email: 'info@thinkerit.be',
                homepage:'http://thinkerit.be',
                description:'description',
                time: new DateTime(2011, 7, 9, 9, 31, 47, 0, forOffsetHours(2)),
                dirs:[] as SortedSet,
                depends:[] as SortedSet
        ]
        map.putAll(args)
        makedh(context(map))
    }

    private MakeDh makedh(Context context) {
        new MakeDh(sources, work, context)
    }

    private boolean sourceFormatGenerated() {
        return target('source/format').text == '3.0 (native)'
    }

    private File target(String path) {
        return new File(work, path)
    }

    private void rulesGenerated() {
        assert target('rules').text == '''#!/usr/bin/make -f
# -*- makefile -*-
# Sample debian/rules that uses debhelper.
# This file was originally written by Joey Hess and Craig Small.
# As a special exception, when this file is copied by dh-make into a
# dh-make output file, you may use that output file without restriction.
# This special exception was added by Craig Small in version 0.37 of dh-make.

# Uncomment this to turn on verbose mode.
#export DH_VERBOSE=1

%:
	dh  $@'''
    }

    def source(String path) {
        new File(sources, path)
    }

    def resource(String path) {
        getClass().getResource("/debian/$path")
    }

    private static final class Task {
        def called = false

        def execute() {
            called = true
        }
    }

    private void allTasksExecuted(tasks) {
        assert !tasks*.called.contains(false)
    }

    private def userProvidedFiles(var) {
        var.each {source(it).text = it}
    }
}
