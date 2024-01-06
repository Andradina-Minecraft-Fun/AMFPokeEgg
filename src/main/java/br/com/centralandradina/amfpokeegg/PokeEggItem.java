package br.com.centralandradina.amfpokeegg;

import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * class of the PokeEgg
 */
public class PokeEggItem
{
	protected AMFPokeEgg plugin;
	protected ItemStack pokeegg;
	NBTItem nbtItem;

	/**
	 * constructor
	 */
	public PokeEggItem(AMFPokeEgg plugin)
	{
		this.plugin = plugin;

		// get type from config
		String item_material = plugin.getConfig().getString("unique.material");

		// create item
		this.pokeegg = new ItemStack(Material.getMaterial(item_material), 1);

		// store nbt of item
		this.nbtItem = new NBTItem(this.pokeegg);

		// generate a random UUID to prevent stack
		this.nbtItem.setBoolean("pokeegg-empty", true);
		this.nbtItem.setString("unique", UUID.randomUUID().toString());
		this.saveNBT();

		// parse the lore and name
		this.parseLore();
	}

	/**
	 * constructor of existing itemstack
	 */
	public PokeEggItem(ItemStack item, AMFPokeEgg plugin)
	{
		this.pokeegg = item;
		this.plugin = plugin;

		// store nbt of item
		this.nbtItem = new NBTItem(this.pokeegg);
	}

	/**
	 * save nbt
	 */
	public void saveNBT()
	{
		this.nbtItem.applyNBT(this.pokeegg);
	}

	/**
	 * get itemstack
	 */
	public ItemStack getItemStack()
	{
		return this.pokeegg;
	}
	
	/**
	 * verify if is a pokeegg looking the nbt
	 */
	public boolean isPokeEgg()
	{
		return this.nbtItem.hasTag("pokeegg-empty");
	}
	
	/**
	 * verify if pokeegg is empty
	 */
	public boolean isEmpty()
	{
		return this.nbtItem.getBoolean("pokeegg-empty");
	}

	/**
	 * catch entity
	 */
	public void catchEntity(Entity entity) 
	{
		NBTEntity nbt = new NBTEntity(entity);
		
		// store data of entity into pokeegg
		this.nbtItem.setString("pokeegg-nbt", nbt.toString());
		this.nbtItem.setString("pokeegg-type", entity.getType().toString());
		this.nbtItem.setString("pokeegg-name", entity.getName());

		// update and save nbt
		this.nbtItem.setBoolean("pokeegg-empty", false);
		this.saveNBT();
	}

	/**
	 * parse lore of pokeegg
	 */
	public void parseLore()
	{
		String item_name = this.plugin.getConfig().getString("unique.name");
		
		// if empty
		if(this.isEmpty()) {
			List<String> item_lore = this.plugin.getConfig().getStringList("empty-lore");

			// set item name
			ItemMeta meta = this.pokeegg.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + item_name));

			// set lore
			ArrayList<String> lore = new ArrayList<String>();
			for (String s : item_lore) {
				lore.add(ChatColor.translateAlternateColorCodes('&', "&f" + s));
			}
			meta.setLore(lore);
			this.pokeegg.setItemMeta(meta);
		}
		else {
			List<String> item_lore = this.plugin.getConfig().getStringList("nonempty-lore");

			// set item name
			ItemMeta meta = this.pokeegg.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + item_name));

			// get infos
			String entity_name = this.nbtItem.getString("pokeegg-name");
			String entity_type = this.nbtItem.getString("pokeegg-type");

			// set lore
			ArrayList<String> lore = new ArrayList<String>();
			for (String s : item_lore) {
				s = s.replace("%name%", entity_name);
				s = s.replace("%type%", entity_type);
				lore.add(ChatColor.translateAlternateColorCodes('&', "&f" + s));
			}

			// verify if show villager trades on config
			// verify if its a villager
				// add itens and prices

			meta.setLore(lore);
			this.pokeegg.setItemMeta(meta);

		}

		// // change lore of item (if villager, show itens to sell and profission)
		// ItemMeta meta = item.getItemMeta();
		// ArrayList<String> lore = new ArrayList<String>();
		// for (String s : this.plugin.getConfig().getStringList("unique.nonempty-lore")) {
		// 	lore.add(this.plugin.color(s));
		// }
		// meta.setLore(lore);
		// item.setItemMeta(meta);
	}


}
