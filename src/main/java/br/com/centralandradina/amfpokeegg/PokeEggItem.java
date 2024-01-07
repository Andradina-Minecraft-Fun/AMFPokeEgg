package br.com.centralandradina.amfpokeegg;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBTCompoundList;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
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
	 * set uuid for item
	 */
	public void setRandomUUID()
	{
		this.nbtItem.setString("unique", UUID.randomUUID().toString());
		this.saveNBT();
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
		this.nbtItem.setString("pokeegg-key", entity.getType().getKey().toString());
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
			String entity_key = "entity." + this.nbtItem.getString("pokeegg-key").replace(":", ".");

			// set lore
			ArrayList<String> lore = new ArrayList<String>();
			for (String s : item_lore) {
				s = s.replace("%type%", this.plugin.langs.getTranslation(entity_key) );
				lore.add(ChatColor.translateAlternateColorCodes('&', "&f" + s));
			}

			// verify if show villager trades on config
			if(entity_type == "VILLAGER") {

				// verify if its a villager
				if(this.plugin.getConfig().getBoolean("show-villager-trades")) {

					// retrieve lore of itens
					String tradeCategoryLore = this.plugin.getConfig().getString("trade-category-lore");
					String tradeItemLore = this.plugin.getConfig().getString("trade-item-lore");
					String tradeEnchantLore = this.plugin.getConfig().getString("trade-enchant-lore");

					// add itens and prices	
					NBTContainer container = new NBTContainer(this.nbtItem.getString("pokeegg-nbt"));
					if(container.hasTag("Offers")) {
						NBTCompound offers = container.getCompound("Offers");
						NBTCompoundList nbtRecipes = offers.getCompoundList("Recipes");
						int iLores = 0;
						for(ReadWriteNBT nbtRecipe : nbtRecipes) {

							// verify first line of trades
							if(iLores == 0) {
								lore.add(this.plugin.color(tradeCategoryLore));
							}
							iLores++;

							// get final item
							ReadWriteNBT nbtSell = nbtRecipe.getCompound("sell");

							String itemKey = nbtSell.getString("id").replace(":", ".");
							int sell_amount = nbtSell.getInteger("Count");

							// translate and add to lore
							String sSellItemName = this.plugin.langs.getTranslation("item." + itemKey );
							if(sSellItemName.equals("item." + itemKey )) {
								sSellItemName = this.plugin.langs.getTranslation("block." + itemKey  );
							}
							lore.add(this.plugin.color(tradeItemLore.replace("%item%", sSellItemName)));

							// verify tag
							if(nbtSell.hasTag("tag")) {

								// loop StoredEnchantments
								ReadWriteNBTCompoundList nbtEnchants = nbtSell.getCompound("tag").getCompoundList("StoredEnchantments");
								for(ReadWriteNBT nbtEnchant : nbtEnchants) {

									String enchantKey = nbtEnchant.getString("id");
									int enchantLevel = nbtEnchant.getInteger("lvl");

									// translate and add to lore
									String sSellItemEnchantName = this.plugin.langs.getTranslation("enchantment." + enchantKey.replace(":", ".") );
									String sSellItemEnchantLevel = this.plugin.langs.getTranslation("enchantment.level." + enchantLevel );
									
									lore.add(this.plugin.color(tradeEnchantLore.replace("%item%", sSellItemName).replace("%enchant%", sSellItemEnchantName).replace("%level%", sSellItemEnchantLevel)));
								}

								// loop Enchantments
								nbtEnchants = nbtSell.getCompound("tag").getCompoundList("Enchantments");
								for(ReadWriteNBT nbtEnchant : nbtEnchants) {

									String enchantKey = nbtEnchant.getString("id");
									int enchantLevel = nbtEnchant.getInteger("lvl");

									// translate and add to lore
									String sSellItemEnchantName = this.plugin.langs.getTranslation("enchantment." + enchantKey.replace(":", ".") );
									String sSellItemEnchantLevel = this.plugin.langs.getTranslation("enchantment.level." + enchantLevel );
									
									lore.add(this.plugin.color(tradeEnchantLore.replace("%item%", sSellItemName).replace("%enchant%", sSellItemEnchantName).replace("%level%", sSellItemEnchantLevel)));
								}

							}


							
						}
					}
				}
			}

			meta.setLore(lore);
			this.pokeegg.setItemMeta(meta);

		}
	}

	/**
	 * register recipe
	 */
	public static void registerRecipe(AMFPokeEgg plugin, boolean unique)
	{
		FileConfiguration config = plugin.getConfig();

		ItemStack pokeegg = new PokeEggItem(plugin).getItemStack();

		ShapedRecipe recipe;

		if(unique) {

			// String Pos1 = config.getString("unique.craft.A") == "AIR" ? "" : "A";
			// Pos1 += config.getString("unique.craft.B") == "AIR" ? "" : "B";
			// Pos1 += config.getString("unique.craft.C") == "AIR" ? "" : "C";

			// String Pos2 = config.getString("unique.craft.D") == "AIR" ? "" : "D";
			// Pos2 += config.getString("unique.craft.E") == "AIR" ? "" : "E";
			// Pos2 += config.getString("unique.craft.F") == "AIR" ? "" : "F";

			// String Pos3 = config.getString("unique.craft.G") == "AIR" ? "" : "G";
			// Pos3 += config.getString("unique.craft.H") == "AIR" ? "" : "H";
			// Pos3 += config.getString("unique.craft.I") == "AIR" ? "" : "I";

			// recipe = new ShapedRecipe(new NamespacedKey(plugin, "pokeegg-unique"), pokeegg);
			// recipe.shape(Pos1,Pos2,Pos3);
			recipe = new ShapedRecipe(new NamespacedKey(plugin, "pokeegg-unique"), pokeegg);
			recipe.shape("ABC","DEF","GHI");

			recipe.setIngredient('A', Material.valueOf(config.getString("unique.craft.A")));
			recipe.setIngredient('B', Material.valueOf(config.getString("unique.craft.B")));
			recipe.setIngredient('C', Material.valueOf(config.getString("unique.craft.C")));
			recipe.setIngredient('D', Material.valueOf(config.getString("unique.craft.D")));
			recipe.setIngredient('E', Material.valueOf(config.getString("unique.craft.E")));
			recipe.setIngredient('F', Material.valueOf(config.getString("unique.craft.F")));
			recipe.setIngredient('G', Material.valueOf(config.getString("unique.craft.G")));
			recipe.setIngredient('H', Material.valueOf(config.getString("unique.craft.H")));
			recipe.setIngredient('I', Material.valueOf(config.getString("unique.craft.I")));
		}
		else {
			recipe = new ShapedRecipe(new NamespacedKey(plugin, "pokeegg-reusable"), pokeegg);
			recipe.shape("ABC","DEF","GHI");

			recipe.setIngredient('A', Material.valueOf(config.getString("reusable.craft.A")));
			recipe.setIngredient('B', Material.valueOf(config.getString("reusable.craft.B")));
			recipe.setIngredient('C', Material.valueOf(config.getString("reusable.craft.C")));
			recipe.setIngredient('D', Material.valueOf(config.getString("reusable.craft.D")));
			recipe.setIngredient('E', Material.valueOf(config.getString("reusable.craft.E")));
			recipe.setIngredient('F', Material.valueOf(config.getString("reusable.craft.F")));
			recipe.setIngredient('G', Material.valueOf(config.getString("reusable.craft.G")));
			recipe.setIngredient('H', Material.valueOf(config.getString("reusable.craft.H")));
			recipe.setIngredient('I', Material.valueOf(config.getString("reusable.craft.I")));
		}

		Bukkit.addRecipe(recipe);
	}



}
