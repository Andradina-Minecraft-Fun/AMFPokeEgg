package br.com.centralandradina.amfpokeegg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/**
 * class to handle commands
 */
public class CommandsExecutor implements CommandExecutor
{
	private final AMFPokeEgg plugin;

	/**
	 * constructor
	 */
	public CommandsExecutor(AMFPokeEgg plugin)
	{
        this.plugin = plugin;
    }

	/**
	 * on command
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		String commandName = cmd.getName().toLowerCase();

		// pokeegg command
		if (commandName.equals("pokeegg"))
		{
			this.plugin.getLogger().info("OK1");
			// help
			if(args.length == 0) {
				sender.sendMessage("/pokeegg give <player>");
				return false;
			}

			// give
			if(args[0].equals("give")) {

				// search for player
				Player player = this.plugin.getServer().getPlayer(args[1]);
				if(!player.isOnline()) {
					sender.sendMessage("Player not found");
					return false;
				}

				// create the PokeEgg
				PokeEggItem i = new PokeEggItem(this.plugin);

				// add item to player inventary
				player.getInventory().addItem(i);

				// finish
				player.sendMessage("PokeEgg foi dada");
				return true;
			}
		}

		return false;
	}


}
