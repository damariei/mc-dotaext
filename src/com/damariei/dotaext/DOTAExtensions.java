package com.damariei.dotaext;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class DOTAExtensions extends JavaPlugin implements Listener {
	
	Logger log;
	
	/* Tower Locations */
	ArrayList<Location> redTowerLocations = new ArrayList<Location>();
	ArrayList<Location> blueTowerLocations = new ArrayList<Location>();
	Location redNexusLoc = new Location(null, -1159, 55, 410);
	Location blueNexusLoc = new Location(null, -976, 54, 225);
	
	/* Towers Destroyed Counters */
	int redTowersDestroyed;
	int blueTowersDestroyed;
	
	/* Plugin Start */
	public void onEnable(){ 
		log = this.getLogger();
		
		redTowersDestroyed = 0;
		blueTowersDestroyed = 0;
		loadRedTowerLocs();
		loadBlueTowerLocs();
		
		getServer().getPluginManager().registerEvents(this, this);
		
		log.info("DOTA Extensions have been enabled.");
	}
	
	private void loadRedTowerLocs() {
		redTowerLocations.add(new Location(null, -1189, 53, 347));
		redTowerLocations.add(new Location(null, -1190, 53, 257));
		redTowerLocations.add(new Location(null, -1089, 53, 458));
		redTowerLocations.add(new Location(null, -1001, 53, 448));
		redTowerLocations.add(new Location(null, -1091, 54, 339));
	}
	
	private void loadBlueTowerLocs() {
		blueTowerLocations.add(new Location(null, -1046, 53, 184));
		blueTowerLocations.add(new Location(null, -1134, 53, 194));
		blueTowerLocations.add(new Location(null, -939, 53, 288));
		blueTowerLocations.add(new Location(null, -938, 53, 378));
		blueTowerLocations.add(new Location(null, -1044, 54, 296));
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		
		// Check if a TNT has exploded at a know Tower location
		if (event.getEntityType()==EntityType.PRIMED_TNT) {
			
			Location eventLoc = event.getLocation();
			
			//RED Towers
			for (Location loc : redTowerLocations) {
				if (eventLoc.getBlockX()==loc.getBlockX()&&
					eventLoc.getBlockY()==loc.getBlockY()&&
					eventLoc.getBlockZ()==loc.getBlockZ()) {
					this.getServer().broadcastMessage(ChatColor.YELLOW + "A "+ ChatColor.RED + "RED" + ChatColor.YELLOW + " Tower Has Been Destroyed!");
					redTowersDestroyed++;
					
					// Check if all towers are destroyed
					if (redTowersDestroyed==5) {
						this.getServer().broadcastMessage(ChatColor.YELLOW + "All "+ ChatColor.RED + "RED" + ChatColor.YELLOW + " Towers Are Destroyed! Defend the NEXUS!");
						redTowersDestroyed++;
					}
				}
			}
			
			//BLUE Towers
			for (Location loc : blueTowerLocations) {
				if (eventLoc.getBlockX()==loc.getBlockX()&&
					eventLoc.getBlockY()==loc.getBlockY()&&
					eventLoc.getBlockZ()==loc.getBlockZ()) {
					this.getServer().broadcastMessage(ChatColor.YELLOW + "A "+ ChatColor.BLUE + "BLUE" + ChatColor.YELLOW + " Tower Has Been Destroyed!");
					blueTowersDestroyed++;
					
					// Check if all towers are destroyed
					if (blueTowersDestroyed==5) {
						this.getServer().broadcastMessage(ChatColor.YELLOW + "All "+ ChatColor.BLUE + "BLUE" + ChatColor.YELLOW + " Towers Are Destroyed! Defend the NEXUS!");
						blueTowersDestroyed++;
					}
				}
			}
			
			//NEXUS
			if (eventLoc.getBlockX()==redNexusLoc.getBlockX()&&
					eventLoc.getBlockY()==redNexusLoc.getBlockY()&&
					eventLoc.getBlockZ()==redNexusLoc.getBlockZ()) {
					this.getServer().broadcastMessage(ChatColor.RED + "The RED NEXUS Has Been Destroyed!" + ChatColor.BLUE + " BLUE WINS!");
			} else if (eventLoc.getBlockX()==blueNexusLoc.getBlockX()&&
					eventLoc.getBlockY()==blueNexusLoc.getBlockY()&&
					eventLoc.getBlockZ()==blueNexusLoc.getBlockZ()) {
					this.getServer().broadcastMessage(ChatColor.BLUE + "The BLUE NEXUS Has Been Destroyed!" + ChatColor.RED + " RED WINS!");
			}
			
		}
		
	}
	
	/* Plugin Stop */
	public void onDisable(){ 
		log.info("DOTA Extensions have been disabled.");
	}
	
}
