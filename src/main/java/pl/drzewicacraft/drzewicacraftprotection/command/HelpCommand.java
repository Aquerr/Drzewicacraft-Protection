package pl.drzewicacraft.drzewicacraftprotection.command;

import com.google.common.collect.Lists;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import pl.drzewicacraft.drzewicacraftprotection.DrzewicacraftProtection;

import java.util.List;
import java.util.Map;

public class HelpCommand extends AbstractCommand
{
    public HelpCommand(final DrzewicacraftProtection plugin)
    {
        super(plugin);
    }

    @Override
    public CommandResult execute(final CommandSource source, final CommandContext args) throws CommandException
    {
        final Map<List<String>, CommandSpec> commands = DrzewicacraftProtection.SUBCOMMANDS;
        final List<Text> helpList = Lists.newArrayListWithExpectedSize(DrzewicacraftProtection.SUBCOMMANDS.size());

        for (final List<String> aliases: commands.keySet())
        {
            final CommandSpec commandSpec = commands.get(aliases);

            if(source instanceof Player)
            {
                final Player player = (Player)source;

                if(!commandSpec.testPermission(player))
                {
                    continue;
                }
            }

            final Text commandHelp = Text.builder()
                    .append(Text.builder()
                            .append(Text.of(TextColors.AQUA, "/dcp " + aliases.toString().replace("[","").replace("]","")))
                            .build())
                    .append(Text.builder()
                            .append(Text.of(TextColors.WHITE, " - " + commandSpec.getShortDescription(source).get().toPlain() + "\n"))
                            .build())
                    .append(Text.builder()
                            .append(Text.of(TextColors.GRAY, "Usage: /dcp " + aliases.toString().replace("[","").replace("]","") + " " + commandSpec.getUsage(source).toPlain()))
                            .build())
                    .build();

            helpList.add(commandHelp);
        }

        //Sort commands alphabetically.
        helpList.sort(Text::compareTo);

        PaginationService paginationService = Sponge.getServiceManager().provide(PaginationService.class).get();
        PaginationList.Builder paginationBuilder = paginationService.builder().title(Text.of(TextColors.GREEN, "DrzewicaCraft-Protection Lista Komend")).padding(Text.of("-")).contents(helpList).linesPerPage(9);
        paginationBuilder.sendTo(source);

        return CommandResult.success();
    }
}
