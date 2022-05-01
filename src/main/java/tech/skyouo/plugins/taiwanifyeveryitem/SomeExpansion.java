package tech.skyouo.plugins.taiwanifyeveryitem;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

public class SomeExpansion extends PlaceholderExpansion {
    @Override
    public String getAuthor() {
        return "skyouo0727";
    }

    @Override
    public String getIdentifier() {
        return "locale";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        Material material = Material.matchMaterial(params);
        if (material == null) return null;

        return returnLocale(material);
    }

    private String toKey(Material material) {
        return (!material.isBlock() && material.isItem() ? "item." : material.isBlock() || material.isAir() ? "block." : "item.") + material.getKey().getNamespace().toLowerCase() + "." + material.getKey().getKey().toLowerCase();
    }

    private String returnLocale(Material material) {
        String key = toKey(material);
        if (!material.isBlock() && material.isItem()) {
            return TaiwanifyEveryItem.itemLocale.containsKey(key) ?
                    TaiwanifyEveryItem.itemLocale.get(key) : material.name();
        } else if (material.isBlock() | material.isAir()) {
            return TaiwanifyEveryItem.blockLocale.containsKey(key) ?
                    TaiwanifyEveryItem.blockLocale.get(key) : material.name();
        } else {
            return material.name();
        }
    }

}
