package com.hekta.chdynmap.core.events;

import java.util.Map;

import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.Prefilters;
import com.laytonsmith.core.events.Prefilters.PrefilterType;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;
import com.laytonsmith.PureUtilities.Version;

import com.hekta.chdynmap.abstraction.events.MCDynmapWebChatEvent;

/**
 *
 * @author Hekta
 */
public class DynmapEvents {

	public static String docs() {
		return "Contains events related to the Dynmap plugin";
	}

	@api
	public static class dm_player_web_chat extends AbstractEvent {

		public String getName() {
			return "dm_player_web_chat";
		}

		public Driver driver() {
			return Driver.EXTENSION;
		}

		public BindableEvent convert(CArray manualObject) {
			throw new ConfigRuntimeException("This operation is not supported.", ExceptionType.BindException, Target.UNKNOWN);
/*			//TODO
*			String source = manualObject.get("source").val();
*			String message = manualObject.get("message").val();
*			String name = manualObject.get("name").val();
*			return EventBuilder.instantiate(CHDynmapWebChatEvent.class, source, name, message);
*
*			//new DynmapWebChatEvent(source, name, message);
*/		}

		public boolean modifyEvent(String key, Construct value, BindableEvent event) {
			return false;
		}

		public String docs() {
			return "{name: <macro> | processed: <boolean match> | source: <macro>}"
					+ " Fires when a player send a message on the Dynmap."
					+ " {message: the message the player sent | name: the name of the sender | processed: returns if the event has been handled by a plugin | source: from where the message is sent}"
					+ " {}"
					+ " {}";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public boolean matches(Map<String, Construct> prefilter, BindableEvent event) throws PrefilterNonMatchException {
			if (event instanceof MCDynmapWebChatEvent) {
				MCDynmapWebChatEvent wce = (MCDynmapWebChatEvent) event;
				Prefilters.match(prefilter, "source", wce.getSource(), PrefilterType.MACRO);
				Prefilters.match(prefilter, "name", wce.getName(), PrefilterType.MACRO);
				Prefilters.match(prefilter, "processed", String.valueOf(wce.isProcessed()), PrefilterType.MACRO);
				return true;
			} else {
				return false;
			}
		}

		public Map<String, Construct> evaluate(BindableEvent event) throws EventException {
			if (event instanceof MCDynmapWebChatEvent) {
				Map<String, Construct> eventObject = evaluate_helper(event);
				MCDynmapWebChatEvent wce = (MCDynmapWebChatEvent) event;
				Target t = Target.UNKNOWN;
				eventObject.put("source", new CString(wce.getSource(), t));
				eventObject.put("name", new CString(wce.getName(), t));
				eventObject.put("message", new CString(wce.getMessage(), t));
				eventObject.put("processed", new CBoolean(wce.isProcessed(), t));
				return eventObject;
			} else {
				throw new EventException("Cannot convert to CHDynmapWebChatEvent.");
			}
		}
	}
}