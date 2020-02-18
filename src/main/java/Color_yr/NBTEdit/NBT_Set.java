package Color_yr.NBTEdit;

import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class NBT_Set {
    public enum Type {
        Integer, Boolean, String
    }

    private static boolean isItem(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().isAir()) {
            player.sendMessage("§d[NBTEdit]§c手上物品不能为空");
            return false;
        }
        return true;
    }

    public static void AddNBT(Player player, String type, String key, String value) {
        if (!isItem(player))
            return;

        ItemStack item = player.getInventory().getItemInMainHand();
        NBTTagCompound NBT = NBT_Get.NBT_get(item);
        if (NBT.hasKey(key)) {
            player.sendMessage("§d[NBTEdit]§c该NBT标签已经存在");
            return;
        }
        Type temp;
        try {
            temp = Type.valueOf(type);
        } catch (IllegalArgumentException ignored) {
            player.sendMessage("§d[NBTEdit]§c该类型不存在");
            return;
        }
        try {
            switch (temp) {
                case Boolean:
                    NBT.setBoolean(key, Boolean.parseBoolean(value));
                    break;
                case String:
                    NBT.setString(key, value);
                    break;
                case Integer:
                    NBT.setInt(key, Integer.parseInt(value));
                    break;
            }
        } catch (NumberFormatException ignored) {
            player.sendMessage("§d[NBTEdit]§c数据不匹配");
            return;
        }
        item = NBT_Get.NBT_save(item, NBT);
        player.getInventory().setItemInMainHand(item);
        player.sendMessage("§d[NBTEdit]§2NBT标签添加成功");
    }

    public static void ListNBT(Player player) {
        if (!isItem(player))
            return;

        ItemStack item = player.getInventory().getItemInMainHand();
        NBTTagCompound NBT = NBT_Get.NBT_get(item);
        if (NBT.isEmpty()) {
            player.sendMessage("§d[NBTEdit]§c该物品没NBT");
            return;
        }
        String nbtlsit = "NBT列表：";
        for (String key : NBT.getKeys()) {
            nbtlsit += "\n" + key + ":" + NBT.get(key).toString();
        }
        player.sendMessage("§d[NBTEdit]§2" + nbtlsit);
    }

    public static void RemoveNBT(Player player, String key) {
        if (!isItem(player))
            return;
        ItemStack item = player.getInventory().getItemInMainHand();
        NBTTagCompound NBT = NBT_Get.NBT_get(item);
        if (NBT.isEmpty()) {
            player.sendMessage("§d[NBTEdit]§c该物品没NBT");
            return;
        }
        if (!NBT.hasKey(key)) {
            player.sendMessage("§d[NBTEdit]§c该NBT标签不存在");
            return;
        }
        NBT.remove(key);
        item = NBT_Get.NBT_save(item, NBT);
        player.getInventory().setItemInMainHand(item);
        player.sendMessage("§d[NBTEdit]§2NBT标签删除成功");
    }

    public static List<String> GetList(Player player)
    {
        if (!isItem(player))
            return null;
        ItemStack item = player.getInventory().getItemInMainHand();
        NBTTagCompound NBT = NBT_Get.NBT_get(item);
        if (NBT.isEmpty()) {
            return null;
        }
        return new ArrayList<>(NBT.getKeys());
    }
}
