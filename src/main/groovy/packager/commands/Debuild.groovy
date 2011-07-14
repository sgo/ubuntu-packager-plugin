package packager.commands

import org.slf4j.LoggerFactory
import org.slf4j.Logger


private static final class Debuild implements Command {

    private static final Logger logger = LoggerFactory.getLogger(Debuild)

    private final File target

    Debuild(File target) {
        this.target = target
    }

    @Override void execute() {
        target.mkdirs()

        def command = "debuild"
        def process = command.execute([], target)
        assert process.waitFor() == 0: process.err.text
        logger.debug(process.in.text)
    }

    boolean equals(o) {
        if(this.is(o)) return true;
        if(getClass() != o.class) return false;

        Debuild debuild = (Debuild) o;

        if(target != debuild.target) return false;

        return true;
    }

    int hashCode() {
        return (target != null ? target.hashCode() : 0);
    }

    public String toString() {
        return "Debuild{" +
                "target=" + target +
                '}';
    }
}