package pl.drzewicacraft.drzewicacraftprotection.listener;

import pl.drzewicacraft.drzewicacraftprotection.DrzewicacraftProtection;

public class AbstractListener
{
	private DrzewicacraftProtection plugin;

	public AbstractListener(final DrzewicacraftProtection plugin)
	{
		this.plugin = plugin;
	}

	public DrzewicacraftProtection getPlugin()
	{
		return this.plugin;
	}
}
