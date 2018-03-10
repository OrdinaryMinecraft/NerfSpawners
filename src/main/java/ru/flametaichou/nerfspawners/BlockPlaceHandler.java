package ru.flametaichou.nerfspawners;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;


public class BlockPlaceHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && !event.world.isRemote)
            if (event.entityPlayer.inventory.getCurrentItem() != null) {
                ItemStack itemStack = event.entityPlayer.inventory.getCurrentItem();
                String itemId = new Integer(Item.getIdFromItem(itemStack.getItem())).toString();
                if (itemStack != null && ConfigHelper.spawnersToRemoveIdList.contains(itemId)) {

                    int radius = ConfigHelper.checkRadius;
                    int block_x = event.x;
                    int block_y = event.y;
                    int block_z = event.z;

                    boolean flag = false;
                    for (int x = block_x - radius; x <= block_x + radius; x++) {
                        for (int y = block_y - radius; y <= block_y + radius; y++) {
                            for (int z = block_z - radius; z <= block_z + radius; z++) {
                                Block block = event.world.getBlock(x, y, z);
                                String blockId = new Integer(Block.getIdFromBlock(block)).toString();
                                if (ConfigHelper.spawnersToRemoveIdList.contains(blockId)) {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (flag) {
                        event.entityPlayer.addChatComponentMessage(new ChatComponentTranslation("spawner.place.deny", ConfigHelper.checkRadius));
                        event.setCanceled(true);
                    }
                }
            }
    }
}
