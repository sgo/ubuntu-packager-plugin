package packager

import packager.commands.Command


interface Packager {

    void execute(List<Command> command)
}