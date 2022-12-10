package pl.drzewicacraft.drzewicacraftprotection.listener;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;

public class EntityMoveListener
{
    @Listener
    public void onEntityMove(MoveEntityEvent event, @Getter("getTargetEntity") Entity entity)
    {
//        if (entity instanceof Player)
//        {
//            Player player = (Player) entity;
//
//            Location lastLocation = event.getFromTransform().getLocation();
//            Location newLocation = event.getToTransform().getLocation();
//
//            if (!lastLocation.equals(newLocation))
//            {
//                Optional<Region> optionalLastRegion = RegionLogic.getRegion(lastLocation);
//                Optional<Region> optionalNewRegion = RegionLogic.getRegion(newLocation);
//
//                //Entering a region
//                if (!optionalLastRegion.isPresent() && optionalNewRegion.isPresent())
//                {
//                    if (optionalNewRegion.get().getFlags().containsKey(FlagType.ENTER_MESSAGE_CHAT))
//                    {
//                        player.sendMessage(PluginInfo.MESSAGE_PREFIX.concat(TextSerializers.FORMATTING_CODE.deserialize((String)optionalNewRegion.get().getFlags().get(FlagType.ENTER_MESSAGE_CHAT))));
//                    }
//
//                    if (optionalNewRegion.get().getFlags().containsKey(FlagType.ENTER_MESSAGE_TITLE) || optionalNewRegion.get().getFlags().containsKey(FlagType.ENTER_MESSAGE_SUBTITLE)
//                            || optionalNewRegion.get().getFlags().containsKey(FlagType.ENTER_MESSAGE_ACTION_BAR))
//                    {
//                        Title.Builder titleBuilder = Title.builder();
//                        titleBuilder.title(TextSerializers.FORMATTING_CODE.deserialize((String)optionalNewRegion.get().getFlags().getOrDefault(FlagType.ENTER_MESSAGE_TITLE, "")));
//                        titleBuilder.subtitle(TextSerializers.FORMATTING_CODE.deserialize((String)optionalNewRegion.get().getFlags().getOrDefault(FlagType.ENTER_MESSAGE_SUBTITLE, "")));
//                        titleBuilder.actionBar(TextSerializers.FORMATTING_CODE.deserialize((String)optionalNewRegion.get().getFlags().getOrDefault(FlagType.ENTER_MESSAGE_ACTION_BAR, "")));
//
//                        player.sendTitle(titleBuilder.build());
//                    }
//                }
//                //Leaving a region
//                else if (optionalLastRegion.isPresent() && !optionalNewRegion.isPresent())
//                {
//                    if (optionalLastRegion.get().getFlags().containsKey(FlagType.LEAVE_MESSAGE_CHAT))
//                    {
//                        player.sendMessage(PluginInfo.MESSAGE_PREFIX.concat(TextSerializers.FORMATTING_CODE.deserialize((String)optionalNewRegion.get().getFlags().get(FlagType.LEAVE_MESSAGE_CHAT))));
//                    }
//
//                    if (optionalLastRegion.get().getFlags().containsKey(FlagType.LEAVE_MESSAGE_TITLE) || optionalLastRegion.get().getFlags().containsKey(FlagType.LEAVE_MESSAGE_SUBTITLE)
//                            || optionalLastRegion.get().getFlags().containsKey(FlagType.LEAVE_MESSAGE_ACTION_BAR))
//                    {
//                        Title.Builder titleBuilder = Title.builder();
//                        titleBuilder.title(TextSerializers.FORMATTING_CODE.deserialize((String)optionalLastRegion.get().getFlags().getOrDefault(FlagType.LEAVE_MESSAGE_TITLE, "")));
//                        titleBuilder.subtitle(TextSerializers.FORMATTING_CODE.deserialize((String)optionalLastRegion.get().getFlags().getOrDefault(FlagType.LEAVE_MESSAGE_SUBTITLE, "")));
//                        titleBuilder.actionBar(TextSerializers.FORMATTING_CODE.deserialize((String)optionalLastRegion.get().getFlags().getOrDefault(FlagType.LEAVE_MESSAGE_ACTION_BAR, "")));
//
//                        player.sendTitle(titleBuilder.build());
//                    }
//                }
//            }
//        }
//        else if(entity instanceof Hostile)
//        {
//            Optional<Region> optionalRegion = RegionLogic.getRegion(entity.getLocation());
//
//            if (optionalRegion.isPresent())
//            {
//                if(!Boolean.valueOf((String)optionalRegion.get().getFlags().get(FlagType.HOSTILE_MOB_ENTER)))
//                {
//                    entity.remove();
//                }
//            }
//        }
    }
}
