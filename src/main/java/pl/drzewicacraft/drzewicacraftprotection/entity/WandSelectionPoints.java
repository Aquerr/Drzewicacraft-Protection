package pl.drzewicacraft.drzewicacraftprotection.entity;

import com.flowpowered.math.vector.Vector3i;

public class WandSelectionPoints
{
    private Vector3i firstPoint;
    private Vector3i secondPoint;

    public WandSelectionPoints(Vector3i firstPoint, Vector3i secondPoint)
    {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
    }

    public Vector3i getFirstPoint()
    {
        return firstPoint;
    }

    public Vector3i getSecondPoint()
    {
        return secondPoint;
    }

    public void setFirstPoint(Vector3i firstPoint)
    {
        this.firstPoint = firstPoint;
    }

    public void setSecondPoint(Vector3i secondPoint)
    {
        this.secondPoint = secondPoint;
    }
}
