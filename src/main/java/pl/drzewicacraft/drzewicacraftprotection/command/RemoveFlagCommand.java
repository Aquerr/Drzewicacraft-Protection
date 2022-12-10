package pl.drzewicacraft.drzewicacraftprotection.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import pl.drzewicacraft.drzewicacraftprotection.PluginInfo;
import pl.drzewicacraft.drzewicacraftprotection.entity.FlagType;
import pl.drzewicacraft.drzewicacraftprotection.entity.Region;
import pl.drzewicacraft.drzewicacraftprotection.service.RegionService;

import java.util.Optional;

/**
 * Created by Aquerr on 2018-07-20.
 */
public class RemoveFlagCommand implements CommandExecutor
{
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException
    {
        Optional<Region> optionalRegion = args.getOne(Text.of("region"));
        Optional<FlagType> optionalFlag = args.getOne(Text.of("flag"));

        if (optionalFlag.isPresent())
        {
            if (optionalRegion.isPresent())
            {
                if (!optionalRegion.get().getFlags().containsKey(optionalFlag.get()))
                {
                    source.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "This flag does not exist in the specified region!")));
                    return CommandResult.success();
                }
                else
                {
                    boolean didSucceed = RegionService.removeFlag(optionalRegion.get(), optionalFlag.get());

                    if (didSucceed)
                        source.sendMessage(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.GREEN, "Successfully removed flag from the region!")));
                    else
                        source.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "Wrong flag name!")));
                }
            }
            else
            {
                source.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "Provided region does not exist!")));
            }
        }
        else
        {
            source.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "Wrong command arguments!")));
        }

        return CommandResult.success();
    }
}
