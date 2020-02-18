package Color_yr.NBTEdit;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Command implements CommandExecutor, TabExecutor {

    private boolean isConsole(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§d[NBTEdit]§c控制台不能使用这条命令");
            return true;
        }
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("nbt")) {
            if (!sender.hasPermission("NBTEdit.admin")) {
                sender.sendMessage("§d[NBTEdit]§c你没有权限使用该指令");
                return true;
            } else if (args.length == 0) {
                sender.sendMessage("§d[NBTEdit]§c错误，请使用/nbt help 获取帮助");
                return true;
            } else if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("§d[NBTEdit]§2帮助手册");
                sender.sendMessage("§d[NBTEdit]§2使用/nbt add [type] [nbtname] [value] 来添加NBT标签");
                sender.sendMessage("§d[NBTEdit]§2使用/nbt list 来重读物品NBT标签列表");
                sender.sendMessage("§d[NBTEdit]§2使用/nbt set [nbtname] [value] 来设置NBT标签");
                sender.sendMessage("§d[NBTEdit]§2使用/nbt remove [nbtname] 来删除NBT标签");
                return true;
            } else if (args[0].equalsIgnoreCase("add")) {
                if (isConsole(sender))
                    return true;
                if (args.length != 4) {
                    sender.sendMessage("§d[NBTEdit]§c参数不足，使用/nbt help来获取帮助");
                    return true;
                }
                NBT_Set.AddNBT((Player) sender, args[1], args[2], args[3]);
                return true;
            } else if (args[0].equalsIgnoreCase("list")) {
                if (isConsole(sender))
                    return true;
                NBT_Set.ListNBT((Player) sender);
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (isConsole(sender))
                    return true;
                if (args.length != 2) {
                    sender.sendMessage("§d[NBTEdit]§c参数不足，使用/nbt help来获取帮助");
                    return true;
                }
                NBT_Set.RemoveNBT((Player) sender, args[1]);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("nbt") && sender.hasPermission("NBTEdit.admin")) {
            List<String> arguments = new ArrayList<>();
            if (args.length == 1) {
                arguments.add("add");
                arguments.add("list");
                arguments.add("remove");
            } else if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
                for (NBT_Set.Type type : NBT_Set.Type.values()) {
                    arguments.add(type.name());
                }
            } else if (args.length == 4 && args[1].equalsIgnoreCase("Boolean")) {
                arguments.add("true");
                arguments.add("false");
            } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
                return NBT_Set.GetList((Player) sender);
            }
            return arguments;
        }
        return null;
    }
}
