package me.G4meM0ment.Orbia.Tutorial;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TutorialHandler {
	private TutorialData tutData;
	
	public TutorialHandler() 
	{
		tutData = new TutorialData();
	}
	
	public boolean finishedTutorial(Player p) 
	{
		if(p.hasPermission("orbia.tutorial.finished"))
		{
			if(!hasStage(p))
				setStage(p, Stage.FIRST);
			return true;
		}
		if(!hasStage(p))
		{
			System.out.println("Debug: Player has no stage");
			setStage(p, Stage.FIRST);
			return false;
		}
		return getStage(p) == Stage.FINISHED;
	}
	
	public boolean hasStage(Player p) 
	{
		if(tutData.getConfig().getKeys(false).contains(p.getName()))
			return true;
		else
			return false;
	}
	public Stage getStage(Player p) 
	{
		if(!hasStage(p))
			setStage(p, Stage.FIRST);
		return Stage.valueOf(tutData.getConfig().getString(p.getName()));
	}
	public void setStage(Player p, Stage s) 
	{
		if(p == null || s == null) return;
		tutData.getConfig().set(p.getName(), s.toString());
		tutData.saveConfig();
	}
	
	public void startStage(Player p, Stage s)
	{
		switch(s)
		{
		case FIRST:
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "campaign forceadd "+p.getName()+" tutorial");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "campaign forceready "+p.getName());
			break;
		case SECOND:
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "campaign forceadd "+p.getName()+" tutorial2");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "campaign forceready "+p.getName());
			break;
		case THIRD:
			p.teleport(new Location(Bukkit.getWorld("OrbiA"), -2268.0, 98.0, -3436.0));
			p.sendMessage(ChatColor.GREEN+"Betrete das Dungeon �ber der Kaserne in Angin! (x: -2167 y: 145 z: -3529)");
			break;
		case FOURTH:
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "campaign forceadd "+p.getName()+" tutorial3");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "campaign forceready "+p.getName());
		case FITH:
			p.teleport(new Location(Bukkit.getWorld("OrbiA"), -2433.0, 202.0, -3659.0));
			p.sendMessage(ChatColor.GREEN+"W�hle eine Klasse bei einem der Graub�rte (Rechtsklick auf den Graubart! Gehe dann zur�ck zu Gondolf und rede mit ihm!");
		default:
			break;
		}
	}
}
