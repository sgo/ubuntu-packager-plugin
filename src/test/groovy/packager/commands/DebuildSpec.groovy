package packager.commands

import static groovy.lang.GroovySystem.*
import spock.lang.*

class DebuildSpec extends Specification {

    def work = new File('build/debuild')

    def setup() {
        work.deleteDir()
        work.mkdirs()
    }

    def cleanup() {
        work.deleteDir()
    }

    def "debuild"() {
        given:
        def command = new Debuild(work)
        command.script = getClass().getResourceAsStream('/debuild/debuild').text

        when:
        command.execute()

        then:
        new File(work, 'test.deb').exists()
    }

    def "default script should be read from classpath:/packager/debuild/debuild.sh"() {
        expect:
        new Debuild(work).script == getClass().getResourceAsStream('/packager/debuild/debuild.sh').text
    }
}
