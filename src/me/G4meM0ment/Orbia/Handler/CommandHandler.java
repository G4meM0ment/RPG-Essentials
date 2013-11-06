package me.G4meM0ment.Orbia.Handler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.dthielke.herochat.Channel;
import com.dthielke.herochat.Herochat;

import me.G4meM0ment.Orbia.Handler.Duell.DuellHandler;
import me.G4meM0ment.Orbia.Handler.Duell.DuellState;
import me.G4meM0ment.Orbia.Tutorial.Stage;
import me.G4meM0ment.Orbia.Tutorial.TutorialHandler;
import me.G4meM0ment.RPGEssentials.RPGEssentials;

public class CommandHandler {
	
	private RPGEssentials plugin;
	private TutorialHandler tutHandler;
	private CMHandler cmh;
	private DuellHandler dh;
	
	public CommandHandler(RPGEssentials plugin) {
		this.plugin = plugin;
		tutHandler = new TutorialHandler();
		cmh = new CMHandler();
		dh = new DuellHandler(plugin);
	}
	
	public boolean exec(CommandSender sender, Command command, String label, String[] args) 
	{
		if(sender instanceof Player && command.getName().equalsIgnoreCase("l")) {
			plugin.getHerochat();
			if(plugin.getHerochat() == null) return false;
			Player p = (Player) sender;
			Channel c = Herochat.getChannelManager().getChannel("Lokal");
			if(args.length == 0) {
				if(Herochat.getChatterManager().getChatter(p).getActiveChannel() == c) 
				{
					p.sendMessage(ChatColor.YELLOW+"Du redest bereits im Lokalen Kanal");
					return true;
				}
				Herochat.getChatterManager().getChatter(p).addChannel(c, false, false);
				Herochat.getChatterManager().getChatter((Player)sender).setActiveChannel(c , false, false);
				p.sendMessage(ChatColor.YELLOW+"Du redest jetzt im Lokalen Kanal");
				return true;
			} else 
			{
				p.chat("/&8l "+getMessage(args));
				return true;
			}
			
		}
		
		if(sender instanceof Player && command.getName().equalsIgnoreCase("g")) {
			plugin.getHerochat();
			if(plugin.getHerochat() == null) return false;
			Player p = (Player) sender;
			Channel c = Herochat.getChannelManager().getChannel("Global");
			if(args.length == 0) {
				if(Herochat.getChatterManager().getChatter(p).getActiveChannel() == c) 
				{
					p.sendMessage(ChatColor.YELLOW+"Du redest bereits im Globalen Kanal");
					return true;
				}
				Herochat.getChatterManager().getChatter(p).addChannel(c, false, false);
				Herochat.getChatterManager().getChatter(p).setActiveChannel(c , false, false);
				p.sendMessage(ChatColor.YELLOW+"Du redest jetzt im Globalen Kanal");
				return true;
			} else 
			{
				p.chat("/&2g "+getMessage(args));
				return true;
			}
		}
		
		if(sender instanceof Player && command.getName().equalsIgnoreCase("c")) {
			Player p = (Player) sender;
			cmh.toggleCombatMode(p);
			if(cmh.isInCombatMode(p))
				p.sendMessage(ChatColor.GRAY+"Du bist jetzt im Kampfmodus!");
			else
				p.sendMessage(ChatColor.GRAY+"Du hast den Kampfmodus verlassen!");
			//TODO add messenger
			return true;
		}
		
		if(sender instanceof Player && command.getName().equalsIgnoreCase("duell") && args.length > 0) {
			Player p = (Player) sender;
			Player r = null;
			
			if(args[0].equalsIgnoreCase("accept") && args.length >= 2)
			{
				r = Bukkit.getPlayer(args[1]);
				if(r == null)
				{
					//TODO add messenger
					p.sendMessage(ChatColor.GRAY+"Spieler nicht gefunden!");
					return true;
				}
				
				if(!dh.isInDuell(p, true) && !dh.isInDuell(r, true) && dh.getDuellState(p) == DuellState.REQUEST && dh.getDuellState(r) == DuellState.REQUEST)
				{
					dh.initDuell(p.getName(), true);
					p.sendMessage(ChatColor.DARK_RED+"Duell gestartet mit "+r.getName());
					r.sendMessage(ChatColor.DARK_RED+"Duell gestartet mit "+p.getName());
					return true;
				}
			}
			else
			{
				r = Bukkit.getPlayer(args[0]);
				if(r == null)
				{
					//TODO add messenger
					p.sendMessage(ChatColor.GRAY+"Spieler nicht gefunden!");
					return true;
				}
				
				if(!dh.isInDuell(p, true) && !dh.isInDuell(r, true))
				{
					dh.initDuellRequest(p, r, true);
					p.sendMessage(ChatColor.GRAY+"Anfrage versendet");
					r.sendMessage(ChatColor.GRAY+"Duell Anfrage von "+p.getName()+" Tippe /duell accept "+p.getName()+" um zu aktzeptieren");
					return true;
				}
			}
		}
		
		if(args.length == 3 && args[0].equalsIgnoreCase("setStage") && command.getName().equalsIgnoreCase("orbia") && sender.hasPermission("orbia.admin"))
		{
			tutHandler.setStage(getPlayer(args[1]), Stage.valueOf(args[2]));
			return true;
		}	
		
		if(command.getName().equalsIgnoreCase("job") && sender instanceof Player)
		{
			Player p = (Player) sender;
			
			String job = "";
			if(p.hasPermission("orbia.job.miner"))
			{
				job = "Minenarbeiter";
			}
			else if(p.hasPermission("orbia.job.alchemist"))
			{
				job = "Alchemist";
			}
			else if(p.hasPermission("orbia.job.farmer"))
			{
				job = "Bauer";
			}
			else
			{
				p.sendMessage(ChatColor.RED+"Du hast keinen Beruf!");
				return true;
			}
			
			p.sendMessage(ChatColor.AQUA+"+-------------:| Beruf |:-------------+");
			p.sendMessage(ChatColor.AQUA+"Du hast den Beruf des "+job);
			return true;
		}
		
		return false;
	}
	
	private String getMessage(String[] args) {
		String msg = "";
		for(String s : args)
		{
			msg += s+" ";
		}
		return msg;
	}
	
    public Player getPlayer(final String name) {
        Player[] players = Bukkit.getOnlinePlayers();
 
        Player found = null;
        String lowerName = name.toLowerCase();
        int delta = Integer.MAX_VALUE;
        for (Player player : players) {
            if (player.getName().toLowerCase().startsWith(lowerName)) {
                int curDelta = player.getName().length() - lowerName.length();
                if (curDelta < delta) {
                    found = player;
                    delta = curDelta;
                    break;
                }
                if (curDelta == 0) break;
            }

        }
        return found;
    }
}
