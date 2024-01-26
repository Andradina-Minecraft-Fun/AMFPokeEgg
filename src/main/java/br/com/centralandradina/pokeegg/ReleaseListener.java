package br.com.centralandradina.pokeegg;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import java.util.Arrays;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;



public class ReleaseListener implements Listener 
{
	protected PokeEgg plugin;
	
	public ReleaseListener(PokeEgg plugin) {
		this.plugin = plugin;
	}


	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();

		// verify if item on hand are an item
		ItemStack item = player.getEquipment().getItemInMainHand();
		if((item == null) || (item.getAmount() == 0) || (item.getType() == Material.AIR)) {
			return;
		}

		// if the event is not fired by main hand
		if ((event.getHand() == null) || (!event.getHand().equals(EquipmentSlot.HAND))) {
			return;
		}
		
		// verify if item on hand are a pokeegg
		NBTItem nbtItem = new NBTItem(item);
		if(!nbtItem.hasTag("pokeegg-empty")) {
			return;
		}

		// verify if is empty
		if(nbtItem.getBoolean("pokeegg-empty")) {
			player.sendMessage(this.plugin.color(this.plugin.getConfig().getString("messages.is-empty")));
			event.setCancelled(true);
			return;
		}

		// retrieve location (@todo look at the face of block clicked and add +1 block in direction of player, to prevent mob stuck on wall)
		Location location = event.getInteractionPoint() == null ? event.getPlayer().getLocation() : event.getInteractionPoint();

		// verify if are protected
		if(!this.plugin.protectionManager.canRelease(location, player)) {
			player.sendMessage(this.plugin.color(this.plugin.getConfig().getString("messages.location-no-permitted")));
			event.setCancelled(true);
			return;
		}

		// cancel event
		event.setCancelled(true);


		// retrieve nbt from entity
		String nbt = nbtItem.getString("pokeegg-nbt");
		String type = nbtItem.getString("pokeegg-type");

		// create nbt container
		NBTContainer container = new NBTContainer(nbt);

		// remove some position and specific tags (found on some another plugins on github)
		String[] arr = {"Pos", "Paper.Origin", "FallFlying", "Motion"};
		Arrays.stream(arr).forEach(container::removeKey);

		// log
		// this.plugin.getLogger().info("\n\n");
		// this.plugin.getLogger().info(container.toString());
		// this.plugin.getLogger().info("\n\n");

		// spawn entity
		player.getWorld().spawnEntity(location, EntityType.fromName(type), CreatureSpawnEvent.SpawnReason.CUSTOM, entity -> {
			NBTEntity nbtEntity = new NBTEntity(entity);
			nbtEntity.mergeCompound(container);
		});

		// destroy item
		player.getInventory().remove(item);
		
		// notify player
			
		
	}
}
