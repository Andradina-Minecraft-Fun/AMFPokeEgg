package br.com.centralandradina.amfpokeegg;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTEntity;

public class ReleaseListener implements Listener 
{
	private final JavaPlugin plugin;
	String teste = "{AbsorptionAmount:0.0f,Age:0,AgeLocked:0b,Air:300s,ArmorDropChances:[0.085f,0.085f,0.085f,0.085f],ArmorItems:[{},{},{},{}],Attributes:[{Base:48.0d,Modifiers:[{Amount:-0.020560552334544256d,Name:\"Random spawn bonus\",Operation:1,UUID:[I;-2077135868,1086148382,-1628225283,2143175941]}],Name:\"minecraft:generic.follow_range\"},{Base:0.5d,Name:\"minecraft:generic.movement_speed\"},{Base:20.0d,Name:\"minecraft:generic.max_health\"}],Brain:{memories:{\"minecraft:job_site\":{value:{dimension:\"minecraft:overworld\",pos:[I;-504,56,778]}},\"minecraft:last_worked_at_poi\":{value:6318485L}}},Bukkit.Aware:1b,Bukkit.updateLevel:2,CanPickUpLoot:1b,DeathTime:0s,FallDistance:0.0f,FallFlying:0b,Fire:-1s,FoodLevel:0b,ForcedAge:0,Gossips:[{Target:[I;-770492215,102513597,-1295815409,-1094103002],Type:\"trading\",Value:4}],HandDropChances:[0.085f,0.085f],HandItems:[{},{}],Health:20.0f,HurtByTimestamp:0,HurtTime:0s,Inventory:[],Invulnerable:0b,LastGossipDecay:6318401L,LastRestock:6318485L,LeftHanded:0b,Motion:[0.0d,-0.0784000015258789d,0.0d],Offers:{Recipes:[{Paper.IgnoreDiscounts:0b,buy:{Count:24b,id:\"minecraft:paper\"},buyB:{Count:0b,id:\"minecraft:air\"},demand:0,maxUses:16,priceMultiplier:0.05f,rewardExp:1b,sell:{Count:1b,id:\"minecraft:emerald\"},specialPrice:0,uses:0,xp:2},{Paper.IgnoreDiscounts:0b,buy:{Count:9b,id:\"minecraft:emerald\"},buyB:{Count:0b,id:\"minecraft:air\"},demand:0,maxUses:12,priceMultiplier:0.05f,rewardExp:1b,sell:{Count:1b,id:\"minecraft:bookshelf\"},specialPrice:0,uses:12,xp:1},{Paper.IgnoreDiscounts:0b,buy:{Count:1b,id:\"minecraft:emerald\"},buyB:{Count:0b,id:\"minecraft:air\"},demand:0,maxUses:12,priceMultiplier:0.05f,rewardExp:1b,sell:{Count:1b,id:\"minecraft:lantern\"},specialPrice:0,uses:0,xp:5},{Paper.IgnoreDiscounts:0b,buy:{Count:8b,id:\"minecraft:emerald\"},buyB:{Count:1b,id:\"minecraft:book\"},demand:0,maxUses:12,priceMultiplier:0.2f,rewardExp:1b,sell:{Count:1b,id:\"minecraft:enchanted_book\",tag:{StoredEnchantments:[{id:\"minecraft:multishot\",lvl:1s}]}},specialPrice:0,uses:0,xp:5}]},OnGround:1b,Paper.Origin:[-502.5d,56.0d,777.5d],Paper.OriginWorld:[I;-843697322,-900314285,-2100878294,1392142994],Paper.SpawnReason:\"SPAWNER_EGG\",PersistenceRequired:0b,PortalCooldown:0,Pos:[-501.72380884784775d,56.0d,778.4559214415559d],RestocksToday:0,Rotation:[276.35983f,-40.0f],Spigot.ticksLived:328,UUID:[I;1722861249,916868382,-1250541527,-1431509221],VillagerData:{level:2,profession:\"minecraft:librarian\",type:\"minecraft:plains\"},WorldUUIDLeast:-9023203564214130030L,WorldUUIDMost:-3623652402318128301L,Xp:12,active_effects:[{ambient:0b,amplifier:0b,duration:192,id:\"minecraft:regeneration\",show_icon:1b,show_particles:1b}]}";

	public ReleaseListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }


	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		// Player player = event.getPlayer();

		// // recupera o item
		// ItemStack item = event.getItem();
		// if((item == null) || (item.getAmount() == 0) || (item.getType() == Material.AIR)) {
		// 	return;
		// }

		// if(item.getType().equals(Material.CAT_SPAWN_EGG)) {
		// 	player.sendMessage("SIM");


		// 	// cancela o evento
		// 	event.setCancelled(true);

		// 	// recupera o local
		// 	Location location = event.getInteractionPoint() == null ? event.getPlayer().getLocation() : event.getInteractionPoint();

		// 	// olhar a face clicada, e por o a menos, para spawnar do lado ou em cima do bloco clicado

		// 	// cria o container nbt
		// 	NBTContainer container = new NBTContainer(teste);

		// 	removeKeys(container, "Pos", "Paper.Origin", "FallFlying", "Motion", "UUID");

		// 	// cria o entity
		// 	player.getWorld().spawnEntity(location, EntityType.VILLAGER, CreatureSpawnEvent.SpawnReason.CUSTOM, entity -> {
		// 		NBTEntity nbtEntity = new NBTEntity(entity);
		// 		nbtEntity.mergeCompound(container);
		// 	});
			
		// }
	}

	private void removeKeys(NBTContainer container, String... keys) {
        Arrays.stream(keys).forEach(container::removeKey);
    }
}
