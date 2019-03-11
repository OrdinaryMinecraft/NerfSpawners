package ru.flametaichou.nerfspawners;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class MobSpawnHandler {
	
	Random random = new Random();
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onEntitySpawn(EntityJoinWorldEvent event) {
		if (event.world.isRemote) {
			return;
		}

		if (event.world.provider.dimensionId == ConfigHelper.worldID) {

			if (true) {
				int radius = ConfigHelper.spawnRadius;
				int entity_x = new Integer((int) event.entity.posX);
				int entity_y = new Integer((int) event.entity.posY);
				int entity_z = new Integer((int) event.entity.posZ);

				boolean flag = false;
				for (int x = entity_x - radius; x <= entity_x + radius; x++) {
					for (int y = entity_y - radius; y <= entity_y + radius; y++) {
						for (int z = entity_z - radius; z <= entity_z + radius; z++) {
							Block block = event.entity.worldObj.getBlock(x, y, z);
							String blockId = new Integer(Block.getIdFromBlock(block)).toString();
							if (ConfigHelper.spawnersIdList.contains(blockId)) {
								flag = true;
								break;
							}
						}
					}
				}

				if (event.entity instanceof EntityCreature) {
					if (flag) {
						writeToEntityNBT((EntityCreature) event.entity);

						boolean despawned = false;
						if (mobIsBlacklisted(event.entity.getClass().getName().toLowerCase())) {
							if (ConfigHelper.debugMode) {
								System.out.println("Cancel spawn blacklist entity: " +
										event.entity.getClass().getName() + " (" +
										event.entity.toString() + ")"
								);
							}
							event.setCanceled(true);
							despawned = true;
						}

						if (!despawned && random.nextDouble() > ConfigHelper.chanse) {
							if (ConfigHelper.debugMode) {
								System.out.println("Cancel spawn: " +
										event.entity.getClass().getName() + " (" +
										event.entity.toString() + ")"
								);
							}
							event.setCanceled(true);
						}
					}
				}
			}
		}
	}

	private void writeToEntityNBT(EntityCreature entity) {
		NBTTagCompound data = new NBTTagCompound();
		entity.writeToNBT(data);

		NBTTagCompound forgeData = new NBTTagCompound();
		forgeData.setBoolean("spawner", true);
		data.setTag("ForgeData", forgeData);

		entity.readFromNBT(data);
	}

	private boolean mobIsBlacklisted(String mobName) {
		for (String string : ConfigHelper.blacklistedMobNames) {
			if (mobName.contains(string)) {
				return true;
			}
		}
		return false;
	}
}
