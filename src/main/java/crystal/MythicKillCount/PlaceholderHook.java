package crystal.MythicKillCount;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.UUID;

public class PlaceholderHook extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "killcount";
    }

    @Override
    public String getAuthor() {
        return "crystal";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params == null){
            return null;
        }
        UUID uuid = player.getUniqueId();
        File dataFile = EventListener.getPlayerDataFile(uuid);
        YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);

        return data.getString(params);
    }
}