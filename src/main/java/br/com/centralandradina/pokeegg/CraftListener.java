package br.com.centralandradina.pokeegg;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class CraftListener implements Listener
{
	protected PokeEgg plugin;
	
	/**
	 * constructor
	 */
	public CraftListener(PokeEgg plugin)
	{
		this.plugin = plugin;
	}

	/**
	 * prevent use uses PokeEgg as normal item to craft something
	 */
	@EventHandler
	public void onPrepareCraft(PrepareItemCraftEvent event) 
	{
		// loop itens on craft table
		for(ItemStack item : event.getInventory().getMatrix()) {
			if(item != null) {

				// if this item is a PokeEgg, set result to nothing
				PokeEggItem pokeegg = new PokeEggItem(item, this.plugin);
				if(pokeegg.isPokeEgg()) {
					event.getInventory().setResult(null);
					break;
				};
			}
		}
	}

	/**
	 * on craft, because each PokeEgg need a unique uuid
	 */
	@EventHandler
	public void onCraftEvent(CraftItemEvent event) 
	{
		ItemStack items =  event.getCurrentItem();

		// if item crafted is a PokeEgg
		PokeEggItem pokeegg = new PokeEggItem(items, this.plugin);
		if(pokeegg.isPokeEgg()) {

			// if crafting with shift, cancel event, close craft and show message to player
			if(event.isShiftClick()) {
				Player player = (Player) event.getWhoClicked();

				player.sendMessage(this.plugin.color(this.plugin.getConfig().getString("messages.craft-one-by-one")));
				event.getInventory().close();
				event.setCancelled(true);
				
				return;
			}

			// set random uuid to PokeEgg
			pokeegg.setRandomUUID();
		}
	}
}
