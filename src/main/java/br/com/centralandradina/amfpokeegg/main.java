package br.com.centralandradina.amfpokeegg;

import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin
{
	
	@Override
	public void onEnable() 
	{
		

		getServer().getPluginManager().registerEvents(new CatchListener(this), this);
		getServer().getPluginManager().registerEvents(new ReleaseListener(this), this);

		getLogger().info("AMFPokeEgg enabled");
	}

	@Override
	public void onDisable()
	{
		getLogger().info("AMFPokeEgg disabled");
	}

	
	
}