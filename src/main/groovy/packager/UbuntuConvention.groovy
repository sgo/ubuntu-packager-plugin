package packager

import packager.commands.Command
import org.gradle.api.Project
import packager.commands.Downloader
import static java.util.Collections.unmodifiableList
import packager.commands.Extractor
import packager.commands.CopyOverrides
import packager.commands.MakeDh
import packager.commands.makedh.Context
import static packager.commands.MakeDh.context
import org.joda.time.DateTime
import packager.commands.Debuild

final class UbuntuConvention implements PackagerConvention {

    List<Command> toCommands() {
        assertConfigurationComplete()
        unmodifiableList(toCommandTasks*.call())
    }

    private Context toContext() {
        context(
                name: project.name,
                version: project.version,
                releaseNotes: releaseNotes,
                author: author,
                email: email,
                homepage: homepage,
                description: project.description ?: '',
                depends: depends,
                dirs: dirs,
                time: new DateTime()
        )
    }

    private def toMakeDh = {
        new MakeDh(new File(project.projectDir, 'src/ubuntu/debian'), new File(workDir, 'debian'), toContext())
    }

    private def toCopyOverrides = {
        new CopyOverrides(new File(project.getProjectDir(), 'src/ubuntu/overrides'), workDir)
    }

    private def toExtractor = {
        new Extractor(archivedFile, workDir)
    }

    private def toDownloader = {
        new Downloader(archiveUri, archivedFile)
    }

    private def toDebuild = {
        new Debuild(workDir)
    }

    private File getArchivedFile() {
        return new File(workDir, new File(archiveUri).getName())
    }

    File getWorkDir() {
        return new File(project.buildDir, 'ubuntu')
    }

    private URI getArchiveUri() {
        return new URI(archive)
    }

    def ubuntu(Closure closure) {
        with closure
    }

    def depends(Closure closure) {
        new Object() {
            def on(dependency) {
                depends << dependency
            }
        }.with closure
    }

    def dirs(Closure c) {
        new Object() {
            def dir(dir) {
                dirs << dir
            }
        }.with c
    }

    def assertConfigurationComplete() {
        assert archive : 'An archive uri should be specified in a ubuntu configuration block'
        assert releaseNotes : 'Release notes information should be specified in a ubuntu configuration block'
        assert author : 'Author should be specified in a ubuntu configuration block'
        assert email : 'E-mail should be specified in a ubuntu configuration block'
        assert homepage : 'A homepage should be specified in a ubuntu configuration block'
        assert project.buildDir : 'The project buildDir should be specified!'
        assert project.version : 'The project version should be specified'
    }

    private final Project project

    private String archive
    private String releaseNotes
    private String author
    private String email
    private String homepage
    private def depends = [] as SortedSet<String>
    private def dirs = [] as SortedSet<String>

    UbuntuConvention(Project project) {
        this.project = project
    }

    private def toCommandTasks = [
            toDownloader,
            toExtractor,
            toCopyOverrides,
            toMakeDh,
            toDebuild
    ]
}
