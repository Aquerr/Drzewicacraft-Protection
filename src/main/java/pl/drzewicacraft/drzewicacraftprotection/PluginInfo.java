package pl.drzewicacraft.drzewicacraftprotection;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class PluginInfo
{
    public static final String ID = "drzewicacraft-protection";
    public static final String NAME = "DrzewicaCraft-Protection";
    public static final String VERSION = "1.0.0";
    public static final String DESCRIPTION = "World protection plugin for DrzewicaCraft Server";
    public static final String[] AUTHORS = {"Aquerr"};
    public static final String URL = "https://drzewicacraft.pl";

    public static final Text MESSAGE_PREFIX = Text.of(TextColors.DARK_GREEN, "[DP] ", TextColors.GREEN);
    public static final Text ERROR_PREFIX = Text.of(TextColors.DARK_RED, "[DP] ", TextColors.RED);
}
