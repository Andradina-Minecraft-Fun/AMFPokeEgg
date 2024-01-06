package br.com.centralandradina.amfpokeegg;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;

/**
 * class to handle catch entity
 */
public class CatchListener implements Listener {
	private final main plugin;

	/**
	 * constructor
	 * 
	 * @param plugin
	 */
	public CatchListener(main plugin) {
		this.plugin = plugin;
	}

	/**
	 * evento trigger when player interact with some entity
	 */
	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();

		// verify if item on hand are an item
		ItemStack item = player.getEquipment().getItemInMainHand();
		if((item == null) || (item.getAmount() == 0) || (item.getType() == Material.AIR)) {
			return;
		}

		// if the event is not fired by main hand
		if (!event.getHand().equals(EquipmentSlot.HAND)) {
			return;
		}
		
		// verify if item on hand are a pokeegg
		NBTItem nbtItem = new NBTItem(item);
		if(!nbtItem.hasTag("pokeegg-empty")) {
			return;
		}

		// verify if is empty
		if(!nbtItem.getBoolean("pokeegg-empty")) {
			player.sendMessage(this.plugin.color(this.plugin.getConfig().getString("messages.not-empty")));
			event.setCancelled(true);
			return;
		}

		// verify if entity exists
		Entity entity = event.getRightClicked();

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

		// get nbt from entity
		NBTEntity nbt = new NBTEntity(entity);

		// store NBT on pokeegg
		nbtItem.setString("pokeegg-nbt", nbt.toString());
		nbtItem.setString("pokeegg-type", entity.getType().toString());

		// remove entity from word
		entity.remove();

		// notify player
		player.sendMessage(this.plugin.color(this.plugin.getConfig().getString("messages.catched")));

		// update and save nbt
		nbtItem.setBoolean("pokeegg-empty", false);
		nbtItem.applyNBT(item);

		// change lore of item (if villager, show itens to sell and profission)
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		for (String s : this.plugin.getConfig().getStringList("unique.nonempty-lore")) {
			lore.add(this.plugin.color(s));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);

		// cancel event
		event.setCancelled(true);
	}

}
