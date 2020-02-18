package Color_yr.ItemDrop;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class Command implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("item")) {
            if (!sender.hasPermission("ItemDrop.admin")) {
                sender.sendMessage("§d[ItemDrop]§c你没有使用该指令的权限");
                return true;
            } else if (args.length == 0) {
                sender.sendMessage("§d[ItemDrop]§c参数错误，请输入/item help");
                return true;
            } else if (args[0].equalsIgnoreCase("reload")) {
                new Config();
                sender.sendMessage("§d[ItemDrop]§b参配置文件重载");
                return true;
            } else if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("§d[ItemDrop]§b帮助菜单");
                sender.sendMessage("§d[ItemDrop]§b/item reload 重读文件");
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        if (sender.hasPermission("ItemDrop.admin")) {
            List<String> temp = new ArrayList<>();
            temp.add("help");
            temp.add("reload");
            return temp;
        }
        return null;
    }
}
