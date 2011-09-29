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
