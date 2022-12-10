package pl.drzewicacraft.drzewicacraftprotection.config;

import java.util.List;

public interface IConfiguration
{
//    boolean setBoolean(boolean value, Object... node);
//
//    boolean setString(String value, Object... node);
//
//    boolean setInteger(int value, Object... node);
//
//    boolean setListOfStrings(List<String> value, Object... node);

    boolean setValue(Object value, Object... node);

    boolean getBoolean(boolean defaultValue, Object... node);

    String getString(String defaultValue, Object... node);

    Integer getInteger(int defaultValue, Object... node);

    List<String> getListOfStrings(List<String> defaultValue, Object... node);

    boolean reload();
}
