package com.hekta.chdynmap.abstraction.bukkit.events.drivers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.dynmap.DynmapWebChatEvent;

import com.laytonsmith.annotations.shutdown;
import com.laytonsmith.annotations.startup;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;

import com.hekta.chdynmap.abstraction.bukkit.events.BukkitDynmapEvents;
import com.hekta.chdynmap.extension.CHDynmapExtension;

/**
 *
 * @author Hekta
 */
public class BukkitDynmapListener implements Listener {

	protected static BukkitDynmapListener dl;

	@startup
	public static void register() {
		if (CHDynmapExtension.dynmapAPI != null) {
			if (dl == null) {
				dl = new BukkitDynmapListener();
			}
			CommandHelperPlugin.self.registerEvent(dl);
		}
	}

	@shutdown
	public static void unregister() {
		if (CHDynmapExtension.dynmapAPI != null) {
			DynmapWebChatEvent.getHandlerList().unregister(dl);
		}
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onWebChat(DynmapWebChatEvent event) {
		BukkitDynmapEvents.BukkitMCDynmapWebChatEvent wce = new BukkitDynmapEvents.BukkitMCDynmapWebChatEvent(event);
		EventUtils.TriggerExternal(wce);
		EventUtils.TriggerListener(Driver.EXTENSION, "dm_player_web_chat", wce);
	}
}