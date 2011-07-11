package packager

import org.gradle.testfixtures.*
import packager.commands.*
import packager.fakes.*
import spock.lang.*

class UbuntuPackagerPluginSpec extends Specification {

    def project = ProjectBuilder.builder().build()
    def plugin = new UbuntuPackagerPlugin(new TestPackager())

    def methodMissing(String name, args) {
        project."$name"(*args)
    }

    def "apply plugin:ubuntu installs the ubuntu packager plugin"() {
        when:
        apply plugin:'ubuntu'

        then:
        assert project.tasks.deb
        assert project.convention.plugins.ubuntu in UbuntuConvention
    }

    def "the applied deb task should delegate to the packager"() {
        given:
        def project = new TestProject()
        plugin.apply(project)
        project.convention.plugins.ubuntu = new TestConvention(commands)

        when:
        project.getTask('deb').tasks*.execute()

        then:
        plugin.packager.called
        plugin.packager.commands == commands

        where:
        commands << [
                [],
                [{} as Command],
                [{} as Command, {} as Command]
        ]
    }

    def "the packager plugin should use the UbuntuPackager by default"() {
        expect:
        new UbuntuPackagerPlugin().packager in UbuntuPackager
    }

    private static final class TestPackager implements Packager {
        def called = false
        def commands

        void execute(List<Command> commands) {
            called = true
            this.commands = commands
        }
    }

    private static final class TestConvention implements PackagerConvention {
        private final List<Command> commands

        TestConvention(List<Command> commands) {
            this.commands = commands
        }

        List<Command> toCommands() {
            commands
        }
    }
}
