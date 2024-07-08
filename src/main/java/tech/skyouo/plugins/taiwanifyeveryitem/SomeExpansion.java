package tech.skyouo.plugins.taiwanifyeveryitem;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

import java.util.Locale;
import java.util.StringJoiner;

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
        return "1.1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.contains(" ")) {
            params = params.toLowerCase(Locale.ROOT).replace(' ', '_');
        }

        if (params.contains(".")) {
            String result = earlyParse(params);
            if (result != null) return result;
        }

        if (params.contains("-")) {
            String category = params.split("-")[0].toLowerCase();

            return switch (category) {
                case "entity" -> otherLocale(getEntityByName(params));
                case "enchantment" -> otherLocale(getEnchantmentByName(params));
                case "item" -> returnLocale(Material.matchMaterial(params));
                case "block" -> returnLocale(Material.matchMaterial(params));
                default -> null;
            };
        }


        Enchantment enchantment = getEnchantmentByName(params);
        if (enchantment != null) return otherLocale(enchantment);
        if (params.contains("level.")) return otherLocale(params);
        Material material = Material.matchMaterial(params);
        if (material != null) return returnLocale(material);
        EntityType entityType = getEntityByName(params);
        if (entityType != null) return otherLocale(entityType);

        return null;
    }

    private String toKey(Material material) {
        return (!material.isBlock() && material.isItem() ? "item." : material.isBlock() || material.isAir() ? "block." : "item.") + material.getKey().getNamespace().toLowerCase() + "." + material.getKey().getKey().toLowerCase();
    }

    private String toKey(EntityType entityType) {
        return "entity." + entityType.getKey().getNamespace().toLowerCase() + "." + entityType.getKey().getKey().toLowerCase();
    }

    private String toKey(Enchantment entityType) {
        return "enchantment." + entityType.getKey().getNamespace().toLowerCase() + "." + entityType.getKey().getKey().toLowerCase();
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

    private String otherLocale(EntityType type) {
        String key = toKey(type);
        return TaiwanifyEveryItem.entityLocale.containsKey(key) ? TaiwanifyEveryItem.entityLocale.get(key) : type.name();
    }

    private String otherLocale(Enchantment type) {
        String key = toKey(type);
        return TaiwanifyEveryItem.enchantmentLocale.containsKey(key) ? TaiwanifyEveryItem.enchantmentLocale.get(key) : type.toString();
    }

    private String otherLocale(String name) {
        int level = Integer.valueOf(name.split("\\.")[1]);
        try {
            return level <= 10 ? RomanNumber.toRoman(level) : String.valueOf(level);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String earlyParse(String key) {
        return TaiwanifyEveryItem.entityLocale.containsKey(key) ? TaiwanifyEveryItem.entityLocale.get(key) :
               TaiwanifyEveryItem.enchantmentLocale.containsKey(key) ? TaiwanifyEveryItem.enchantmentLocale.get(key) :
               TaiwanifyEveryItem.itemLocale.containsKey(key) ? TaiwanifyEveryItem.itemLocale.get(key) :
               TaiwanifyEveryItem.blockLocale.containsKey(key) ? TaiwanifyEveryItem.blockLocale.get(key) : null;
    }

    public EntityType getEntityByName(String name) {
        for (EntityType type : EntityType.values()) {
            if(type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public Enchantment getEnchantmentByName(String name) {
        for (Enchantment type : Enchantment.values()) {
            if(type.getKey().getKey().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
