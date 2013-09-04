package me.G4meM0ment.RPGItem.Listener;

import me.G4meM0ment.RPGEssentials.RPGEssentials;
import me.G4meM0ment.RPGItem.Handler.CustomItem.CustomItemHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class BListener implements Listener{

	private RPGEssentials plugin;
	private CustomItemHandler customItemHandler;
	
	public BListener(RPGEssentials plugin) {
		this.plugin = plugin;
		customItemHandler = new CustomItemHandler();
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent event) {
		Player p = (Player) event.getPlayer();
		ItemMeta meta = event.getPlayer().getItemInHand().getItemMeta();
		if(p == null || meta == null) return;
		
		customItemHandler.itemUsed(customItemHandler.getCustomItem(ChatColor.stripColor(meta.getDisplayName()),
				Integer.valueOf(ChatColor.stripColor(meta.getLore().get(meta.getLore().size()-1)))));
	}
}