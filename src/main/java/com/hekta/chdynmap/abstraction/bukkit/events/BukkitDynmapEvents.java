package com.hekta.chdynmap.abstraction.bukkit.events;

import org.dynmap.DynmapWebChatEvent;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.annotations.abstraction;

import com.hekta.chdynmap.abstraction.events.MCDynmapWebChatEvent;

/**
 *
 * @author Hekta
 */
public class BukkitDynmapEvents {

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCDynmapWebChatEvent implements MCDynmapWebChatEvent {

		DynmapWebChatEvent wce;

		public BukkitMCDynmapWebChatEvent(DynmapWebChatEvent event) {
			this.wce = event;
		}

		public Object _GetObject() {
			return wce;
		}

		public String getMessage() {
			return wce.getMessage();
		}
		
		public String getName() {
			return wce.getName();
		}

		public boolean isProcessed() {
			return wce.isProcessed();
		}

		public void setProcessed() {
			wce.setProcessed();
		}

		public String getSource() {
			return wce.getSource();
		}
	}
}