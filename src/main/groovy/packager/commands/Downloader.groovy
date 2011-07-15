package packager.commands

final class Downloader {

    private final URI from
    private final File to

    Downloader(URI from, File to) {
        this.from = from
        this.to = to
    }

    void execute() {
        to.mkdirs()
        def f = new File(to, new File(from.toURL().file).name)
        if(f.exists()) f.delete()
        f << from.toURL().openStream()
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
