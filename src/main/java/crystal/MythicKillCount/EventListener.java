package crystal.MythicKillCount;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class EventListener implements Listener {
    
    @EventHandler
    public void onMobKill(EntityDeathEvent e) throws IOException {
        //defining basic;
        Player killer = e.getEntity().getKiller();
        LivingEntity killed = e.getEntity();
        //check if killer is player
        UUID uuid;
        if (killer != null) {
            //get player uuid
            uuid = killer.getUniqueId();
        } else return;
        //check if mythic mod
        ActiveMob activeMob = MythicBukkit.inst().getMobManager().getActiveMob(killed.getUniqueId()).orElse(null);
        File dataFile = getPlayerDataFile(uuid);
        YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
        
        //Get index. Vanilla mob name or internal id of mythicmobs
        String index;
        if (activeMob != null){
            index = activeMob.getMobType();
        }else{
            index = killed.getType().name();
        }
        
        //Add data to file
        int oldValue = data.getInt(index);
        data.set(index, oldValue + 1);
        data.save(dataFile);
    }
    public File getPlayerDataFile(UUID uuid){
        String fileName = String.valueOf(uuid) + ".yml";
        File playerFile = new File(Bukkit.getPluginsFolder(), fileName);
        //check if file already exists
        if(!playerFile.exists()) {
            try {
                if (playerFile.createNewFile()) {
                    Bukkit.getLogger().info("New file created");
                } else {
                    Bukkit.getLogger().info("Can not create new file");
                }
                
            } catch(Exception exception){
                exception.printStackTrace();
            }
        }
        return playerFile;
    }
}
