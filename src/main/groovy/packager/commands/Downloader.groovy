package packager.commands

final class Downloader {

    private final URI from
    private final File to

    Downloader(URI from, File to) {
        this.from = from
        this.to = to
    }

    void execute() {
        def dir = to.parentFile
        dir.mkdirs()

        def file = new File(dir, to.name)
        if(file.exists()) file.delete()
//        file.withWriter {it << from.toURL().openStream().bytes}
        file << from.toURL().openStream()
    }

    boolean equals(o) {
        if(this.is(o)) return true;
        if(getClass() != o.class) return false;

        Downloader that = (Downloader) o;

        if(from != that.from) return false;
        if(to != that.to) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }


    public String toString() {
        return "Downloader{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
