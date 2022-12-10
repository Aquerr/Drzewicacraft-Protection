package pl.drzewicacraft.drzewicacraftprotection.command;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import pl.drzewicacraft.drzewicacraftprotection.DrzewicacraftProtection;
import pl.drzewicacraft.drzewicacraftprotection.PluginInfo;
import pl.drzewicacraft.drzewicacraftprotection.entity.Region;
import pl.drzewicacraft.drzewicacraftprotection.entity.WandSelectionPoints;
import pl.drzewicacraft.drzewicacraftprotection.service.RegionService;

import java.util.HashMap;

public class CreateRegionCommand extends AbstractCommand
{
    public CreateRegionCommand(final DrzewicacraftProtection plugin)
    {
        super(plugin);
    }

    @Override
    public CommandResult execute(final CommandSource source, final CommandContext args) throws CommandException
    {
        String regionName = args.requireOne(Text.of("name"));

        if (!(source instanceof Player))
            throw new CommandException(Text.of(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "Only in-game players can use this command!"))));

        final Player player = (Player) source;
        if (RegionService.getRegion(regionName).isPresent())
        {
            source.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "Provided region name already exist!")));
            return CommandResult.success();
        }

        if (!DrzewicacraftProtection.PLAYER_WAND_SELECTION_POINTS.containsKey(player.getUniqueId()))
            throw new CommandException(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "You didn't specify one or both points!")));

        final WandSelectionPoints wandSelectionPoints = DrzewicacraftProtection.PLAYER_WAND_SELECTION_POINTS.get(player.getUniqueId());
        if (wandSelectionPoints.getFirstPoint() == null || wandSelectionPoints.getSecondPoint() == null)
            throw new CommandException(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "You didn't specify one or both points!")));

        final Region region = new Region(regionName, player.getWorld().getUniqueId(), wandSelectionPoints.getFirstPoint(), wandSelectionPoints.getSecondPoint(), new HashMap<>());
        boolean didSucceed = RegionService.createRegion(region);
        if (didSucceed)
        {
            player.playSound(SoundTypes.BLOCK_ANVIL_USE, player.getLocation().getPosition(), 5, 10);
            player.getWorld().spawnParticles(ParticleEffect.builder().type(ParticleTypes.END_ROD).quantity(400).offset(new Vector3d(5, 5, 5)).build(), player.getLocation().getPosition());
            player.sendMessage(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.GREEN, "Successfully created a region named " + regionName + "!")));
        }
        else
        {
            player.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "Something went wrong...")));
        }
        return CommandResult.success();
    }
}
