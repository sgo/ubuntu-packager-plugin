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
