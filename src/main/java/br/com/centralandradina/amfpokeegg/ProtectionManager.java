package br.com.centralandradina.amfpokeegg;

import br.net.fabiozumbi12.RedProtect.Bukkit.RedProtect;
import br.net.fabiozumbi12.RedProtect.Bukkit.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;


/**
 * 
 */
public class ProtectionManager 
{
	protected AMFPokeEgg plugin;
	protected boolean pluginRedProtect = false;

	/**
	 * constructor
	 */
	public ProtectionManager(AMFPokeEgg plugin)
	{
		this.plugin = plugin;

		PluginManager pluginManager = this.plugin.getServer().getPluginManager();

		// verify if are using RedProtect
		if (pluginManager.isPluginEnabled("RedProtect")) {
			this.plugin.getLogger().info("RedProtect hooked");
			this.pluginRedProtect = true;
		}
	}

	/**
	 * verify if can catch
	 */
	public boolean canCapture(Location entityLocation, Player player)
	{
		// verify redprotect
		if(this.pluginRedProtect) {
			if(!this.hasAccessRedProtect(entityLocation, player)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * verify if can release
	 */
	public boolean canRelease(Location entityLocation, Player player)
	{
		// verify redprotect
		if(this.pluginRedProtect) {
			if(!this.hasAccessRedProtect(entityLocation, player)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * do the verification of RedProtect
	 * nice reference https://github.com/FabioZumbi12/RedProtect/blob/master/RedProtect-Core/src/main/java/br/net/fabiozumbi12/RedProtect/Core/region/CoreRegion.java#L228
	 */
	public boolean hasAccessRedProtect(Location entityLocation, Player player)
	{
		// get region
		Region reg = RedProtect.get().getAPI().getRegion(entityLocation);
		if(reg != null) {

			// is owner
			if(!reg.isLeader(player)) {
				return false;
			}
		}

		return true;
	}
}
