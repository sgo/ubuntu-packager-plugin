package packager

import packager.commands.Command
import org.gradle.api.Project
import packager.commands.Downloader
import static java.util.Collections.unmodifiableList
import packager.commands.Extractor
import packager.commands.CopyOverrides

final class UbuntuConvention implements PackagerConvention {

    private final Project project

    private String archive

    UbuntuConvention(Project project) {
        this.project = project
    }

    List<Command> toCommands() {
        def commands = []
        commands << toDownloader()
        commands << toExtractor()
        commands << toCopyOverrides()
        unmodifiableList(commands)
    }

    private CopyOverrides toCopyOverrides() {
        new CopyOverrides(new File(project.getProjectDir(), 'src/ubuntu/overrides'), workDir)
    }

    private Extractor toExtractor() {
        new Extractor(archivedFile, workDir)
    }

    private Downloader toDownloader() {
        new Downloader(archiveUri, archivedFile)
    }

    private File getArchivedFile() {
        return new File(workDir, new File(archiveUri).getName())
    }

    private File getWorkDir() {
        return new File(project.buildDir, 'ubuntu')
    }

    private URI getArchiveUri() {
        return new URI(archive)
    }

    def ubuntu(Closure closure) {
        with closure
        assertConfigurationComplete()
    }

    def assertConfigurationComplete() {
        assert archive : 'An archive uri should be specified in a ubuntu configuration block!'
        assert project.buildDir : 'The project buildDir should be specified!'
    }
}
