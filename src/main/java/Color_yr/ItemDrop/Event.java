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
        Material material;
        for (ItemStack item : inventory) {
            if (item == null)
                continue;
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
    private List<ItemStack> removeItem(List<ItemStack> inventory, int cost) {
        Material material;
        List<ItemStack> items = new ArrayList<>(inventory);
        for (ItemStack item : inventory) {
            if (item == null)
                continue;
            material = item.getType();
            if (material.equals(ItemDrop.Item)) {
                if (!ItemDrop.MainConfig.getNBT().isEmpty()) {
                    if (hasNBT(NBTRead.NBT_get(item)))
                        continue;
                }
                int a = item.getAmount();
                items.remove(item);
                if (a == cost) {
                    return items;
                } else if (a > cost) {
                    item.setAmount(a - cost);
                    items.add(item);
                    return items;
                } else {
                    cost -= a;
                }
                if (cost == 0)
                    return items;
            }
        }
        return items;
    }

    private List<ItemStack> getAllItem(PlayerInventory inventory)
    {
        List<ItemStack> items = new ArrayList<>();
        for (ItemStack item : inventory) {
            if (item == null)
                continue;
            items.add(item);
        }
        return items;
    }

    private ItemStack[] ListTOArray(List<ItemStack> items)
    {
        ItemStack[] array = new ItemStack[items.size()];
        for (int i = 0; i < items.size(); i++) {
            array[i] = items.get(i);
        }
        return array;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void OnPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        if (ItemDrop.MainConfig.isWorld(player.getWorld().getName())) {
            int count = haveItem(player.getInventory());
            if (count != 0) {
                if (count >= ItemDrop.MainConfig.getNeedItem()) {
                    List<ItemStack> temps = removeItem(getAllItem(player.getInventory()), ItemDrop.MainConfig.getCostItem());
                    player.getInventory().clear();
                    player.getInventory().addItem(ListTOArray(temps));
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                } else {
                    //随机掉落
                    Random random = new Random();
                    double chance = (double) count / (double) ItemDrop.MainConfig.getNeedItem();
                    //清空物品栏
                    player.getInventory().clear();
                    //获取所有掉落的物品
                    List<ItemStack> dropItems = new ArrayList<>(e.getDrops());
                    dropItems = removeItem(dropItems, random.nextInt(ItemDrop.MainConfig.getCostItem()));
                    //保留的物品
                    List<ItemStack> saveItems = new ArrayList<>();
                    //删除掉落石物品
                    Material material;
                    for (ItemStack temp : e.getDrops()) {
                        material = temp.getType();
                        if (material.equals(ItemDrop.Item)) {
                            if (!ItemDrop.MainConfig.getNBT().isEmpty()) {
                                if (hasNBT(NBTRead.NBT_get(temp)))
                                    continue;
                            }
                            dropItems.remove(temp);
                            saveItems.add(temp);
                        }
                    }
                    //掉落物数量
                    int removeLength = (int) (dropItems.size() * chance);
                    //打乱
                    Collections.shuffle(dropItems);
                    //获取保留的物品
                    for (ItemStack item : new ArrayList<>(dropItems)) {
                        if (removeLength != 0) {
                            saveItems.add(item);
                            removeLength--;
                        }
                    }
                    //添加保留物品
                    player.getInventory().addItem(ListTOArray(saveItems));
                    //删除保留的物品
                    e.getDrops().removeAll(saveItems);
                    e.setKeepInventory(true);
                }
            }
        } else {
            e.setKeepInventory(true);
            e.getDrops().clear();
        }
    }
}
