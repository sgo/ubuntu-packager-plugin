package packager

import spock.lang.Specification


class TodoSpec extends Specification {

    def "if no description specified this should default to an empty string instead of null"() {
        expect:
        false
    }

    def "version is a required property"() {
        expect:
        false
    }
}
