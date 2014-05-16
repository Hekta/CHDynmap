package com.hekta.chdynmap.abstraction.bukkit.events;

import com.hekta.chdynmap.abstraction.events.MCDynmapWebChatEvent;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.annotations.abstraction;
import org.dynmap.DynmapWebChatEvent;

/**
 *
 * @author Hekta
 */
@abstraction(type = Implementation.Type.BUKKIT)
public class BukkitMCDynmapWebChatEvent implements MCDynmapWebChatEvent {

	private final DynmapWebChatEvent _event;

	public BukkitMCDynmapWebChatEvent(DynmapWebChatEvent event) {
		_event = event;
	}

	@Override
	public Object _GetObject() {
		return _event;
	}

	@Override
	public String getMessage() {
		return _event.getMessage();
	}

	@Override
	public String getName() {
		return _event.getName();
	}

	@Override
	public boolean isProcessed() {
		return _event.isProcessed();
	}

	@Override
	public void setProcessed() {
		_event.setProcessed();
	}

	@Override
	public String getSource() {
		return _event.getSource();
	}
}