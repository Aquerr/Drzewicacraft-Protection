package pl.drzewicacraft.drzewicacraftprotection.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import pl.drzewicacraft.drzewicacraftprotection.PluginInfo;
import pl.drzewicacraft.drzewicacraftprotection.entity.Region;
import pl.drzewicacraft.drzewicacraftprotection.service.RegionService;

import java.util.Optional;

public class RenameRegionCommand implements CommandExecutor
{
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException
    {
        Optional<Region> optionalRegion = args.getOne(Text.of("region"));
        Optional<String> optionalName = args.getOne(Text.of("name"));

        if(optionalRegion.isPresent() && optionalName.isPresent())
        {
            Region region = optionalRegion.get();
            String name = optionalName.get();

            boolean didSucceed = RegionService.changeName(region, name);

            if(didSucceed)
            {
                source.sendMessage(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.GREEN, "Successfully updated region's name!")));
            }
            else
            {
                source.sendMessage(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.GREEN, "Something went wrong!")));
            }
        }
        else
        {
            source.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "You need to specify region and its new name!")));
        }

        return CommandResult.success();
    }
}
