package pl.drzewicacraft.drzewicacraftprotection.command.args;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import pl.drzewicacraft.drzewicacraftprotection.PluginInfo;
import pl.drzewicacraft.drzewicacraftprotection.entity.Region;
import pl.drzewicacraft.drzewicacraftprotection.service.RegionService;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegionCommandElement extends CommandElement
{
    public RegionCommandElement(final @Nullable Text key)
    {
        super(key);
    }

    @Nullable
    @Override
    protected Region parseValue(final CommandSource source, final CommandArgs args) throws ArgumentParseException
    {
        final Optional<String> optionalArg = args.nextIfPresent();
        if (!optionalArg.isPresent())
            throw new ArgumentParseException(Text.of(PluginInfo.ERROR_PREFIX, TextColors.DARK_RED, "Podany region nie istnieje!"), "", 0);

        final String arg = optionalArg.get();
        final Optional<Region> optionalRegion = RegionService.getRegion(arg);
        if (!optionalRegion.isPresent())
            throw new ArgumentParseException(Text.of(PluginInfo.ERROR_PREFIX, TextColors.DARK_RED, "Podany region nie istnieje!"), "", 0);

        return optionalRegion.get();
    }

    @Override
    public List<String> complete(final CommandSource src, final CommandArgs args, final CommandContext context)
    {
        final List<String> resultList = new ArrayList<>();
        final List<Region> regions = RegionService.getRegions();

        if (args.hasNext())
        {
            final String arg = args.getRaw();
            for (final Region region : regions)
            {
                if (region.getName().contains(arg))
                    resultList.add(region.getName());
            }
        }
        return resultList;
    }
}
