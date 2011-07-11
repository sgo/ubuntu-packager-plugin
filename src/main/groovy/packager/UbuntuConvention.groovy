package packager

import packager.commands.Command
import org.gradle.api.Project
import packager.commands.Downloader
import static java.util.Collections.unmodifiableList

final class UbuntuConvention implements PackagerConvention {

    private final Project project

    private String archive

    UbuntuConvention(Project project) {
        this.project = project
    }

    List<Command> toCommands() {
        def commands = []
        commands << toDownloader()
        unmodifiableList(commands)
    }

    private Downloader toDownloader() {
        def uri = new URI(archive)
        new Downloader(uri, new File(new File(project.buildDir, 'ubuntu'), new File(uri).getName()))
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
