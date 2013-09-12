package me.G4meM0ment.UnamedPortalPlugin.Handler;

import java.util.HashMap;

import me.G4meM0ment.UnamedPortalPlugin.UnnamedPortalPlugin;
import me.G4meM0ment.UnamedPortalPlugin.DataStorage.PortalData;
import me.G4meM0ment.UnamedPortalPlugin.Event.PortalEvent;
import me.G4meM0ment.UnamedPortalPlugin.Portal.Portal;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class PortalHandler {
	
	private UnnamedPortalPlugin upp;
	private PortalData portalData;
	
	private static HashMap<String, Portal> portals = new HashMap<String, Portal>();
	
	public PortalHandler(UnnamedPortalPlugin upp) {
		this.upp = upp;
	}
	
	public boolean isPortal(Block b) {
		if(b == null) return false;
		
		for(Portal p : getPortals().values()) {
			for(Block block : p.getBlocks()) {
				if(block.getLocation().distance(b.getLocation()) == 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Portal getPortal(Block b) {
		for(Portal p : getPortals().values()) {
			for(Block block : p.getBlocks()) {
				if(block.getLocation().distance(b.getLocation()) == 0)
					return p;
			}
		}
		return null;
	}
	public Portal getPortal(String id) {
		for(Portal p : getPortals().values()) {
			if(p.getID().equalsIgnoreCase(id))
				return p;
		}
		return null;
	}
	
	public HashMap<String, Portal> getPortals() {
		return portals;
	}

	public void accessedPortal(final Player p, final Portal portal) {
		if(p == null || portal == null || portal.getDestination() == null) return;
		
		final PortalEvent portalEvent = new PortalEvent(portal, p);
		Bukkit.getServer().getPluginManager().callEvent(portalEvent);
		if(!portalEvent.isCancelled()) {
			if(!upp.getConfig().getBoolean("UseOnMove")) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 240, 1000));
				Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("RPGEssentials"), new Runnable() {
					@Override
					public void run() {
						for(Block b : portal.getBlocks()) {
							if(p.getLocation().distance(b.getLocation()) < 1) {
								p.getWorld().playEffect(portalEvent.getPortal().getDestination(), Effect.POTION_BREAK, new Potion(PotionType.REGEN));
								p.teleport(portalEvent.getPortal().getDestination());
								p.getWorld().playEffect(portalEvent.getPortal().getDestination(), Effect.POTION_BREAK, new Potion(PotionType.REGEN));
							}
						}
					}
				}, 80L); 
			}
			else { 
				p.getWorld().playEffect(portalEvent.getPortal().getDestination(), Effect.POTION_BREAK, new Potion(PotionType.REGEN));
				p.teleport(portalEvent.getPortal().getDestination());
				p.getWorld().playEffect(portalEvent.getPortal().getDestination(), Effect.POTION_BREAK, new Potion(PotionType.REGEN));
			}
		}
	}
	
	public void createPortal(Portal portal) {
		if(portal == null) return;
		portalData = new PortalData();
		int counter = 1;
		for(Block b : portal.getBlocks()) {
			portalData.getConfig().set(portal.getID()+".location."+counter+".world", b.getWorld().getName());
			portalData.getConfig().set(portal.getID()+".location."+counter+".x", b.getLocation().getBlockX());
			portalData.getConfig().set(portal.getID()+".location."+counter+".y", b.getLocation().getBlockY());
			portalData.getConfig().set(portal.getID()+".location."+counter+".z", b.getLocation().getBlockZ());
			portalData.saveConfig();
			counter++;
		}
		if(portal.getDestination() != null) {
			portalData.getConfig().set(portal.getID()+".destination.world", portal.getDestination().getWorld().getName());
			portalData.getConfig().set(portal.getID()+".destination.x", portal.getDestination().getBlockX());
			portalData.getConfig().set(portal.getID()+".destination.y", portal.getDestination().getBlockY());
			portalData.getConfig().set(portal.getID()+".destination.z", portal.getDestination().getBlockZ());
			portalData.saveConfig();
		}
		getPortals().put(portal.getID(), portal);
	}
	public void removePortal(Portal portal) {
		if(portal == null) return;
		getPortals().remove(portal.getID());
		portalData.getConfig().set(portal.getID(), null);
		portalData.saveConfig();
	}
}
