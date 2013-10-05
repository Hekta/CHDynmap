package com.hekta.chdynmap.util;

import java.util.regex.Pattern;

import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;
import com.laytonsmith.core.Static;

import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.CircleMarker;
import org.dynmap.markers.GenericMarker;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;
import org.dynmap.markers.PlayerSet;
import org.dynmap.markers.PolyLineMarker;

/*
 *
 * @author Hekta
 */
public final class CHDynmapAPI {
	
	public static final DynmapAPI dynmapapi = (DynmapAPI) Static.getServer().getPluginManager().getPlugin("dynmap").getHandle();
	public static final MarkerAPI markerapi = dynmapapi.getMarkerAPI();

	public static MarkerSet getDynmapMarkerSet(String setID, Target t) {
		MarkerSet set = markerapi.getMarkerSet(setID);
		if (set == null) {
			throw new ConfigRuntimeException("\"" + setID + "\" is not an existing markerset.", ExceptionType.PluginInternalException, t);
		}
		return set;
	}

	public static AreaMarker getDynmapAreaMarker(String setID, String markerID, Target t) {
		AreaMarker marker = getDynmapMarkerSet(setID, t).findAreaMarker(markerID);
		if (marker == null) {
			throw new ConfigRuntimeException("\"" + markerID + "\" is not an existing area marker.", ExceptionType.PluginInternalException, t);
		}
		return marker;
	}

	public static CircleMarker getDynmapCircleMarker(String setID, String markerID, Target t) {
		CircleMarker marker = getDynmapMarkerSet(setID, t).findCircleMarker(markerID);
		if (marker == null) {
			throw new ConfigRuntimeException("\"" + markerID + "\" is not an existing circle marker.", ExceptionType.PluginInternalException, t);
		}
		return marker;
	}

	public static Marker getDynmapIconMarker(String setID, String markerID, Target t) {
		Marker marker = getDynmapMarkerSet(setID, t).findMarker(markerID);
		if (marker == null) {
			throw new ConfigRuntimeException("\"" + markerID + "\" is not an existing icon marker.", ExceptionType.PluginInternalException, t);
		}
		return marker;
	}

	public static PolyLineMarker getDynmapPolyLineMarker(String setID, String markerID, Target t) {
		PolyLineMarker marker = getDynmapMarkerSet(setID, t).findPolyLineMarker(markerID);
		if (marker == null) {
			throw new ConfigRuntimeException("\"" + markerID + "\" is not an existing polyline marker.", ExceptionType.PluginInternalException, t);
		}
		return marker;
	}

	public static GenericMarker getDynmapMarker(String setID, String markerID, Target t) {
		GenericMarker marker;
		MarkerSet set = getDynmapMarkerSet(setID, t);
		marker = set.findAreaMarker(markerID);
		if (marker != null) {
			return marker;
		}
		marker = set.findCircleMarker(markerID);
		if (marker != null) {
			return marker;
		}
		marker = set.findMarker(markerID);
		if (marker != null) {
			return marker;
		}
		marker = set.findPolyLineMarker(markerID);
		if (marker != null) {
			return marker;
		}
		throw new ConfigRuntimeException("\"" + markerID + "\" is not an existing marker.", ExceptionType.PluginInternalException, t);
	}

	public static MarkerIcon getDynmapIcon(String iconID, Target t) {
		MarkerIcon icon = markerapi.getMarkerIcon(iconID);
		if (icon == null) {
			throw new ConfigRuntimeException("\"" + iconID + "\" is not an existing icon.", ExceptionType.PluginInternalException, t);
		}
		return icon;
	}

	public static PlayerSet getDynmapPlayerSet(String setID, Target t) {
		PlayerSet set = markerapi.getPlayerSet(setID);
		if (set == null) {
			throw new ConfigRuntimeException("\"" + setID + "\" is not an existing playerset.", ExceptionType.PluginInternalException, t);
		}
		return set;
	}

	public static void testDynmapIDValidity(String ID, Target t) {
		if (Pattern.matches("[^\\w\\.]", ID)) {
			throw new ConfigRuntimeException("A setID must only contain numbers, letters, periods (.) and underscores (_).", ExceptionType.FormatException, t);
		}
	}
}
