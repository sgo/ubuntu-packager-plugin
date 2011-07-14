package packager.commands.makedh

import static org.apache.commons.lang.StringUtils.*
import org.joda.time.*
import static java.util.Collections.unmodifiableSortedSet

final class Context {

    String getName() {name}

    String getVersion() {version}

    String getReleaseNotes() {releaseNotes}

    String getAuthor() {author}

    String getEmail() {email}

    String getHomepage() {homepage}

    String getTime() {time}

    String getDescription() {description}

    SortedSet<String> getDirs() {unmodifiableSortedSet(dirs)}

    SortedSet<String> getDepends() {unmodifiableSortedSet(depends)}

    def asType(Class type) {
        "to${capitalise(type.simpleName)}"()
    }

    Map toMap() {
        [name: name, version: version, releaseNotes: releaseNotes, author: author, email: email, time: time, dirs: dirs, homepage: homepage, description: description, depends: depends]
    }

    boolean equals(o) {
        if(this.is(o)) return true;
        if(getClass() != o.class) return false;

        Context context = (Context) o;

        if(author != context.author) return false;
        if(depends != context.depends) return false;
        if(description != context.description) return false;
        if(dirs != context.dirs) return false;
        if(email != context.email) return false;
        if(homepage != context.homepage) return false;
        if(name != context.name) return false;
        if(releaseNotes != context.releaseNotes) return false;
        if(time != context.time) return false;
        if(version != context.version) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (name != null ? name.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (releaseNotes != null ? releaseNotes.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (homepage != null ? homepage.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dirs != null ? dirs.hashCode() : 0);
        result = 31 * result + (depends != null ? depends.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "Context{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", releaseNotes='" + releaseNotes + '\'' +
                ", author='" + author + '\'' +
                ", email='" + email + '\'' +
                ", homepage='" + homepage + '\'' +
                ", time='" + time + '\'' +
                ", description='" + description + '\'' +
                ", dirs=" + dirs +
                ", depends=" + depends +
                '}';
    }
    private String name
    private String version
    private String releaseNotes
    private String author
    private String email
    private String homepage
    private String time
    private String description
    private SortedSet<String> dirs
    private SortedSet<String> depends

    Context(String name, String version, String releaseNotes, String author, String email, String homepage, String time, String description, SortedSet<String> dirs, SortedSet<String> depends) {
        this.name = name
        this.version = version
        this.releaseNotes = releaseNotes
        this.author = author
        this.email = email
        this.homepage = homepage
        this.time = time
        this.description = description
        this.dirs = dirs
        this.depends = depends
    }
}
