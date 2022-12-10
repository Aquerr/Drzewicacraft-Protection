package pl.drzewicacraft.drzewicacraftprotection.storage;

import pl.drzewicacraft.drzewicacraftprotection.entity.Flag;
import pl.drzewicacraft.drzewicacraftprotection.entity.FlagType;
import pl.drzewicacraft.drzewicacraftprotection.entity.Region;

import javax.annotation.Nullable;
import java.util.List;

public interface IStorage
{
    boolean createRegion(Region region);

    boolean setFlag(String regionName, Flag flag);

//    @Nullable
//    Region getRegion(Location location);

    @Nullable
    Region getRegion(String name);

    List<Region> getRegions();

    boolean deleteRegion(String regionName);

    boolean removeFlag(String name, FlagType type);

    boolean updateRegion(String oldName, Region region);
}
