package pl.drzewicacraft.drzewicacraftprotection.command;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import pl.drzewicacraft.drzewicacraftprotection.DrzewicacraftProtection;
import pl.drzewicacraft.drzewicacraftprotection.PluginInfo;
import pl.drzewicacraft.drzewicacraftprotection.entity.Region;

import java.util.Optional;

/**
 * Created by Aquerr on 2018-07-17.
 */
public class RegionInfoCommand extends AbstractCommand
{
    public RegionInfoCommand(DrzewicacraftProtection plugin)
    {
        super(plugin);
    }

    @Override
    public CommandResult execute(final CommandSource source, final CommandContext args) throws CommandException
    {
        final Optional<Region> optionalRegion = args.getOne(Text.of("region"));

        if(optionalRegion.isPresent())
        {
            final Region region = optionalRegion.get();
            final Text.Builder textBuilder = Text.builder();

            textBuilder.append(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.GOLD, "----Informacje Regionu----" + "\n")));
            textBuilder.append(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.BLUE, "Nazwa - ", TextColors.GOLD, region.getName() + "\n")));
            textBuilder.append(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.BLUE, "Nazwa świata - ", TextColors.GOLD, Sponge.getServer().getWorld(region.getWorldUUID()).get().getName() + "\n")));
            textBuilder.append(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.BLUE, "Pierwszy punkt - ", TextColors.GOLD, region.getFirstPoint().toString() + "\n")));
            textBuilder.append(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.BLUE, "Drugi punkt - ", TextColors.GOLD, region.getSecondPoint().toString() + "\n")));
            textBuilder.append(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.BLUE, "Opcje: " + "\n")));
            region.getFlags().forEach((x, y) -> textBuilder.append(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.BLUE, "- " + x.name() + ": ",TextColors.GOLD, y.toString() + "\n"))));

            source.sendMessage(textBuilder.build());
            return CommandResult.success();
        }
        else
        {
            source.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "Wrong command arguments!")));
        }

        return CommandResult.success();
    }
}
