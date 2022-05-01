package tech.skyouo.plugins.taiwanifyeveryitem;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEventTest implements Listener {
    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent event) {
        event.setMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), event.getMessage()));
    }
}
