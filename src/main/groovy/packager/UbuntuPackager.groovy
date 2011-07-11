package packager

import packager.commands.Command

final class UbuntuPackager implements Packager {
    void execute(List<Command> command) {
        command*.execute()
    }

}
