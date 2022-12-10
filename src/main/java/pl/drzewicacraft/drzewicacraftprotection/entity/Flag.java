package pl.drzewicacraft.drzewicacraftprotection.entity;

/**
 * Created by Aquerr on 2018-07-17.
 */
public class Flag
{
    private FlagType flagType;
    private Object value;

    public Flag(FlagType flagType, Object value)
    {
        this.flagType = flagType;
        this.value = value;


        //Handling value type
//        if (!(value instanceof Boolean) && !(value instanceof Integer))
//        {
//            this.value = TextSerializers.FORMATTING_CODE.deserialize((String)value);
//        }
//        else
//        {
//            this.value = value;
//        }
    }
    public FlagType getType()
    {
        return this.flagType;
    }

    public Object getValue()
    {
        return this.value;
    }
}
