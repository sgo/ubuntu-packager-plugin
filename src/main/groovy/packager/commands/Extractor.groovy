package packager.commands

import org.slf4j.LoggerFactory
import org.slf4j.Logger


final class Extractor implements Command {

    private static final Logger logger = LoggerFactory.getLogger(Extractor)

    private final File archive
    private final File to

    Extractor(File archive, File to) {
        this.archive = archive
        this.to = to
    }

    void execute() {
        to.mkdirs()
        def command = "tar -x -z -v -C $to.absolutePath -f $archive.absolutePath"
        def process = command.execute()
        assert process.waitFor() == 0 : process.err.text
        logger.debug("extracting...\n$command\n${process.err.text}")
    }

    public String toString() {
        return "Extractor{" +
                "archive=" + archive +
                ", to=" + to +
                '}';
    }

    boolean equals(o) {
        if(this.is(o)) return true;
        if(getClass() != o.class) return false;

        Extractor extractor = (Extractor) o;

        if(archive != extractor.archive) return false;
        if(to != extractor.to) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (archive != null ? archive.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}