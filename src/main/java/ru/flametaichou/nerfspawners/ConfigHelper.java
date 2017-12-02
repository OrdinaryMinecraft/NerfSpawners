package ru.flametaichou.nerfspawners;

import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

public class ConfigHelper {

	public ConfigHelper() {
		
	}
	
	public static List<String> spawnersIdList;
	public static int radius;
	public static double chanse;
	public static double worldID;
	
	public static void setupConfig(Configuration config) {
		try {
			config.load();
			chanse = config.get("Settings", "chanse", 0.1).getDouble(0.1);
			radius = config.get("Settings", "radius", 5).getInt(5);
			worldID = config.get("Settings", "worldID", 0).getInt(0);
			spawnersIdList = Arrays.asList(config.getStringList("Settings", "SpawnersIdList", new String[]{"52","999"}, "ID's of blocks that spawning mobs."));
			
		} catch(Exception e) {
			System.out.println("A severe error has occured when attempting to load the config file for this mod!");
		} finally {
			if(config.hasChanged()) {
				config.save();
			}
		}
	}

}