package Color_yr.NBTEdit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class NBTEdit extends JavaPlugin {

    public static Plugin NBTEdit;
    public static Logger log;
    public static final String version = "1.0.0";

    @Override
    public void onEnable() {
        NBTEdit = this;
        log = getLogger();

        log.info("§d[NBTEdit]§e正在启动，感谢使用，本插件交流群：571239090");

        //org.bukkit.Bukkit.getPluginManager().registerEvents(new BukkitEvent(), this);

        Bukkit.getPluginCommand("nbt").setExecutor(new Command());
        Bukkit.getPluginCommand("nbt").setTabCompleter(new Command());

        log.info("§d[NBTEdit]§e已启动-" + version);

    }

    @Override
    public void onDisable() {
        log.info("§d[NBTEdit]§e已停止，感谢使用");
    }
}
