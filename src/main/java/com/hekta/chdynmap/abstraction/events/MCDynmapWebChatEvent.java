package com.hekta.chdynmap.abstraction.events;

import com.laytonsmith.core.events.BindableEvent;

/**
 *
 * @author Hekta
 */
public interface MCDynmapWebChatEvent extends BindableEvent {

	public String getMessage();

	public String getName();

	public boolean isProcessed();
	public void setProcessed();

	public String getSource();
}