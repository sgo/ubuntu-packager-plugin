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

class CopyOverridesSpec extends Specification {

    def source = resource('/overrides')
    def target = new File('build/overrides')

    def setup() {
        target.deleteDir()
        "ln -s /something/nonexisting $source.absolutePath/symlink".execute().waitFor()
    }

    def cleanup() {
        "rm $source.absolutePath/symlink".execute().waitFor()
    }

    def "contents of a given source dir should be copied over to a given target dir"() {
        given:
        def overrides = new CopyOverrides(source, target)

        when:
        overrides.execute()

        then:
        copiedOverrides()
        preservedSymLinks()
        copiedDirsRecursively()
    }

    def "do nothing if source dir does not exist"() {
        when:
        new CopyOverrides(new File('/not/exists'), target).execute()

        then:
        assert !target.list()
    }

    private void copiedDirsRecursively() {
        assert new File(target, 'dir').list() as Set == ['indir.file'] as Set
    }

    private void preservedSymLinks() {
        assert isSymlink(target.listFiles().find {it.name == 'symlink'})
    }

    private void copiedOverrides() {
        assert target.list() as Set == ['simple.file', 'symlink', 'dir'] as Set
    }


    static boolean isSymlink(File file) {
        def p = "ls -l $file.absolutePath".execute()
        p.waitFor()
        def output = p.in.text
        output.startsWith(/l/)
    }

    File resource(String path) {
        new File("src/test/resources$path")
    }
}
