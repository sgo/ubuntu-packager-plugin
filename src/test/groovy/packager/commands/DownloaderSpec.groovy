package packager.commands

import spock.lang.Specification


class DownloaderSpec extends Specification {

    def build = new File("build/download")

    def setup() {
        build.deleteDir()
    }

    def "the downloader should download the given uri"() {
        given:
        def downloader = new Downloader(uri, build)

        when:
        downloader.execute()

        then:
        new File(build, "jetty.tar.gz").exists()
        new File(build, "jetty.tar.gz").bytes == new File(uri).bytes

        where:
        uri << [resource('/jetty-1.tar.gz'), resource('/jetty-2.tar.gz')]
    }

    def "if the distribution already exists it is overwritten"() {
        given:
        def downloader = new Downloader(resource('/jetty-1.tar.gz'), build)

        when:
        downloader.execute()
        downloader.execute()

        then:
        new File(build, "jetty.tar.gz").bytes == new File(resource('/jetty-1.tar.gz')).bytes
    }

    private URI resource(String path) {
        return getClass().getResource(path).toURI()
    }
}
