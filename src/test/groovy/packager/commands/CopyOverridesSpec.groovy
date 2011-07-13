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
