package com.hekta.chdynmap.events.CHDynmapListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.laytonsmith.annotations.shutdown;
import com.laytonsmith.annotations.startup;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;

import org.dynmap.DynmapWebChatEvent;

import com.hekta.chdynmap.events.CHDynmapBindableEvents.CHDynmapWebChatEvent;

/*
 *
 * @author Hekta
 */
public class CHDynmapWebChatListener implements Listener {

	protected static CHDynmapWebChatListener wcl;

	@startup
	public static void register() {
		Static.checkPlugin("dynmap", Target.UNKNOWN);
		if (wcl == null) {
			wcl = new CHDynmapWebChatListener();
		}
		CommandHelperPlugin.self.registerEvent(wcl);
	}

	@shutdown
	public static void unregister() {
		DynmapWebChatEvent.getHandlerList().unregister(wcl);
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onDynmapWebChat(DynmapWebChatEvent event) {
		EventUtils.TriggerListener(Driver.EXTENSION, "dm_player_web_chat", new CHDynmapWebChatEvent(event));
	}
}
