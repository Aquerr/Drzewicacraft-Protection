package pl.drzewicacraft.drzewicacraftprotection.storage;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import pl.drzewicacraft.drzewicacraftprotection.entity.Flag;
import pl.drzewicacraft.drzewicacraftprotection.entity.FlagType;
import pl.drzewicacraft.drzewicacraftprotection.entity.Region;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class HOCONStorage implements IStorage
{
    private ConfigurationLoader configLoader;
    private ConfigurationNode configNode;

    public HOCONStorage(Path configPath)
    {
        Path configFilePath = configPath.resolve("regions").resolve("regions.conf");

        if(!Files.exists(configFilePath))
        {
            try
            {
                if(!Files.exists(configPath))
                {
                    Files.createDirectory(configPath);
                }

                if(!Files.exists(configPath.resolve("regions")))
                {
                    Files.createDirectory(configPath.resolve("regions"));
                }

                Files.createFile(configFilePath);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        configLoader = HoconConfigurationLoader.builder().setPath(configFilePath).build();
        load();
    }

    private boolean load()
    {
        try
        {
            configNode = configLoader.load();
            return true;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private boolean save()
    {
        try
        {
            configLoader.save(configNode);
            return true;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Nullable
    private Region createRegionObject(String name)
    {
        try
        {
            UUID worldUUID = configNode.getNode("regions", name, "worldUUID").getValue(TypeToken.of(UUID.class));
            Vector3i firstPoint = configNode.getNode("regions", name, "points", "first-point").getValue(TypeToken.of(Vector3i.class));
            Vector3i secondPoint = configNode.getNode("regions", name, "points", "second-point").getValue(TypeToken.of(Vector3i.class));

            Map<FlagType, Object> regionFlags = new HashMap<>();
            Object flagNode = configNode.getNode("regions", name, "flags").getValue();

            if (flagNode != null)
            {
                if (flagNode instanceof Map)
                {
                    Map<String, Object> flags = (Map<String, Object>)flagNode;

                    for (Map.Entry<String, Object> flagEntry : flags.entrySet())
                    {
                        regionFlags.put(FlagType.valueOf(flagEntry.getKey()), flagEntry.getValue());
                    }
                }
            }

            return new Region(name, worldUUID, firstPoint, secondPoint, regionFlags);
        }
        catch(ObjectMappingException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean createRegion(Region region)
    {
        try
        {
            configNode.getNode("regions", region.getName(), "worldUUID").setValue(TypeToken.of(UUID.class), region.getWorldUUID());
            configNode.getNode("regions", region.getName(), "points", "first-point").setValue(TypeToken.of(Vector3i.class), region.getFirstPoint());
            configNode.getNode("regions", region.getName(), "points", "second-point").setValue(TypeToken.of(Vector3i.class), region.getSecondPoint());
            configNode.getNode("regions", region.getName(), "flags").setValue(region.getFlags());

            save();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteRegion(String regionName)
    {
        boolean didSucceed = configNode.getNode("regions").removeChild(regionName);
        save();
        return didSucceed;
    }

    @Override
    public boolean removeFlag(String regionName, FlagType type)
    {
        configNode.getNode("regions", regionName, "flags").removeChild(type.name());
        save();

        return true;
    }

    @Override
    public boolean updateRegion(String oldName, Region region)
    {
        configNode.getNode("regions").removeChild(oldName);
        return createRegion(region);
    }

    @Override
    public boolean setFlag(String regionName, Flag flag)
    {
//        if (flag.getType() == FlagType.ENTER_MESSAGE || flag.getType() == FlagType.LEAVE_MESSAGE)
//        {
//            try
//            {
//                configNode.getNode("regions", regionName, "flags", flag.getType()).setValue(TypeToken.of(Text.class), (Text)flag.getValue());
//            }
//            catch (ObjectMappingException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        else
//        {
//            configNode.getNode("regions", regionName, "flags", flag.getType()).setValue(flag.getValue());
//        }
        configNode.getNode("regions", regionName, "flags", flag.getType()).setValue(flag.getValue());

        save();

        return true;
    }

//    @Override
//    @Nullable
//    public Region getRegion(Location location)
//    {
//        Set<Object> regionNamesSet = configNode.getNode("regions").getChildrenMap().keySet();
//
//        for(Object regionName : regionNamesSet)
//        {
//            try
//            {
//                String regionType = configNode.getNode("regions", regionName, "type").getString();
//                Vector3i firstPoint = configNode.getNode("regions", regionName, "points", "first-point").getValue(TypeToken.of(Vector3i.class));
//                Vector3i secondPoint = configNode.getNode("regions", regionName, "points", "first-point").getValue(TypeToken.of(Vector3i.class));
//
//                //return new Region(RegionTypes.valueOf(regionType), firstPoint, secondPoint);
//            }
//            catch(ObjectMappingException e)
//            {
//                e.printStackTrace();
//            }
//        }
//
//        return null;
//    }

    @Override
    @Nullable
    public Region getRegion(String name)
    {
        Set<Object> regionNamesSet = configNode.getNode("regions").getChildrenMap().keySet();

        for(Object regionName : regionNamesSet)
        {
            if(regionName.equals(name))
            {
                return createRegionObject(name);
            }
        }

        return null;
    }

    @Override
    public List<Region> getRegions()
    {
        Set<Object> regionNames = configNode.getNode("regions").getChildrenMap().keySet();
        List<Region> regionList = new ArrayList<>(regionNames.size());

        for(Object regionName : regionNames)
        {
            regionList.add(createRegionObject((String)regionName));
        }

        return regionList;
    }
}
