package crystal.MythicKillCount;

import com.mojang.brigadier.Command;
import crystal.MythicKillCount.Commands.CommandHandle;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class MythicKillCount extends JavaPlugin implements Listener {

    public void onEnable() {
        // Plugin startup logic

        //Register Event
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        //Register Commands
        PluginCommand commandRegister = getCommand("mythickillcount");
        if (commandRegister != null) {
            commandRegister.setExecutor(new CommandHandle());
        } else {
            this.getLogger().warning("Can not register commands!");
        }

        //Register Placeholder
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderHook().register();
        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
