package pl.drzewicacraft.drzewicacraftprotection.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import pl.drzewicacraft.drzewicacraftprotection.PluginInfo;
import pl.drzewicacraft.drzewicacraftprotection.entity.Flag;
import pl.drzewicacraft.drzewicacraftprotection.entity.FlagType;
import pl.drzewicacraft.drzewicacraftprotection.entity.Region;
import pl.drzewicacraft.drzewicacraftprotection.service.RegionService;

import java.util.Optional;

/**
 * Created by Aquerr on 2018-07-17.
 */
public class SetFlagCommand implements CommandExecutor
{
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException
    {
        Optional<Region> optionalRegion = args.getOne(Text.of("region"));
        Optional<FlagType> optionalFlag = args.getOne(Text.of("flag"));
        Optional<String> optionalRemainingJoinedStrings = args.getOne(Text.of("value"));

        if(optionalRemainingJoinedStrings.isPresent())
        {
            if(optionalFlag.isPresent())
            {
                if(optionalRegion.isPresent())
                {
                    Flag flag = new Flag(optionalFlag.get(), optionalRemainingJoinedStrings.get());

                    boolean didSucceed = RegionService.setFlag(optionalRegion.get(), flag);
                    if(didSucceed)
                        source.sendMessage(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.GREEN, "Successfully added a flag to the region!")));
                    else
                        source.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "Wrong flag name!")));

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
        }
        else
        {
            source.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "Wrong command arguments!")));
        }

        return CommandResult.success();
    }
}
