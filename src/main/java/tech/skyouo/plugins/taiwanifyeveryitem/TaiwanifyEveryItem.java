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
    public static HashMap<String, String> entityLocale;
    public static HashMap<String, String> enchantmentLocale;

    @Override
    public void onEnable() {
        // Plugin startup logic

        try {
            itemLocale = loadJSON("/item.zh-TW.json");

            blockLocale = loadJSON("/block.zh-TW.json");

            entityLocale = loadJSON("/entity.zh-TW.json");

            enchantmentLocale = loadJSON("/enchantment.zh-TW.json");

            assert getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
            
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

    private HashMap<String, String> loadJSON(String path) {
        return new Gson().fromJson(
                new JsonReader(
                        new BufferedReader(new InputStreamReader(
                                getClass().getResourceAsStream(path)
                        ))
                ),
                new TypeToken<HashMap<String, String>>(){}.getType()
        );
    }
}
