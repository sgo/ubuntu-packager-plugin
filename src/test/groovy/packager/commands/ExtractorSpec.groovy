package packager.commands

import spock.lang.Specification
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class ExtractorSpec extends Specification {

    def build = new File("build/extractor")

    def setup() {
        build.deleteDir()
    }

    def "the extractor unpacks a given tar.gz archive"() {
        given:
        def extractor = new Extractor(new File(resource('/jetty-1.tar.gz')), build)

        when:
        extractor.execute()

        then:
        new File(build, 'jetty').exists()
        new File(build, 'jetty/contexts/test.xml').exists()
    }

    def "throw an assertion error if the archive could not be extracted"() {
        given:
        def extractor = new Extractor(new File('/whatever/test.tar.gz'), build)

        when:
        extractor.execute()

        then:
        thrown(AssertionError)
    }

    URI resource(String path) {
        return getClass().getResource(path).toURI()
    }
}
