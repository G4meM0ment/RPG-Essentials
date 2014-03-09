package me.G4meM0ment.Orbia.Commands;

import me.G4meM0ment.RPGEssentials.RPGEssentials;
import me.G4meM0ment.RPGEssentials.Commands.Command;
import me.G4meM0ment.RPGEssentials.Commands.CommandInfo;
import me.G4meM0ment.RPGEssentials.Messenger.Messenger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandInfo(
		name = "help",
		pattern = "help",
		usage = "/help",
		desc = "Zeigt das Hilfsmen�",
		permission = ""
	)
public class HelpCommand implements Command {
	@Override
	public boolean execute(RPGEssentials plugin, CommandSender sender, String... args) {
		
		Messenger.sendMessage(sender, ChatColor.DARK_PURPLE+"oOo---------------| Hilfe |---------------oOo\n"+
									  ChatColor.DARK_PURPLE+"Kommandos:\n"+
									  ChatColor.GREEN+"/g, /l, /p "+ChatColor.DARK_PURPLE+"Wechsel in den globalen, lokalen oder Gruppenchat\n"+
									  ChatColor.GREEN+"/c "+ChatColor.DARK_PURPLE+"Schalte den Kampfmodus ein/aus, du wirst nicht mehr ausversehen Gegenst�nde fallen lassen\n"+
									  ChatColor.GREEN+"/hero help "+ChatColor.DARK_PURPLE+"Zeigt dir die Kommandos f�r deine Klasse & Gruppen\n"+
									  ChatColor.GREEN+"/hero tools "+ChatColor.DARK_PURPLE+"Gegenst�nde die du verwenden kannst\n"+
									  ChatColor.GREEN+"/hero armor "+ChatColor.DARK_PURPLE+"R�stungen die du tragen kannst\n"+
									  ChatColor.GREEN+"/lvl "+ChatColor.DARK_PURPLE+"Deine EP und Level\n"+
									  ChatColor.GREEN+"/skills "+ChatColor.DARK_PURPLE+"Deine F�higkeiten\n"+
									  ChatColor.GREEN+"/cprivate "+ChatColor.DARK_PURPLE+"Eine Kiste/T�r abschlie�en\n"+
									  ChatColor.GREEN+"/cpublic "+ChatColor.DARK_PURPLE+"Kiste/T�r f�r alle Zug�nglich machen\n"+
									  ChatColor.GREEN+"/cpassword "+ChatColor.DARK_PURPLE+"Kiste/T�r mit Passwort abschlie�en\n"+
									  ChatColor.GREEN+"/cremove "+ChatColor.DARK_PURPLE+"Ein Schloss entfernen\n"+
									  ChatColor.GREEN+"/mount "+ChatColor.DARK_PURPLE+"Du steigst auf dein Pferd (wenn du eines hast)\n"+
									  ChatColor.GREEN+"/party invite <Spieler> "+ChatColor.DARK_PURPLE+"L�dt einen Spieler in deine Gruppe ein\n"+
									  ChatColor.GREEN+"/duell <Spieler> "+ChatColor.DARK_PURPLE+"L�dt einen Spieler zu einem Duell ein");
		return true;
	}
}
