package crystal.MythicKillCount;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
public final class Main extends JavaPlugin implements Listener {

    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }
    
    
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
