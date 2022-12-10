package pl.drzewicacraft.drzewicacraftprotection.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import pl.drzewicacraft.drzewicacraftprotection.DrzewicacraftProtection;
import pl.drzewicacraft.drzewicacraftprotection.PluginInfo;

public class DeselectCommand extends AbstractCommand
{

	public DeselectCommand(final DrzewicacraftProtection plugin)
	{
		super(plugin);
	}

	@Override
	public CommandResult execute(final CommandSource source, final CommandContext args) throws CommandException
	{
		if(!(source instanceof Player))
			throw new CommandException(Text.of("Only in-game players can use this command!"));

		final Player player = (Player)source;
		DrzewicacraftProtection.PLAYER_WAND_SELECTION_POINTS.remove(player.getUniqueId());
		player.sendMessage(PluginInfo.MESSAGE_PREFIX.concat(Text.of("Cleared selection points!")));

		return CommandResult.success();
	}
}
