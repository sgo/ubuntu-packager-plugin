package packager

import packager.commands.Command


interface PackagerConvention {

    List<Command> toCommands()
}
