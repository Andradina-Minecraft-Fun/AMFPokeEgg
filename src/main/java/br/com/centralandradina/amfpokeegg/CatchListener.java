package br.com.centralandradina.amfpokeegg;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.nbtapi.NBTEntity;

public class CatchListener  implements Listener 
{
	private final JavaPlugin plugin;

	public CatchListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEntityEvent event)
	{
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();

		player.sendMessage("ENTROU");

		// le o nbt
		NBTEntity nbt = new NBTEntity(entity);
		player.sendMessage(nbt.toString());
		plugin.getServer().getLogger().info(nbt.toString());
	}

}
