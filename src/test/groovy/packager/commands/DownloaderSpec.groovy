package packager.commands

import spock.lang.*

class DownloaderSpec extends Specification {

    def build = new File("build/downloader")

    def setup() {
        build.deleteDir()
    }

    def "the downloader should download the given uri"() {
        given:
        def downloader = new Downloader(uri, build)

        when:
        downloader.execute()

        then:
        new File(build, new File(uri).name).exists()
        new File(build, new File(uri).name).bytes == new File(uri).bytes

        where:
        uri << [resource('/jetty-1.tar.gz'), resource('/jetty-2.tar.gz')]
    }

    def "if the distribution already exists it is overwritten"() {
        given:
        def uri = resource('/jetty-1.tar.gz')
        def downloader = new Downloader(uri, build)

        when:
        downloader.execute()
        downloader.execute()

        then:
        new File(build, new File(uri).name).bytes == new File(resource('/jetty-1.tar.gz')).bytes
    }

    def "files are downloaded to the target dir"() {
        given:
        buildDir.deleteDir()
        def uri = resource('/jetty-1.tar.gz')
        def downloader = new Downloader(uri, buildDir)

        when:
        downloader.execute()

        then:
        new File(buildDir, new File(uri).name).bytes == new File(resource('/jetty-1.tar.gz')).bytes

        where:
        buildDir << [new File('build/download1'), new File('build/download2')]
    }

    URI resource(String path) {
        return getClass().getResource(path).toURI()
    }
}
