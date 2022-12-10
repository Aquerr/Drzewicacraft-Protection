package pl.drzewicacraft.drzewicacraftprotection.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import pl.drzewicacraft.drzewicacraftprotection.DrzewicacraftProtection;

public abstract class AbstractCommand implements CommandExecutor
{
	private final DrzewicacraftProtection plugin;

	public AbstractCommand(final DrzewicacraftProtection plugin)
	{
		this.plugin = plugin;
	}

	public DrzewicacraftProtection getPlugin()
	{
		return this.plugin;
	}

	@Override
	public abstract CommandResult execute(CommandSource src, CommandContext args) throws CommandException;
}
