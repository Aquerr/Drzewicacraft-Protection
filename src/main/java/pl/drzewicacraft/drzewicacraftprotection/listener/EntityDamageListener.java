package pl.drzewicacraft.drzewicacraftprotection.listener;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;

public class EntityDamageListener
{
    @Listener
    public void onEntityReceiveDamage(DamageEntityEvent event)
    {
//        if(event.getCause().root() instanceof DamageSource)
//        {
//            if(event.getTargetEntity().getType() == EntityTypes.PLAYER)
//            {
//                Player attackedPlayer = (Player) event.getTargetEntity();
//                Optional<Region> optionalRegion = RegionLogic.getRegion(attackedPlayer.getLocation());
//
//                if(optionalRegion.isPresent() && Boolean.valueOf((String) optionalRegion.get().getFlags().get(FlagType.IGNORE_PLAYER_DAMAGE)))
//                {
//                    event.setBaseDamage(0);
//                    event.setCancelled(true);
//                }
//            }
//        }
    }
}
