package ru.flametaichou.nerfspawners;

import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

public class ConfigHelper {

	public ConfigHelper() {
		
	}
	
	public static List<String> spawnersIdList;
	public static List<String> spawnersToRemoveIdList;
	public static List<String> blacklistedMobNames;
	public static int spawnRadius;
	public static int checkRadius;
	public static double chanse;
	public static double worldID;
	public static boolean debugMode;
	
	public static void setupConfig(Configuration config) {
		try {
			config.load();
			debugMode = config.get("Settings", "debug mode", false).getBoolean(false);
			chanse = config.get("Settings", "chanse", 0.1).getDouble(0.1);
			spawnRadius = config.get("Settings", "spawn spawnRadius", 5).getInt(5);
			checkRadius = config.get("Settings", "check raduis", 25).getInt(25);
			worldID = config.get("Settings", "worldID", 0).getInt(0);
			spawnersIdList = Arrays.asList(config.getStringList("Settings", "SpawnersIdList", new String[]{"52","999"}, "ID's of blocks that spawning mobs."));
			spawnersToRemoveIdList = Arrays.asList(config.getStringList("Settings", "SpawnersToRemoveIdList", new String[]{"999"}, "ID's of blocks which must be limited in checkRadius."));
			blacklistedMobNames = Arrays.asList(config.getStringList("Settings", "BlacklistedMobNames", new String[]{"herobrine","dragon","wither","golem"}, "Names (or name parts) of blacklisted mobs that will not spawn never."));
			
		} catch(Exception e) {
			System.out.println("A severe error has occured when attempting to load the config file for this mod!");
		} finally {
			if(config.hasChanged()) {
				config.save();
			}
		}
	}

}