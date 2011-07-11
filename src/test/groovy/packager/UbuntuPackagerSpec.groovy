package packager

import spock.lang.Specification
import packager.commands.Command


class UbuntuPackagerSpec extends Specification {

    def packager = new UbuntuPackager()

    def build = new File('build/debian')

    def setup() {
        build.deleteDir()
    }

    def "the packager executes all given commands"() {
        given:
        def commands = [command(), command(), command()]

        when:
        packager.execute(commands)

        then:
        assert !commands*.called.contains(false)
    }

    TestCommand command() {
        new TestCommand()
    }

    private static final class TestCommand implements Command {

        def called = false

        void execute() {
            called = true
        }

    }
}
