package tech.skyouo.plugins.taiwanifyeveryitem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

public final class TaiwanifyEveryItem extends JavaPlugin {
    public static HashMap<String, String> itemLocale;
    public static HashMap<String, String> blockLocale;

    @Override
    public void onEnable() {
        // Plugin startup logic

        try {
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();

            itemLocale = new Gson().fromJson(
                    new JsonReader(
                            new BufferedReader(new InputStreamReader(
                                    getClass().getResourceAsStream("/item.zh-TW.json")
                            ))
                    ),
                    type
            );

            blockLocale = new Gson().fromJson(
                    new JsonReader(
                            new BufferedReader(new InputStreamReader(
                                    getClass().getResourceAsStream("/block.zh-TW.json")
                            ))
                    ),
                    type
            );

            for (String item:
                 itemLocale.keySet()) {
                getLogger().info("Loaded item: " + item);
            }

            for (String item:
                    blockLocale.keySet()) {
                getLogger().info("Loaded block: " + item);
            }

            assert getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;

            getServer().getPluginManager().registerEvents(new ChatEventTest(), this);
            new SomeExpansion().register();
        } catch (AssertionError e) {
            getLogger().warning("您尚未安裝 PlaceholderAPI!");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
