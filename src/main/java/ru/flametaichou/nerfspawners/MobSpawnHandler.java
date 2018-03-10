package ru.flametaichou.nerfspawners;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;

public class MobSpawnHandler {
	
	Random random = new Random();
	
	@SubscribeEvent
	public void onEntitySpawn(EntityJoinWorldEvent event) {
		if (event.world.provider.dimensionId == ConfigHelper.worldID) {

			int radius = ConfigHelper.spawnRadius;
			int entity_x = (int) event.entity.posX;
			int entity_y = (int) event.entity.posY;
			int entity_z = (int) event.entity.posZ;
			
			boolean flag = false;
			for (int x = entity_x - radius; x <= entity_x + radius; x++) {
				for (int y = entity_y - radius; y <= entity_y + radius; y++) {
					for (int z = entity_z - radius; z <= entity_z + radius; z++) {
						Block block = event.world.getBlock(x, y, z);
						String blockId = new Integer(Block.getIdFromBlock(block)).toString();
						if (ConfigHelper.spawnersIdList.contains(blockId)) {
							flag = true;
							break;
						}
					}
				}
			}
			
			//if(!event.entity.getClass().equals(EntityPlayer.class)){
			if(event.entity instanceof EntityCreature) {
	        	if (flag) {
					if (ConfigHelper.debugMode) {
						System.out.println("Spawning: " + event.entity.toString());
					}
	        		if (random.nextDouble() > ConfigHelper.chanse) {
						if (ConfigHelper.debugMode) {
							System.out.println("Cancel spawn: " + event.entity.toString());
						}
						event.setCanceled(true);
					}
	        	}
			}
		}
	}	
}
