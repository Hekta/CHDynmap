package com.hekta.chdynmap.abstraction.bukkit.events.drivers;

import com.hekta.chdynmap.abstraction.bukkit.events.BukkitMCDynmapWebChatEvent;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.dynmap.DynmapWebChatEvent;

/**
 *
 * @author Hekta
 */
public class BukkitDynmapListener implements Listener {

	private static BukkitDynmapListener _listener;

	public static void register() {
		if (_listener == null) {
			_listener = new BukkitDynmapListener();
		}
		CommandHelperPlugin.self.registerEvents(_listener);
	}

	public static void unregister() {
			DynmapWebChatEvent.getHandlerList().unregister(_listener);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onWebChat(DynmapWebChatEvent event) {
		EventUtils.TriggerListener(Driver.EXTENSION, "dm_player_web_chat", new BukkitMCDynmapWebChatEvent(event));
	}
}