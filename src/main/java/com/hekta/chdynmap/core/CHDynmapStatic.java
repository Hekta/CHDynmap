package com.hekta.chdynmap.core;

import java.util.regex.Pattern;

import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;

import com.hekta.chdynmap.abstraction.MCDynmapAPI;
import com.hekta.chdynmap.abstraction.MCDynmapAreaMarker;
import com.hekta.chdynmap.abstraction.MCDynmapCircleMarker;
import com.hekta.chdynmap.abstraction.MCDynmapIcon;
import com.hekta.chdynmap.abstraction.MCDynmapIconMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerAPI;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerSet;
import com.hekta.chdynmap.abstraction.MCDynmapPlayerSet;
import com.hekta.chdynmap.abstraction.MCDynmapPolyLineMarker;

/**
 *
 * @author Hekta
 */
public final class CHDynmapStatic {

	public static MCDynmapAPI getDynmapAPI(Target t) {
		MCDynmapAPI api = com.hekta.chdynmap.extension.CHDynmapExtension.dynmapAPI;
		if (api != null) {
			return api;
		} else {
			throw new ConfigRuntimeException("Needed plugin Dynmap not found.", ExceptionType.InvalidPluginException, t);
		}
	}

	public static MCDynmapMarkerAPI getMarkerAPI(Target t) {
		try {
			return getDynmapAPI(t).getMarkerAPI();
		} catch (NullPointerException exception) {
			throw new ConfigRuntimeException("The marker API of Dynmap plugin is not loaded.", ExceptionType.PluginInternalException, t);
		}
	}

	public static MCDynmapMarkerSet getMarkerSet(String setID, Target t) {
		MCDynmapMarkerSet set = getMarkerAPI(t).getMarkerSet(setID);
		if (set != null) {
			return set;
		} else {
			throw new ConfigRuntimeException("\"" + setID + "\" is not an existing markerset.", ExceptionType.NotFoundException, t);
		}
	}

	public static MCDynmapMarker getMarker(String setID, String markerID, Target t) {
		MCDynmapMarker marker = getMarkerSet(setID, t).getMarker(markerID);
		if (marker != null) {
			return marker;
		} else {
			throw new ConfigRuntimeException("\"" + markerID + "\" is not an existing marker.", ExceptionType.NotFoundException, t);
		}
	}

	public static MCDynmapAreaMarker getAreaMarker(String setID, String markerID, Target t) {
		MCDynmapAreaMarker marker = getMarkerSet(setID, t).getAreaMarker(markerID);
		if (marker != null) {
			return marker;
		} else {
			throw new ConfigRuntimeException("\"" + markerID + "\" is not an existing area marker.", ExceptionType.NotFoundException, t);
		}
	}

	public static MCDynmapCircleMarker getCircleMarker(String setID, String markerID, Target t) {
		MCDynmapCircleMarker marker = getMarkerSet(setID, t).getCircleMarker(markerID);
		if (marker != null) {
			return marker;
		} else {
			throw new ConfigRuntimeException("\"" + markerID + "\" is not an existing circle marker.", ExceptionType.NotFoundException, t);
		}
	}

	public static MCDynmapIconMarker getIconMarker(String setID, String markerID, Target t) {
		MCDynmapIconMarker marker = getMarkerSet(setID, t).getIconMarker(markerID);
		if (marker != null) {
			return marker;
		} else {
			throw new ConfigRuntimeException("\"" + markerID + "\" is not an existing icon marker.", ExceptionType.NotFoundException, t);
		}
	}

	public static MCDynmapPolyLineMarker getPolyLineMarker(String setID, String markerID, Target t) {
		MCDynmapPolyLineMarker marker = getMarkerSet(setID, t).getPolyLineMarker(markerID);
		if (marker != null) {
			return marker;
		} else {
			throw new ConfigRuntimeException("\"" + markerID + "\" is not an existing polyline marker.", ExceptionType.NotFoundException, t);
		}
	}

	public static MCDynmapIcon getIcon(String iconID, Target t) {
		MCDynmapIcon icon = getMarkerAPI(t).getIcon(iconID);
		if (icon != null) {
			return icon;
		} else {
			throw new ConfigRuntimeException("\"" + iconID + "\" is not an existing icon.", ExceptionType.NotFoundException, t);
		}
	}

	public static MCDynmapPlayerSet getPlayerSet(String setID, Target t) {
		MCDynmapPlayerSet set = getMarkerAPI(t).getPlayerSet(setID);
		if (set != null) {
			return set;
		} else {
			throw new ConfigRuntimeException("\"" + setID + "\" is not an existing playerset.", ExceptionType.NotFoundException, t);
		}
	}

	public static void testDynmapIDValidity(String ID, Target t) {
		if (Pattern.matches("[^\\w\\.]", ID)) {
			throw new ConfigRuntimeException("A setID must only contain numbers, letters, periods (.) and underscores (_).", ExceptionType.FormatException, t);
		}
	}
}