package Color_yr.ItemDrop;

import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class Event implements Listener {

    //检查NBT标签
    private boolean hasNBT(NBTTagCompound NBT) {
        for (String name : ItemDrop.MainConfig.getNBT()) {
            if (!NBT.hasKey(name))
                return true;
        }
        return false;
    }

    //检查物品个数
    private int haveItem(PlayerInventory inventory) {
        int count = 0;
        NBTTagCompound NBT;
        Material material;
        for (ItemStack item : inventory) {
            material = item.getType();
            if (material.equals(ItemDrop.Item)) {
                if (!ItemDrop.MainConfig.getNBT().isEmpty()) {
                    if (hasNBT(NBTRead.NBT_get(item)))
                        continue;
                }
                count += item.getAmount();
            }
        }
        return count;
    }

    //删除物品
    private void removeItem(PlayerInventory inventory, int cost) {
        Material material;
        for (ItemStack item : inventory) {
            material = item.getType();
            if (material.equals(ItemDrop.Item)) {
                if (!ItemDrop.MainConfig.getNBT().isEmpty()) {
                    if (hasNBT(NBTRead.NBT_get(item)))
                        continue;
                }
                int a = item.getAmount();
                inventory.remove(item);
                if (a == cost) {
                    return;
                } else if (a > cost) {
                    item.setAmount(a - cost);
                    inventory.addItem(item);
                    return;
                } else if (a < cost) {
                    cost -= a;
                }
                if (cost == 0)
                    return;
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void OnPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        int count = haveItem(player.getInventory());
        if (count != 0) {
            if (count >= ItemDrop.MainConfig.getNeedItem()) {
                e.setKeepInventory(true);
                removeItem(player.getInventory(), ItemDrop.MainConfig.getCostItem());
            } else {
                Random random = new Random();
                removeItem(player.getInventory(), random.nextInt(ItemDrop.MainConfig.getCostItem()));
                //随机掉落
                double chance = (double) count / (double) ItemDrop.MainConfig.getNeedItem();
                ItemStack[] items = player.getInventory().getContents();
                int removeLength = (int) (items.length * chance);
                Collections.shuffle(Arrays.asList(items));
                List<ItemStack> removeItems = new ArrayList<>();
                for (ItemStack item : items) {
                    if (removeLength != 0) {
                        removeItems.add(item);
                        removeLength--;
                    }
                }
                player.getInventory().removeItem((ItemStack[]) removeItems.toArray());
            }
        }
    }
}
