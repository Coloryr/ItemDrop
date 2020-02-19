package Color_yr.ItemDrop;

import com.google.gson.Gson;
import org.bukkit.Material;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Config {
    public Config() {
        try {
            File file = new File(ItemDrop.ItemDrop.getDataFolder().getParent() + "/ItemDrop/config.json");
            if (!ItemDrop.ItemDrop.getDataFolder().exists())
                ItemDrop.ItemDrop.getDataFolder().mkdirs();
            if (!file.exists()) {
                try (InputStream in = ItemDrop.ItemDrop.getResource("config.json")) {
                    Files.copy(in, file.toPath());
                } catch (IOException e) {
                    ItemDrop.log.warning("§d[ItemDrop]§c配置文件 config.json 创建失败：" + e);
                }
            }
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            BufferedReader bf = new BufferedReader(reader);
            ItemDrop.MainConfig = new Gson().fromJson(bf, ConfigOBJ.class);
            if (ItemDrop.MainConfig == null) {
                ItemDrop.MainConfig = new ConfigOBJ();
            }
            try {
                Material material = Material.getMaterial(ItemDrop.MainConfig.getItem());
                if (material == null) {
                    ItemDrop.log.warning("§d[ItemDrop]§c没有找到物品：" + ItemDrop.MainConfig.getItem());
                } else {
                    ItemDrop.Item = material;
                }
            } catch (Exception e) {
                ItemDrop.log.warning("§d[ItemDrop]§c发生错误");
                e.printStackTrace();
            }
        } catch (Exception e) {
            ItemDrop.log.warning("§d[ItemDrop]§c配置文件 config.json 读取失败：" + e);
        }
    }
}
