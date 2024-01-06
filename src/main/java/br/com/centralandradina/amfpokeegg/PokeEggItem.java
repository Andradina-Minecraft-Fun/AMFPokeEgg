package br.com.centralandradina.amfpokeegg;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.nbtapi.NBTItem;

public class PokeEggItem extends ItemStack {
	public PokeEggItem(JavaPlugin plugin) {
		String item_material = plugin.getConfig().getString("unique.material");
		String item_name = plugin.getConfig().getString("unique.name");
		// ArrayList<String> lore = (ArrayList<String>) ;
		// String lore = ChatColor.translateAlternateColorCodes('&',
		// plugin.getConfig().getString("Swords.lore"));

		// set material and amount
		this.setType(Material.getMaterial(item_material));
		this.setAmount(1);

		// set item name
		ItemMeta meta = this.getItemMeta();
		meta.setDisplayName(item_name);

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
