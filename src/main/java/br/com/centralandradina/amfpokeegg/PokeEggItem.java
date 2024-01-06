package br.com.centralandradina.amfpokeegg;

import de.tr7zw.nbtapi.NBTItem;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * class of the PokeEgg
 */
public class PokeEggItem extends ItemStack 
{
	public PokeEggItem(AMFPokeEgg plugin)
	{
		String item_material = plugin.getConfig().getString("unique.material");
		String item_name = plugin.getConfig().getString("unique.name");

		// set material and amount
		this.setType(Material.getMaterial(item_material));
		this.setAmount(1);

		// set item name
		ItemMeta meta = this.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + item_name));

		ArrayList<String> lore = new ArrayList<String>();
		for (String s : plugin.getConfig().getStringList("unique.empty-lore")) {
			lore.add(ChatColor.translateAlternateColorCodes('&', "&f" + s));
		}
		meta.setLore(lore);
		this.setItemMeta(meta);

		// generate a random UUID to prevent stack
		NBTItem nbtItem = new NBTItem(this);
		nbtItem.setBoolean("pokeegg-empty", true);
		nbtItem.setString("unique", UUID.randomUUID().toString());
		nbtItem.applyNBT(this);
	}

}
