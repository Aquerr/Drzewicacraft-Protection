package pl.drzewicacraft.drzewicacraftprotection.command;

import com.google.common.collect.Lists;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import pl.drzewicacraft.drzewicacraftprotection.DrzewicacraftProtection;
import pl.drzewicacraft.drzewicacraftprotection.entity.Region;
import pl.drzewicacraft.drzewicacraftprotection.service.RegionService;

import java.util.ArrayList;
import java.util.List;

public class ListCommand extends AbstractCommand
{
    public ListCommand(final DrzewicacraftProtection plugin)
    {
        super(plugin);
    }

    @Override
    public CommandResult execute(final CommandSource source, final CommandContext args) throws CommandException
    {
        final List<Region> regions = new ArrayList<>(RegionService.getRegions());

        final List<Text> helpList = Lists.newArrayList();
        int count = 1;

        for(final Region region : regions)
        {
            final Text.Builder regionDetails = Text.builder();

            regionDetails.append(Text.of(TextColors.YELLOW, "======Lokacja Regionu======" + "\n"));
            regionDetails.append(Text.of(TextColors.BLUE, "Nazwa świata: ", TextColors.GOLD, Sponge.getServer().getWorld(region.getWorldUUID()).get().getName() + "\n"));
            regionDetails.append(Text.of(TextColors.BLUE, "Pierwszy punkt: ", TextColors.GOLD, region.getFirstPoint().toString() + "\n"));
            regionDetails.append(Text.of(TextColors.BLUE, "Drugi punkt: ", TextColors.GOLD, region.getSecondPoint().toString() + "\n"));
            region.getFlags().forEach((x, y) -> regionDetails.append(Text.of(TextColors.BLUE, x.name() + ": ", TextColors.GOLD, y + "\n")));

            final Text regionText = Text.builder()
                    .append(Text.of(count + ". " + region.getName()))
                    .onHover(TextActions.showText(regionDetails.build()))
                    .build();

            count++;

            helpList.add(regionText);
        }

        final PaginationService paginationService = Sponge.getServiceManager().provide(PaginationService.class).get();
        final PaginationList.Builder paginationBuilder = paginationService.builder().title(Text.of(TextColors.WHITE, "Lista regionów")).padding(Text.of(TextColors.GOLD, "-")).contents(helpList).linesPerPage(10);
        paginationBuilder.sendTo(source);

        return CommandResult.success();
    }
}
