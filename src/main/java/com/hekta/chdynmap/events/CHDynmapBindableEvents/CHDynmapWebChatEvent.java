package com.hekta.chdynmap.events.CHDynmapBindableEvents;

import com.laytonsmith.core.events.BindableEvent;

import org.dynmap.DynmapWebChatEvent;

/*
 *
 * @author Hekta
 */
public class CHDynmapWebChatEvent implements BindableEvent {

	DynmapWebChatEvent wce;

	public CHDynmapWebChatEvent(DynmapWebChatEvent event) {
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
