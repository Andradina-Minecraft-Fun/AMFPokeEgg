package br.com.centralandradina.amfpokeegg;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * class to handle catch entity
 */
public class CatchListener implements Listener
{
	protected AMFPokeEgg plugin;

	/**
	 * constructor
	 * 
	 * @param plugin
	 */
	public CatchListener(AMFPokeEgg plugin)
	{
		this.plugin = plugin;
	}

	/**
	 * event to trigger when player interact with some entity
	 */
	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEntityEvent event)
	{
		Player player = event.getPlayer();

		// verify if item on hand are an item
		ItemStack item = player.getEquipment().getItemInMainHand();
		if((item == null) || (item.getAmount() == 0) || (item.getType() == Material.AIR)){
			return;
		}

		// if the event is not fired by main hand
		if (!event.getHand().equals(EquipmentSlot.HAND)) {
			return;
		}

		// create a pokeegg
		PokeEggItem pokeegg = new PokeEggItem(item, this.plugin);
		
		// verify if item on hand are a pokeegg
		if(!pokeegg.isPokeEgg()) {
			return;
		}

		// verify if is empty
		if(!pokeegg.isEmpty()) {
			player.sendMessage(this.plugin.color(this.plugin.getConfig().getString("messages.not-empty")));
			event.setCancelled(true);
			return;
		}

		// verify if entity exists
		Entity entity = event.getRightClicked();

		// verify if are protected
		if(!this.plugin.protectionManager.canCapture(entity.getLocation(), player)) {
			player.sendMessage(this.plugin.color(this.plugin.getConfig().getString("messages.location-no-permitted")));
			event.setCancelled(true);
			return;
		}

		// verify if entity is on the list of permission
		boolean isPermited = false;
		for (String sEntity : plugin.getConfig().getStringList("entities")) {
			if(entity.getType().toString().equalsIgnoreCase(sEntity)) {
				isPermited = true;
				break;
			}
		}
		if(!isPermited) {
			player.sendMessage(this.plugin.color(this.plugin.getConfig().getString("messages.entity-no-permitted")));
			event.setCancelled(true);
			return;
		}

		// verify if player has this permission

		// store NBT on pokeegg
		pokeegg.catchEntity(entity);

		// parse lore of item
		pokeegg.parseLore();
	
		// remove entity from word
		entity.remove();

		// notify player
		player.sendMessage(this.plugin.color(this.plugin.getConfig().getString("messages.catched")));

		// cancel event
		event.setCancelled(true);
	}

}
