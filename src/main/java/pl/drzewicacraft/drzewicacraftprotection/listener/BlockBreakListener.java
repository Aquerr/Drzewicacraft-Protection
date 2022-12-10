package pl.drzewicacraft.drzewicacraftprotection.listener;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.hanging.ItemFrame;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.entity.CollideEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.LocatableBlock;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import pl.drzewicacraft.drzewicacraftprotection.DrzewicacraftProtection;
import pl.drzewicacraft.drzewicacraftprotection.PluginInfo;
import pl.drzewicacraft.drzewicacraftprotection.entity.FlagType;
import pl.drzewicacraft.drzewicacraftprotection.entity.Region;
import pl.drzewicacraft.drzewicacraftprotection.service.RegionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlockBreakListener extends AbstractListener
{
    public BlockBreakListener(final DrzewicacraftProtection plugin)
    {
        super(plugin);
    }

    @Listener
    public void onBlockModify(ChangeBlockEvent.Modify event)
    {
        User user = null;
        if(event.getCause().containsType(Player.class))
        {
            user = event.getCause().first(Player.class).get();
        }
        else if(event.getCause().containsType(User.class))
        {
            user = event.getCause().first(User.class).get();
        }

        if(user != null)
        {
            for (Transaction<BlockSnapshot> transaction : event.getTransactions())
            {
                final Optional<Location<World>> optionalLocation = transaction.getFinal().getLocation();
                if(optionalLocation.isPresent())
                {
                    Optional<Region> optionalRegion = RegionService.getRegion(optionalLocation.get());
                    if(optionalRegion.isPresent() && optionalRegion.get().getFlags().containsKey(FlagType.PROTECT_BLOCKS))
                    {
                        ((Player) user).sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of("Nie masz do tego uprawnień!")));
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @Listener
    public void onBlockBreak(final ChangeBlockEvent.Break event, final @Root Player player)
    {
        if(!player.hasPermission("drzewicacraftprotection.world." + player.getWorld().getName().toLowerCase()))
        {
            player.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of("Nie masz do tego uprawnień!")));
            event.setCancelled(true);
            return;
        }
    }

    @Listener
    public void onBlockPlace(final ChangeBlockEvent.Place event, final @Root Player player)
    {
        if(!player.hasPermission("drzewicacraftprotection.world." + player.getWorld().getName().toLowerCase()))
        {
            player.sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of("Nie masz do tego uprawnień!")));
            event.setCancelled(true);
            return;
        }
    }

    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onBlockPre(ChangeBlockEvent.Pre event)
    {
        User user = null;
        if(event.getCause().containsType(Player.class))
        {
            user = event.getCause().first(Player.class).get();
        }
        else if(event.getCause().containsType(User.class))
        {
            user = event.getCause().first(User.class).get();
        }

        final LocatableBlock locatableBlock = event.getCause().first(LocatableBlock.class).orElse(null);
        final TileEntity tileEntity = event.getCause().first(TileEntity.class).orElse(null);
        final boolean pistonExtend = event.getContext().containsKey(EventContextKeys.PISTON_EXTEND);
        final boolean isLiquidSource = event.getContext().containsKey(EventContextKeys.LIQUID_FLOW);
        final boolean isFireSource = !isLiquidSource && event.getContext().containsKey(EventContextKeys.FIRE_SPREAD);
        final boolean isLeafDecay = event.getContext().containsKey(EventContextKeys.LEAVES_DECAY);
        final boolean isForgePlayerBreak = event.getContext().containsKey(EventContextKeys.PLAYER_BREAK);
        final Location<World> sourceLocation = locatableBlock != null ? locatableBlock.getLocation() : tileEntity != null ? tileEntity.getLocation() : null;

        if(isForgePlayerBreak && user instanceof Player)
        {
            for(Location<World> location : event.getLocations())
            {
                if(location.getBlockType() == BlockTypes.AIR)
                    continue;

                if(!user.hasPermission("drzewicacraftprotection.world." + location.getExtent().getName()))
                {
                    ((Player) user).sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of("Nie masz do tego uprawnień!")));
                    event.setCancelled(true);
                    return;
                }
//                Optional<Region> optionalRegion = RegionService.getRegion(location);
//                if(optionalRegion.isPresent() && optionalRegion.get().getFlags().containsKey(FlagType.PROTECT_BLOCKS))
//                {
//                    event.setCancelled(true);
//                    return;
//                }
            }
        }

        if(sourceLocation != null)
        {
            List<Location<World>> sourceLocations = event.getLocations();
            if(pistonExtend)
            {
                sourceLocations = new ArrayList<>(event.getLocations());
                Location<World> location = sourceLocations.get(sourceLocations.size() - 1);
                final Direction direction = locatableBlock.getLocation().getBlock().get(Keys.DIRECTION).get();
                final Location<World> directionLocation = location.getBlockRelative(direction);
                sourceLocations.add(directionLocation);
            }
            for(final Location<World> location : sourceLocations)
            {
                if(user != null && pistonExtend)
                {
                    if(!user.hasPermission("drzewicacraftprotection.world." + location.getExtent().getName().toLowerCase()))
                    {
                        ((Player) user).sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of("Nie masz do tego uprawnień!")));
                        event.setCancelled(true);
                        return;
                    }
//                    Optional<Region> optionalRegion = RegionService.getRegion(location);
//                    if(optionalRegion.isPresent() && optionalRegion.get().getFlags().containsKey(FlagType.PROTECT_BLOCKS))
//                    {
//                        event.setCancelled(true);
//                        return;
//                    }
                }

                if(isFireSource)
                {
                    final Optional<Region> optionalRegion = RegionService.getRegion(location);
                    if(optionalRegion.isPresent() && optionalRegion.get().getFlags().containsKey(FlagType.PROTECT_BLOCKS))
                    {
                        event.setCancelled(true);
                        return;
                    }
                }

                if(isLiquidSource)
                    continue;

                if(isLeafDecay)
                    continue;

                final Optional<Region> optionalRegion = RegionService.getRegion(location);
                if(user != null && optionalRegion.isPresent() && optionalRegion.get().getFlags().containsKey(FlagType.PROTECT_BLOCKS))
                {
                    event.setCancelled(true);
                    return;
                }
                else if(user == null && optionalRegion.isPresent() && optionalRegion.get().getFlags().containsKey(FlagType.PROTECT_BLOCKS))
                {
                    event.setCancelled(true);
                    return;
                }
            }
        }
        else if(user != null)
        {
            for(final Location<World> location : event.getLocations())
            {
                if(!user.hasPermission("drzewicacraftprotection.world." + location.getExtent().getName().toLowerCase()))
                {
                    ((Player) user).sendMessage(PluginInfo.ERROR_PREFIX.concat(Text.of("Nie masz do tego uprawnień!")));
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onEntityCollideEntity(final CollideEntityEvent event)
    {
        if(event instanceof CollideEntityEvent.Impact)
            return;

        final Object rootCause = event.getCause().root();
        if(!(rootCause instanceof ItemFrame))
            return;

        event.filterEntities(entity ->
        {
            if(entity instanceof Living)
            {
                Optional<Region> optionalRegion = RegionService.getRegion(entity.getLocation());
                if(entity instanceof User && optionalRegion.isPresent() && optionalRegion.get().getFlags().containsKey(FlagType.PROTECT_ITEM_FRAMES))
                {
                    return false;
                }
            }
            return true;
        });
    }

    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onItemUse(final InteractItemEvent event, @Root final Player player)
    {
        if (event.getItemStack() == ItemStackSnapshot.NONE)
            return;

        final Optional<Vector3d> optionalInteractionPoint = event.getInteractionPoint();
        if (!optionalInteractionPoint.isPresent())
            return;

        final Location<World> location = new Location<>(player.getWorld(), optionalInteractionPoint.get());
        final Optional<Region> optionalRegion = RegionService.getRegion(location);
        if (optionalRegion.isPresent() && optionalRegion.get().getFlags().containsKey(FlagType.PROTECT_ITEM_FRAMES))
        {
            event.setCancelled(true);
            return;
        }
    }

    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onBlockInteract(final InteractBlockEvent event, @Root final Player player)
    {
        //If AIR or NONE then return
        if (event.getTargetBlock() == BlockSnapshot.NONE)
            return;

        final Optional<Location<World>> optionalLocation = event.getTargetBlock().getLocation();
        if (!optionalLocation.isPresent())
            return;

        final Location<World> blockLocation = optionalLocation.get();

        final Optional<Region> optionalRegion = RegionService.getRegion(blockLocation);
        if (optionalRegion.isPresent() && optionalRegion.get().getFlags().containsKey(FlagType.PROTECT_ITEM_FRAMES))
        {
            event.setCancelled(true);
            return;
        }
    }
}
