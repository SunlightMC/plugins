package crystal.MythicKillCount.Commands;

import crystal.MythicKillCount.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CommandHandle implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String s,
                             @NotNull String[] args
    ) {
        //reset all mob of a player
        if (args.length >= 2 && args[0].equalsIgnoreCase("resetAll")) {
            return ResetAllHandle.handle(args);
        }

        //reset a mob
        //mythicmobcount reset [player,all] mobID
        if (args.length >= 3 && args[0].equalsIgnoreCase("reset")) {
            return ResetHandle.handle(args);
        }
        //change the mob amount of a player/all
        //mythicmobcount change [player,all] mobID value
        if(args.length >=4&& args[0].equalsIgnoreCase("change")) {
            return ChangeDataHandle.handle(args);
        }

        //Set an amount for player/all
        //mythicmobcount set [player,all] mobID value
        if(args.length >= 4 && args[0].equalsIgnoreCase("set")) {
            return SetDataHandle.handle(args);
        }


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.toString().equalsIgnoreCase("mythicmobcount"))
            //mythicmobcount arg1 arg2 arg3
            if (args.length == 0) {
                String[] firstArg = {"reset", "resetAll", "change", "set"};
                return Arrays.asList(firstArg);
            }
        if (args.length == 1) {
            // /command reset [player,all] mobID value
            String[] secondArg = {"player", "all"};
            return Arrays.asList(secondArg);
        }
        //command reset [player,all] mobID value
        if (args.length >= 2 && args[1].equalsIgnoreCase("all")) {
            return null;
        }
        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null) {
                UUID targetUUID = target.getUniqueId();
                File dataFile = EventListener.getPlayerDataFile(targetUUID);
                YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                List<String> mobList = new ArrayList<>(data.getKeys(false));
                return mobList;
            }
        }
        return null;
    }
}
