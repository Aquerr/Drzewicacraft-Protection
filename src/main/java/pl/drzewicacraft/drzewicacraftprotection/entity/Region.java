package pl.drzewicacraft.drzewicacraftprotection.entity;

import com.flowpowered.math.vector.Vector3i;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Region
{
    private String name;
    private UUID worldUUID;
    private Vector3i firstPoint;
    private Vector3i secondPoint;

    private Map<FlagType, Object> flags;

    public Region(final String name, final UUID worldUUID, final Vector3i firstPoint, final Vector3i secondPoint, final Map<FlagType, Object> regionFlags)
    {
        this.name = name;
        this.worldUUID = worldUUID;
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.flags = regionFlags;
    }

    public String getName()
    {
        return this.name;
    }

    public UUID getWorldUUID()
    {
        return this.worldUUID;
    }

    public Vector3i getFirstPoint()
    {
        return this.firstPoint;
    }

    public Vector3i getSecondPoint()
    {
        return this.secondPoint;
    }

    public Map<FlagType, Object> getFlags()
    {
        return this.flags;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public void setWorldUUID(final UUID worldUUID)
    {
        this.worldUUID = worldUUID;
    }

    public void setFirstPoint(final Vector3i firstPoint)
    {
        this.firstPoint = firstPoint;
    }

    public void setSecondPoint(final Vector3i secondPoint)
    {
        this.secondPoint = secondPoint;
    }

    public void setFlag(final Flag flag)
    {
        if (this.flags.containsKey(flag.getType()))
        {
            this.flags.replace(flag.getType(), flag.getValue());
        }
        else
        {
            this.flags.put(flag.getType(), flag.getValue());
        }
    }

    public boolean hasFlag(final FlagType flagType)
    {
        return this.flags.containsKey(flagType);
    }

    public void removeFlag(final FlagType flagType)
    {
        this.flags.remove(flagType);
    }

    public boolean intersects(final Vector3i blockPosition)
    {
        boolean intersectsWithX = false;
        boolean intersectsWithY = false;
        boolean intersectsWithZ = false;

        //Check X
        if(this.firstPoint.getX() <= this.secondPoint.getX() && blockPosition.getX() >= this.firstPoint.getX() && blockPosition.getX() <= this.secondPoint.getX())
        {
            intersectsWithX = true;
        }
        else if(this.firstPoint.getX() >= this.secondPoint.getX() && blockPosition.getX() <= this.firstPoint.getX() && blockPosition.getX() >= this.secondPoint.getX())
        {
            intersectsWithX = true;
        }

        //Check Z
        if(this.firstPoint.getZ() <= this.secondPoint.getZ() && blockPosition.getZ() >= this.firstPoint.getZ() && blockPosition.getZ() <= this.secondPoint.getZ())
        {
            intersectsWithZ = true;
        }
        else if(this.firstPoint.getZ() >= this.secondPoint.getZ() && blockPosition.getZ() <= this.firstPoint.getZ() && blockPosition.getZ() >= this.secondPoint.getZ())
        {
            intersectsWithZ = true;
        }

        //Check Y
        if(this.firstPoint.getY() <= this.secondPoint.getY() && blockPosition.getY() >= this.firstPoint.getY() && blockPosition.getY() <= this.secondPoint.getY())
        {
            intersectsWithY = true;
        }
        else if(this.firstPoint.getY() >= this.secondPoint.getY() && blockPosition.getY() <= this.firstPoint.getY() && blockPosition.getY() >= this.secondPoint.getY())
        {
            intersectsWithY = true;
        }

        return intersectsWithX && intersectsWithZ && intersectsWithY;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Region region = (Region) o;
        return name.equals(region.name) &&
                worldUUID.equals(region.worldUUID) &&
                firstPoint.equals(region.firstPoint) &&
                secondPoint.equals(region.secondPoint) &&
                flags.equals(region.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, worldUUID, firstPoint, secondPoint, flags);
    }
}
