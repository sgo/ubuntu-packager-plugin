package packager.commands

import static groovy.lang.GroovySystem.*
import spock.lang.*

class DebuildSpec extends Specification {

    def work = new File('build/debuild')

    def setup() {
        work.deleteDir()

        String.metaClass.execute = {List args, File dir->
            new File(work, 'test.deb').text = 'test'
            [waitFor:{-> 0}, in:[text:'stdout'], err:[text:'stderr']]
        }
    }

    def cleanup() {
        metaClassRegistry.removeMetaClass String
    }

    def "debuild"() {
        when:
        new Debuild(work).execute()

        then:
        new File(work, 'test.deb').exists()
    }
}
