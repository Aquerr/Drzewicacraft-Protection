package pl.drzewicacraft.drzewicacraftprotection.service;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import pl.drzewicacraft.drzewicacraftprotection.entity.Flag;
import pl.drzewicacraft.drzewicacraftprotection.entity.FlagType;
import pl.drzewicacraft.drzewicacraftprotection.entity.Region;
import pl.drzewicacraft.drzewicacraftprotection.storage.HOCONStorage;
import pl.drzewicacraft.drzewicacraftprotection.storage.IStorage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegionService
{
    //TODO: Add a separate thread for saving regions in the file.

    private static final List<Region> regionListCache = new ArrayList<>();

    private static IStorage regionStorage;

    public static void load(Path configDir)
    {
        regionStorage = new HOCONStorage(configDir);
        loadCache();
    }

    private static void loadCache()
    {
        regionListCache.addAll(regionStorage.getRegions());
    }

    public static boolean isSafeZone(Location location)
    {
        for(Region region : getSafeZoneRegions())
        {
            if(region.intersects(location.getBlockPosition()))
            {
                return true;
            }
        }

        return false;
    }

    public static Optional<Region> getRegion(String name)
    {
        for(Region regionCache : regionListCache)
        {
            if (regionCache.getName().equals(name))
            {
                return Optional.of(regionCache);
            }
        }
        return Optional.empty();
    }

    public static boolean createRegion(Region region)
    {
        regionListCache.add(region);
        return regionStorage.createRegion(region);
    }

    public static List<Region> getRegions()
    {
        return regionListCache;
    }

    public static List<Region> getSafeZoneRegions()
    {
        List<Region> regions = getRegions();
        List<Region> safeZoneRegions = new ArrayList<>();

        for(Region region : regions)
        {
            if((boolean)region.getFlags().get(FlagType.IGNORE_PLAYER_DAMAGE))
            {
                safeZoneRegions.add(region);
            }
        }

        return safeZoneRegions;
    }

    public static Optional<Region> getRegion(Location<World> location)
    {
        for(Region region : regionListCache)
        {
            if(region.getWorldUUID().equals(location.getExtent().getUniqueId()) && region.intersects(location.getBlockPosition()))
                return Optional.of(region);
        }

        return Optional.empty();
    }

    public static boolean removeRegion(Region region)
    {
        regionListCache.remove(region);
        return regionStorage.deleteRegion(region.getName());
    }

    public static boolean setFlag(Region region, Flag flag)
    {
        try
        {
            regionListCache.remove(region);
            region.setFlag(flag);
            regionListCache.add(region);
            return regionStorage.setFlag(region.getName(), flag);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
    }

    public static boolean removeFlag(Region region, FlagType flagType)
    {
        try
        {
            regionListCache.remove(region);
            region.getFlags().remove(flagType);
            regionListCache.add(region);
            return regionStorage.removeFlag(region.getName(), flagType);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
    }

    public static boolean changeName(Region region, String name)
    {
        try
        {
            String oldName = region.getName();
            regionListCache.remove(region);
            region.setName(name);
            regionListCache.add(region);
            return regionStorage.updateRegion(oldName, region);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
    }
}
