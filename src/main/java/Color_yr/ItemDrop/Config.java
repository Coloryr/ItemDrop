package Color_yr.ItemDrop;

import com.google.gson.Gson;

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

        } catch (Exception e) {
            ItemDrop.log.warning("§d[ItemDrop]§c配置文件 config.json 读取失败：" + e);
        }
    }
}
