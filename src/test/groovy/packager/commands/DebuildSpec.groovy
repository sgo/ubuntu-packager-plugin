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
