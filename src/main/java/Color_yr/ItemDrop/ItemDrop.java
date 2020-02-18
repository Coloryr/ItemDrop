package Color_yr.ItemDrop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ItemDrop extends JavaPlugin {

    public static Plugin ItemDrop;
    public static Logger log;
    public static final String version = "1.0.0";
    public static ConfigOBJ MainConfig;

    public static Material Item = Material.STONE;

    @Override
    public void onEnable() {
        ItemDrop = this;
        log = getLogger();

        log.info("§d[ItemDrop]§e正在启动，感谢使用，本插件交流群：571239090");

        new Config();

        Bukkit.getPluginManager().registerEvents(new Event(), this);

        try {
            Material material = Material.getMaterial(MainConfig.getItem());
            if (material == null) {
                log.warning("§d[ItemDrop]§c没有找到物品：" + MainConfig.getItem());
            } else {
                Item = material;
            }
        } catch (Exception e) {
            log.warning("§d[ItemDrop]§c发生错误");
            e.printStackTrace();
        }

        Bukkit.getPluginCommand("item").setExecutor(new Command());
        Bukkit.getPluginCommand("item").setTabCompleter(new Command());

        log.info("§d[ItemDrop]§e已启动-" + version);

    }

    @Override
    public void onDisable() {
        log.info("§d[ItemDrop]§e已停止，感谢使用");
    }
}
