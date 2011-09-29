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

import spock.lang.*

class DownloaderSpec extends Specification {

    def build = new File("build/downloader")

    def setup() {
        build.deleteDir()
    }

    def "the downloader should download the given uri"() {
        given:
        def source = new File(uri)
        def target = new File(build, source.name)
        def downloader = new Downloader(uri, target)

        when:
        downloader.execute()

        then:
        target.exists()
        target.bytes == source.bytes

        where:
        uri << [resource('/jetty-1.tar.gz'), resource('/jetty-2.tar.gz')]
    }

    def "if the distribution already exists it is overwritten"() {
        given:
        def uri = resource('/jetty-1.tar.gz')
        def source = new File(uri)
        def target = new File(build, source.name)
        def downloader = new Downloader(uri, target)

        when:
        downloader.execute()
        downloader.execute()

        then:
        target.bytes == source.bytes
    }

    def "files are downloaded to the target dir"() {
        given:
        buildDir.deleteDir()
        def uri = resource('/jetty-1.tar.gz')
        def source = new File(uri)
        def target = new File(buildDir, source.name)
        def downloader = new Downloader(uri, target)

        when:
        downloader.execute()

        then:
        target.bytes == source.bytes

        where:
        buildDir << [new File('build/download1'), new File('build/download2')]
    }

    URI resource(String path) {
        return getClass().getResource(path).toURI()
    }
}
