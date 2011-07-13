package packager.commands

import org.slf4j.LoggerFactory
import org.slf4j.Logger


final class CopyOverrides implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CopyOverrides)

    private final File source
    private final File target

    CopyOverrides(File source, File target) {
        this.source = source
        this.target = target
    }

    @Override void execute() {
        assert source.directory
        if(!target.exists()) target.mkdirs()
        assert target.directory

        source.listFiles().each {File f ->
            def command = "cp -Rf $f.absolutePath $target.absolutePath".toString()
            logger.debug(command)
            def process = command.execute()
            def result = process.waitFor()
            assert result == 0: "Failed to copy overrides! [${process.err.text.replaceAll(/[\n\r]*/, '')}]"
        }
    }

    boolean equals(o) {
        if(this.is(o)) return true;
        if(getClass() != o.class) return false;

        CopyOverrides that = (CopyOverrides) o;

        if(source != that.source) return false;
        if(target != that.target) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (source != null ? source.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "CopyOverrides{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }
}
