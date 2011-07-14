package packager.commands.makedh

import static org.apache.commons.lang.StringUtils.*
import org.joda.time.*

@Immutable
final class Context {

    String name
    String version
    String releaseNotes
    String author
    String email
    String homepage
    String time
    String description
    SortedSet<String> dirs
    SortedSet<String> depends

    def asType(Class type) {
        "to${capitalise(type.simpleName)}"()
    }

    Map toMap() {
        [name:name, version:version, releaseNotes:releaseNotes, author:author, email:email, time:time, dirs:dirs, homepage:homepage, description:description, depends:depends]
    }
}
