package pl.drzewicacraft.drzewicacraftprotection.command;

import com.flowpowered.math.vector.Vector3i;
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
import pl.drzewicacraft.drzewicacraftprotection.entity.ExpandDirection;
import pl.drzewicacraft.drzewicacraftprotection.entity.WandSelectionPoints;

import java.util.Optional;

public class ExpandCommand implements CommandExecutor
{
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException
    {
        Optional<ExpandDirection> optionalDirection = args.getOne(Text.of("direction"));
        Optional<Integer> optionalDistance = args.getOne(Text.of("distance"));

        if(source instanceof Player)
        {
            if(optionalDirection.isPresent() && optionalDistance.isPresent())
            {
                ExpandDirection direction = optionalDirection.get();
                int distance = optionalDistance.get();

                Player player = (Player)source;

                if(DrzewicacraftProtection.PLAYER_WAND_SELECTION_POINTS.containsKey(player.getUniqueId()))
                {
                    WandSelectionPoints wandSelectionPoints = DrzewicacraftProtection.PLAYER_WAND_SELECTION_POINTS.get(player.getUniqueId());

                    WandSelectionPoints expandedSelectionPoints = handleExpand(wandSelectionPoints, direction, distance);

                    DrzewicacraftProtection.PLAYER_WAND_SELECTION_POINTS.replace(player.getUniqueId(), wandSelectionPoints, expandedSelectionPoints);

                    source.sendMessage(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.GREEN, "Successfully expanded selections points.")));
                    source.sendMessage(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.DARK_PURPLE, "First point: " + expandedSelectionPoints.getFirstPoint().toString())));
                    source.sendMessage(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.DARK_PURPLE, "Second point: " + expandedSelectionPoints.getSecondPoint().toString())));
                }
                else
                {
                    source.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "You need to select two points first!")));
                }
            }
            else
            {
                source.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "Wrong distance or direction!")));
            }
        }
        else
        {
            source.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of(TextColors.DARK_RED, "Only in-game players can use this command!")));
        }
        return CommandResult.success();
    }

    private WandSelectionPoints handleExpand(WandSelectionPoints wandSelectionPoints, ExpandDirection direction, Integer distance)
    {
        Vector3i firstPoint = wandSelectionPoints.getFirstPoint();
        Vector3i secondPoint = wandSelectionPoints.getSecondPoint();

        if("up".equals(direction.toString().toLowerCase()))
        {
            if(firstPoint.getY() > secondPoint.getY())
                firstPoint = firstPoint.add(0, distance, 0);
            else
                secondPoint = secondPoint.add(0, distance, 0);
        }
        else if("down".equals(direction.toString().toLowerCase()))
        {
            if(firstPoint.getY() < secondPoint.getY())
                firstPoint = firstPoint.sub(0, distance, 0);
            else
                secondPoint = secondPoint.sub(0, distance, 0);
        }
        else if("east".equals(direction.toString().toLowerCase()))
        {
            if(firstPoint.getX() > secondPoint.getX())
                firstPoint = firstPoint.add(distance, 0, 0);
            else
                secondPoint = secondPoint.add(distance, 0, 0);
        }
        else if("west".equals(direction.toString().toLowerCase()))
        {
            if(firstPoint.getX() < secondPoint.getX())
                firstPoint = firstPoint.sub(distance, 0, 0);
            else
                secondPoint = secondPoint.sub(distance, 0, 0);
        }
        else if("north".equals(direction.toString().toLowerCase()))
        {
            if(firstPoint.getZ() < secondPoint.getZ())
                firstPoint = firstPoint.sub(0, 0, distance);
            else
                secondPoint = secondPoint.sub(0, 0, distance);
        }
        else if("south".equals(direction.toString().toLowerCase()))
        {
            if(firstPoint.getZ() > secondPoint.getZ())
                firstPoint = firstPoint.add(0, 0, distance);
            else
                secondPoint = secondPoint.add(0, 0, distance);
        }

        return new WandSelectionPoints(firstPoint, secondPoint);
    }
}
