package pl.drzewicacraft.drzewicacraftprotection;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import pl.drzewicacraft.drzewicacraftprotection.command.*;
import pl.drzewicacraft.drzewicacraftprotection.command.args.RegionCommandElement;
import pl.drzewicacraft.drzewicacraftprotection.entity.WandSelectionPoints;
import pl.drzewicacraft.drzewicacraftprotection.listener.BlockBreakListener;
import pl.drzewicacraft.drzewicacraftprotection.listener.ClickListener;

import java.nio.file.Path;
import java.util.*;

@Plugin(id = PluginInfo.ID, name = PluginInfo.NAME, description = PluginInfo.DESCRIPTION, version = PluginInfo.VERSION,
        url = PluginInfo.URL, authors = {"Aquerr"})
public class DrzewicacraftProtection
{
    public static final Map<UUID, WandSelectionPoints> PLAYER_WAND_SELECTION_POINTS = new HashMap<>();

    public static final Map<List<String>, CommandSpec> SUBCOMMANDS = new HashMap<>();

    private final EventManager eventManager;
    private final CommandManager commandManager;
    private final Path configDir;

    @Inject
    private Logger logger;

    @Inject
    private DrzewicacraftProtection(final EventManager eventManager, final CommandManager commandManager, final @ConfigDir(sharedRoot = false) Path configDir)
    {
        this.eventManager = eventManager;
        this.commandManager = commandManager;
        this.configDir = configDir;
    }

    @Listener
    public void onServerStart(final GameInitializationEvent event)
    {
        //Init services

        registerCommands();
        registerListeners();
    }

    private void registerListeners()
    {
        this.eventManager.registerListeners(this, new ClickListener(this));
        this.eventManager.registerListeners(this, new BlockBreakListener(this));
    }

    private void registerCommands()
    {
        //Help command should display all possible commands in plugin.
        SUBCOMMANDS.put(Collections.singletonList("help"), CommandSpec.builder()
                .description(Text.of("Pokazuje liste dostępnych komend"))
                .permission(PluginPermissions.HELP_COMMAND)
                .executor(new HelpCommand(this))
                .build());

        //List Commmand
        SUBCOMMANDS.put(Collections.singletonList("list"), CommandSpec.builder()
                .description(Text.of("Pokazuje liste regionów"))
                .permission(PluginPermissions.LIST_COMMAND)
                .executor(new ListCommand(this))
                .build());

        //Wand Command
        SUBCOMMANDS.put(Collections.singletonList("wand"), CommandSpec.builder()
                .description(Text.of("Daje różdżkę do zaznaczania terenu"))
                .permission(PluginPermissions.WAND_COMMAND)
                .executor(new WandCommand(this))
                .build());

        //Deselect Command
        SUBCOMMANDS.put(Arrays.asList("deselect", "desel"), CommandSpec.builder()
                .description(Text.of("Usuwa oznaczone punkty przy pomocy różdżki"))
                .permission(PluginPermissions.DESELECT_COMMAND)
                .executor(new DeselectCommand(this))
                .build());

        //Create Region Command
        SUBCOMMANDS.put(Collections.singletonList("createregion"), CommandSpec.builder()
                .description(Text.of("Tworzy region"))
                .permission(PluginPermissions.CREATE_REGION_COMMAND)
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
                .executor(new CreateRegionCommand(this))
                .build());

        //Delete Region Command
        SUBCOMMANDS.put(Collections.singletonList("deleteregion"), CommandSpec.builder()
                .description(Text.of("Usuwa region"))
                .permission(PluginPermissions.DELETE_REGION_COMMAND)
                .arguments(GenericArguments.onlyOne(new RegionCommandElement(Text.of("region"))))
                .executor(new DeleteRegionCommand(this))
                .build());

        //Region Command
        SUBCOMMANDS.put(Collections.singletonList("region"), CommandSpec.builder()
                .description(Text.of("Pokazuje informacje o danym regionie"))
                .permission(PluginPermissions.REGION_INFO_COMMAND)
                .arguments(GenericArguments.onlyOne(new RegionCommandElement(Text.of("region"))))
                .executor(new RegionInfoCommand(this))
                .build());

        final CommandSpec drzewicaCraftProtectionCommands = CommandSpec.builder()
                .children(SUBCOMMANDS)
                .build();

        this.commandManager.register(this, drzewicaCraftProtectionCommands, "drzewica", "drzewicacraft", "dc", "dcp", "drzewicacraftprotection");
    }
}
