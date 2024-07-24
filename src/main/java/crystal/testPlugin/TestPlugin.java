package crystal.testPlugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public final class TestPlugin extends JavaPlugin implements Listener {
    public FileConfiguration myFileConfig;


    @Override
    public void onEnable() {
        // Plugin startup logic
        //Setup Folder
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        try {
            createPlayerConfig(uuid);
        } catch (Exception exception){
            this.getLogger().warning(exception.getMessage());
        }
    }
    public void createPlayerConfig(UUID uuid) throws IOException {
        String fileName = String.valueOf(uuid) + ".yml";
        File playerFile = new File(getDataFolder(), fileName);
        if(!playerFile.exists()) {
            if (playerFile.createNewFile()){
                //Log: New file created
            } else {
                //Log: Can not create new file
            };
        }
    }
    
    
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
