package com.hekta.chdynmap.core;

import com.hekta.chdynmap.CHDynmap;
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
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.exceptions.CRE.CREFormatException;
import com.laytonsmith.core.exceptions.CRE.CREInvalidPluginException;
import com.laytonsmith.core.exceptions.CRE.CRENotFoundException;
import com.laytonsmith.core.exceptions.CRE.CREPluginInternalException;
import java.util.regex.Pattern;

/**
 *
 * @author Hekta
 */
public final class CHDynmapStatic {

	public static MCDynmapAPI getDynmapAPI(Target t) {
		MCDynmapAPI api = CHDynmap.getDynmapAPI();
		if (api != null) {
			return api;
		} else {
			throw new CREInvalidPluginException("Needed plugin Dynmap not found.", t);
		}
	}

	public static MCDynmapMarkerAPI getMarkerAPI(Target t) {
		try {
			return getDynmapAPI(t).getMarkerAPI();
		} catch (NullPointerException exception) {
			throw new CREPluginInternalException("The marker API of Dynmap plugin is not loaded.", t);
		}
	}

	public static MCDynmapMarkerSet getMarkerSet(String setID, Target t) {
		MCDynmapMarkerSet set = getMarkerAPI(t).getMarkerSet(setID);
		if (set != null) {
			return set;
		} else {
			throw new CRENotFoundException("\"" + setID + "\" is not an existing markerset.", t);
		}
	}

	public static MCDynmapMarker getMarker(String setID, String markerID, Target t) {
		MCDynmapMarker marker = getMarkerSet(setID, t).getMarker(markerID);
		if (marker != null) {
			return marker;
		} else {
			throw new CRENotFoundException("\"" + markerID + "\" is not an existing marker.", t);
		}
	}

	public static MCDynmapAreaMarker getAreaMarker(String setID, String markerID, Target t) {
		MCDynmapAreaMarker marker = getMarkerSet(setID, t).getAreaMarker(markerID);
		if (marker != null) {
			return marker;
		} else {
			throw new CRENotFoundException("\"" + markerID + "\" is not an existing area marker.", t);
		}
	}

	public static MCDynmapCircleMarker getCircleMarker(String setID, String markerID, Target t) {
		MCDynmapCircleMarker marker = getMarkerSet(setID, t).getCircleMarker(markerID);
		if (marker != null) {
			return marker;
		} else {
			throw new CRENotFoundException("\"" + markerID + "\" is not an existing circle marker.", t);
		}
	}

	public static MCDynmapIconMarker getIconMarker(String setID, String markerID, Target t) {
		MCDynmapIconMarker marker = getMarkerSet(setID, t).getIconMarker(markerID);
		if (marker != null) {
			return marker;
		} else {
			throw new CRENotFoundException("\"" + markerID + "\" is not an existing icon marker.", t);
		}
	}

	public static MCDynmapPolyLineMarker getPolyLineMarker(String setID, String markerID, Target t) {
		MCDynmapPolyLineMarker marker = getMarkerSet(setID, t).getPolyLineMarker(markerID);
		if (marker != null) {
			return marker;
		} else {
			throw new CRENotFoundException("\"" + markerID + "\" is not an existing polyline marker.", t);
		}
	}

	public static MCDynmapIcon getIcon(String iconID, Target t) {
		MCDynmapIcon icon = getMarkerAPI(t).getIcon(iconID);
		if (icon != null) {
			return icon;
		} else {
			throw new CRENotFoundException("\"" + iconID + "\" is not an existing icon.", t);
		}
	}

	public static MCDynmapPlayerSet getPlayerSet(String setID, Target t) {
		MCDynmapPlayerSet set = getMarkerAPI(t).getPlayerSet(setID);
		if (set != null) {
			return set;
		} else {
			throw new CRENotFoundException("\"" + setID + "\" is not an existing playerset.", t);
		}
	}

	public static void testDynmapIDValidity(String ID, Target t) {
		if (Pattern.matches("[^\\w\\.]", ID)) {
			throw new CREFormatException("A setID must only contain numbers, letters, periods (.) and underscores (_).", t);
		}
	}
}