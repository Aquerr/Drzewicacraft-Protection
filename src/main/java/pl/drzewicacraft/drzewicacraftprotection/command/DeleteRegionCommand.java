package pl.drzewicacraft.drzewicacraftprotection.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import pl.drzewicacraft.drzewicacraftprotection.DrzewicacraftProtection;
import pl.drzewicacraft.drzewicacraftprotection.PluginInfo;
import pl.drzewicacraft.drzewicacraftprotection.entity.Region;
import pl.drzewicacraft.drzewicacraftprotection.service.RegionService;

import java.util.Optional;

public class DeleteRegionCommand extends AbstractCommand
{
    public DeleteRegionCommand(final DrzewicacraftProtection plugin)
    {
        super(plugin);
    }

    @Override
    public CommandResult execute(final CommandSource source, final CommandContext args) throws CommandException
    {
        final Region region = args.requireOne(Text.of("region"));
        boolean didSucceed = RegionService.removeRegion(region);
        if(didSucceed)
        {
            source.sendMessage(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.GREEN, "Successfully removed a region named " + region.getName() + "!")));
        }
        else
        {
            source.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "Something went wrong...")));
        }

        return CommandResult.success();
    }
}
