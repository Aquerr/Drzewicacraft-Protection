package pl.drzewicacraft.drzewicacraftprotection.config;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class Configuration implements IConfiguration
{
    ConfigurationLoader configLoader;
    ConfigurationNode configNode;

    public Configuration(Path configPath)
    {
        Path configFilePath = configPath.resolve("settings.conf");

        if(!Files.exists(configFilePath))
        {
            try
            {
                if(!Files.exists(configPath))
                {
                    Files.createDirectory(configPath);
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

    @Override
    public boolean setValue(Object value, Object... node)
    {
        try
        {
            configNode.getNode(node).setValue(value);
            save();
            return true;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean getBoolean(boolean defaultValue, Object... node)
    {
        return configNode.getNode(node).getBoolean(defaultValue);
    }

    @Override
    public String getString(String defaultValue, Object... node)
    {
        return configNode.getNode(node).getString(defaultValue);
    }

    @Override
    public Integer getInteger(int defaultValue, Object... node)
    {
        return configNode.getNode(node).getInt(defaultValue);
    }

    @Override
    public List<String> getListOfStrings(List<String> defaultValue, Object... node)
    {
        return configNode.getNode(node).getList(objectToStringTransformer, defaultValue);
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

    @Override
    public boolean reload()
    {
        return load();
    }

    private static Function<Object,String> objectToStringTransformer = input ->
    {
        if (input instanceof String)
        {
            return (String) input;
        }
        else
        {
            return null;
        }
    };
}
