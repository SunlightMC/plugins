package crystal.MythicKillCount;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
public final class MythicKillCount extends JavaPlugin implements Listener {

    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderHook().register();
        }
    }
    
    
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}