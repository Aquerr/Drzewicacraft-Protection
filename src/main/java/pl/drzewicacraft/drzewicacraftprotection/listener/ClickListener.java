package pl.drzewicacraft.drzewicacraftprotection.listener;

import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import pl.drzewicacraft.drzewicacraftprotection.DrzewicacraftProtection;
import pl.drzewicacraft.drzewicacraftprotection.PluginInfo;
import pl.drzewicacraft.drzewicacraftprotection.entity.WandSelectionPoints;

public class ClickListener extends AbstractListener
{
    public ClickListener(final DrzewicacraftProtection plugin)
    {
        super(plugin);
    }

    @Listener
    public void onLeftClick(final InteractBlockEvent.Primary.MainHand event, final @Root Player player)
    {
        if(event.getInteractionPoint().isPresent() && player.getItemInHand(HandTypes.MAIN_HAND).isPresent()
                && player.getItemInHand(HandTypes.MAIN_HAND).get().getType() == ItemTypes.IRON_SWORD
                && player.getItemInHand(HandTypes.MAIN_HAND).get().get(Keys.DISPLAY_NAME).get().toPlain().equals("DrzewicaCraft Wand"))
        {
            final Vector3i firstPoint = event.getTargetBlock().getPosition();

            if(DrzewicacraftProtection.PLAYER_WAND_SELECTION_POINTS.containsKey(player.getUniqueId()))
            {
                final WandSelectionPoints selectionPoints = DrzewicacraftProtection.PLAYER_WAND_SELECTION_POINTS.get(player.getUniqueId());
                selectionPoints.setFirstPoint(firstPoint);
                DrzewicacraftProtection.PLAYER_WAND_SELECTION_POINTS.replace(player.getUniqueId(), selectionPoints);
            }
            else
            {
                final WandSelectionPoints selectionPoints = new WandSelectionPoints(firstPoint, null);
                DrzewicacraftProtection.PLAYER_WAND_SELECTION_POINTS.put(player.getUniqueId(), selectionPoints);
            }

            player.sendMessage(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.DARK_PURPLE, "Selected first point at ", TextColors.GOLD, firstPoint.toString())));
            event.setCancelled(true);
        }
    }

    @Listener
    public void onRightClick(final InteractBlockEvent.Secondary.MainHand event, final @Root Player player)
    {
        if(event.getTargetBlock() != BlockSnapshot.NONE && player.getItemInHand(HandTypes.MAIN_HAND).isPresent()
                && player.getItemInHand(HandTypes.MAIN_HAND).get().getType() == ItemTypes.IRON_SWORD
                && player.getItemInHand(HandTypes.MAIN_HAND).get().get(Keys.DISPLAY_NAME).get().toPlain().equals("DrzewicaCraft Wand"))
        {
            final Vector3i secondPoint = event.getTargetBlock().getPosition();

            if(DrzewicacraftProtection.PLAYER_WAND_SELECTION_POINTS.containsKey(player.getUniqueId()))
            {
                final WandSelectionPoints selectionPoints = DrzewicacraftProtection.PLAYER_WAND_SELECTION_POINTS.get(player.getUniqueId());
                selectionPoints.setSecondPoint(secondPoint);
                DrzewicacraftProtection.PLAYER_WAND_SELECTION_POINTS.replace(player.getUniqueId(), selectionPoints);
            }
            else
            {
                final WandSelectionPoints selectionPoints = new WandSelectionPoints( null, secondPoint);
                DrzewicacraftProtection.PLAYER_WAND_SELECTION_POINTS.put(player.getUniqueId(), selectionPoints);
            }

            player.sendMessage(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.DARK_PURPLE, "Selected second point at ", TextColors.GOLD, secondPoint.toString())));
            event.setCancelled(true);
        }
    }
}
