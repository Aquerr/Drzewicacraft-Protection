package pl.drzewicacraft.drzewicacraftprotection.listener;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;

public class EntitySpawnListener
{
    @Listener
    public void onEntitySpawn(SpawnEntityEvent event)
    {
//        for(Entity entity : event.getEntities())
//        {
//            if(entity instanceof Hostile)
//            {
//                Optional<Region> optionalRegion = RegionLogic.getRegion(entity.getLocation());
//
//                if(optionalRegion.isPresent() && !(Boolean.valueOf((String) optionalRegion.get().getFlags().get(FlagType.HOSTILE_MOB_SPAWN))))
//                {
//                    event.setCancelled(true);
//                }
//            }
//        }
    }
}
