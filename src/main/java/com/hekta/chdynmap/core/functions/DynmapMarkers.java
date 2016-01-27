package com.hekta.chdynmap.core.functions;

import com.hekta.chdynmap.abstraction.MCDynmapAreaMarker;
import com.hekta.chdynmap.abstraction.MCDynmapCircleMarker;
import com.hekta.chdynmap.abstraction.MCDynmapIcon;
import com.hekta.chdynmap.abstraction.MCDynmapIconMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerFillStyle;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerLineStyle;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerSet;
import com.hekta.chdynmap.abstraction.MCDynmapPolyLineMarker;
import com.hekta.chdynmap.abstraction.enums.MCDynmapMarkerType;
import com.hekta.chdynmap.core.CHDynmapStatic;
import com.laytonsmith.PureUtilities.Common.StringUtils;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCWorld;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.ArgumentValidation;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.CDouble;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CNull;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CRECastException;
import com.laytonsmith.core.exceptions.CRE.CREFormatException;
import com.laytonsmith.core.exceptions.CRE.CREInvalidPluginException;
import com.laytonsmith.core.exceptions.CRE.CREInvalidWorldException;
import com.laytonsmith.core.exceptions.CRE.CRENotFoundException;
import com.laytonsmith.core.exceptions.CRE.CREPluginInternalException;
import com.laytonsmith.core.exceptions.CRE.CRERangeException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import java.util.Set;

/**
 *
 * @author Hekta
 */
public class DynmapMarkers {

	public static String docs() {
		return "A class of functions to manage the Dynmap markers.";
	}

	public static abstract class DynmapMarkerFunction extends AbstractFunction {

		@Override
		public boolean isRestricted() {
			return true;
		}

		@Override
		public Boolean runAsync() {
			return false;
		}

		@Override
		public Version since() {
			return CHVersion.V3_3_1;
		}
	}

	public static abstract class DynmapMarkerGetterFunction extends DynmapMarkerFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRENotFoundException.class};
		}
	}

	public static abstract class DynmapMarkerSetterFunction extends DynmapMarkerFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{3};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRENotFoundException.class, CRECastException.class};
		}
	}

	@api
	public static class dm_all_markers extends DynmapMarkerFunction {

		@Override
		public String getName() {
			return "dm_all_markers";
		}

		@Override
		public Integer[] numArgs() {
			return new Integer[]{1, 2};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class};
		}

		@Override
		public String docs() {
			return "array {setID, [type]} Returns an associative array containing the ID of all markers in the markerset."
					+ " If the type is given, only the markers of this type are returne."
					+ " Type can be one of " + StringUtils.Join(MCDynmapMarkerType.values(), ", ", ", or ", " or ") + ".";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarkerSet set = CHDynmapStatic.getMarkerSet(args[0].val(), t);
			MCDynmapMarkerType type;
			if ((args.length == 1) || (args[1] instanceof CNull)) {
				type = null;
			} else {
				try {
					type = MCDynmapMarkerType.valueOf(args[1].val().toUpperCase());
				} catch (IllegalArgumentException exception) {
					throw new CREPluginInternalException("Invalid marker type: " + args[1].val() + ".", t);
				}
			}
			CArray markerArray = new CArray(t);
			if ((type == null) || (type == MCDynmapMarkerType.AREA)) {
				for (MCDynmapAreaMarker areaMarker : set.getAreaMarkers()) {
					markerArray.push(new CString(areaMarker.getId(), t), t);
				}
			}
			if ((type == null) || (type == MCDynmapMarkerType.CIRCLE)) {
				for (MCDynmapCircleMarker circleMarker : set.getCircleMarkers()) {
					markerArray.push(new CString(circleMarker.getId(), t), t);
				}	
			}
			if ((type == null) || (type == MCDynmapMarkerType.ICON)) {
				for (MCDynmapIconMarker iconMarker : set.getIconMarkers()) {
					markerArray.push(new CString(iconMarker.getId(), t), t);
				}	
			}
			if ((type == null) || (type == MCDynmapMarkerType.POLYLINE)) {
				for (MCDynmapPolyLineMarker polyLineMarker : set.getPolyLineMarkers()) {
					markerArray.push(new CString(polyLineMarker.getId(), t), t);
				}	
			}
			return markerArray;
		}
	}

	@api
	public static class dm_create_marker extends DynmapMarkerFunction {

		@Override
		public String getName() {
			return "dm_create_marker";
		}

		@Override
		public Integer[] numArgs() {
			return new Integer[]{1, 2};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRECastException.class, CREFormatException.class, CREInvalidWorldException.class};
		}

		@Override
		public String docs() {
			return "string {setID, [optionArray]} Creates a marker and returns its ID."
					+ " ---- The option array is associative and not required, and all its keys are optional."
					+ " <li>KEY - DEFAULT - DESCRIPTION - COMMENT</li>"
					+ " <li>center - world spawn - the center of the marker - only for circle markers, world is ignored</li>"
					+ " <li>corners - world spawn - the corners of the marker - only for area or polyline markers, world is ignored (and also y for area markers)</li>"
					+ " <li>icon - null - the icon ID of the marker, null for the markerset default icon - only for icon markers</li>"
					+ " <li>id - random - ID of the marker, must be unique within the set, if null or not given, an unique ID is generated</li>"
					+ " <li>label - markerID - the label of the marker</li>"
					+ " <li>label_is_html - false - sets if the label is processing as HTML</li>"
					+ " <li>location - world spawn - the location of the marker - only for icon markers, world is ignored</li>"
					+ " <li>persistent - false - sets if the label is persistent (saved and reloaded on restart), the markerset must be persistent - can not be changed later</li>"
					+ " <li>radius - 0 0 - the radius of the marker - only for circle markers</li>"
					+ " <li>type - ICON - the type of the marker, can be one of " + StringUtils.Join(MCDynmapMarkerType.values(), ", ", ", or ", " or ") + " - can not be changed later</li>"
					+ " <li>world - first world - the world of the marker</li>";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarkerSet set = CHDynmapStatic.getMarkerSet(args[0].val(), t);
			//create the option array
			CArray optionArray;
			if (args.length == 1) {
				optionArray = new CArray(t);
			} else {
				optionArray = ArgumentValidation.getArray(args[1], t);
			}
			Set<String> keys = optionArray.stringKeySet();
			//set optional values
			//type
			MCDynmapMarkerType type;
			if ((!keys.contains("type")) || (optionArray.get("type", t) instanceof CNull)) {
				type = MCDynmapMarkerType.ICON;
			} else {
				try {
					type = MCDynmapMarkerType.valueOf(optionArray.get("type", t).val().toUpperCase());
				} catch (IllegalArgumentException exception) {
					throw new CREPluginInternalException("Invalid marker type: " + optionArray.get("type", t).val(), t);
				}
			}
			//id
			String id;
			if (keys.contains("id")) {
				id = optionArray.get("id", t).val();
				if (set.getMarker(id) != null) {
					throw new CREPluginInternalException("\"" + id + "\" is already an existing marker.", t);
				}
			} else {
				id = null;
			}
			//world
			MCWorld world;
			if (keys.contains("world")) {
				world = Static.getServer().getWorld(optionArray.get("world", t).val());
				if (world == null) {
					throw new CREInvalidWorldException("Unknown world: " + optionArray.get("world", t).val(), t);
				}
			} else {
				world = Static.getServer().getWorlds().get(0);
			}
			//label
			String label;
			if (keys.contains("label")) {
				label = optionArray.get("label", t).val();
			} else {
				label = id;
			}
			//label_is_html
			boolean labelIsHTML;
			if (keys.contains("label_is_html")) {
				labelIsHTML = ArgumentValidation.getBoolean(optionArray.get("label_is_html", t), t);
			} else {
				labelIsHTML = false;
			}
			//persistent
			boolean isPersistent;
			if (keys.contains("persistent")) {
				isPersistent = ArgumentValidation.getBoolean(optionArray.get("persistent", t), t);
			} else {
				isPersistent = false;
			}
			//center
			MCLocation center;
			if (type == MCDynmapMarkerType.CIRCLE) {
				if (keys.contains("center")) {
					center = ObjectGenerator.GetGenerator().location(optionArray.get("center", t), world, t);
				} else {
					center = world.getSpawnLocation();
				}
			} else {
				center = null;
			}
			//corners
			MCLocation[] corners;
			if (type == MCDynmapMarkerType.AREA) {
				if (keys.contains("corners")) {
					CArray givenCorners = ArgumentValidation.getArray(optionArray.get("corners", t), t);
					corners = new MCLocation[(int) givenCorners.size()];
					if (givenCorners.inAssociativeMode()) {
						throw new CRECastException("The corners array must not be associative.", t);
					}
					int i = 0;
					for (Construct corner : givenCorners.asList()) {
						corners[i] = ObjectGenerator.GetGenerator().location(corner, world, t);
						i++;
					}
				} else {
					corners = new MCLocation[]{world.getSpawnLocation()};
				}
			} else if (type == MCDynmapMarkerType.POLYLINE) {
				if (keys.contains("corners")) {
					CArray givenCorners = ArgumentValidation.getArray(optionArray.get("corners", t), t);
					corners = new MCLocation[(int) givenCorners.size()];
					if (givenCorners.inAssociativeMode()) {
						throw new CRECastException("The corners array must not be associative.", t);
					}
					int i = 0;
					for (Construct corner : givenCorners.asList()) {
						corners[i] = ObjectGenerator.GetGenerator().location(corner, world, t);
						i++;
					}
				} else {
					corners = new MCLocation[]{world.getSpawnLocation()};
				}
			} else {
				corners = null;
			}
			//icon
			MCDynmapIcon icon;
			if (type == MCDynmapMarkerType.ICON) {
				if (keys.contains("icon")) {
					icon = CHDynmapStatic.getIcon(optionArray.get("icon", t).val(), t);
				} else {
					icon = set.getDefaultIcon();
				}
			} else {
				icon = null;
			}
			//location
			MCLocation iconLocation;
			if (type == MCDynmapMarkerType.ICON) {
				if (keys.contains("location")) {
					iconLocation = ObjectGenerator.GetGenerator().location(optionArray.get("location", t), world, t);
				} else {
					iconLocation = world.getSpawnLocation();
				}
			} else {
				iconLocation = null;
			}
			//radius
			double radiusX;
			double radiusZ;
			if (type == MCDynmapMarkerType.CIRCLE) {
				if (keys.contains("radius")) {
					CArray radius = ArgumentValidation.getArray(optionArray.get("radius", t), t);
					radiusX = ArgumentValidation.getDouble(radius.get("x", t), t);
					radiusZ = ArgumentValidation.getDouble(radius.get("z", t), t);
				} else {
					radiusX = 0;
					radiusZ = 0;
				}
			} else {
				radiusX = 0;
				radiusZ = 0;
			}
			//create the marker
			MCDynmapMarker marker;
			switch (type) {
				case AREA:
					marker = set.createAreaMarker(id, label, labelIsHTML, world, corners, isPersistent);
					break;
				case CIRCLE:
					marker = set.createCircleMarker(id, label, labelIsHTML, center, radiusX, radiusZ, isPersistent);
					break;
				case ICON:
					marker = set.createIconMarker(id, label, labelIsHTML, iconLocation, icon, isPersistent);
					break;
				case POLYLINE:
					marker = set.createPolyLineMarker(id, label, labelIsHTML, world, corners, isPersistent);
					break;
				default:
					marker = null;
					break;
			}
			if (marker == null) {
				throw new CREPluginInternalException("The marker creation failed.", t);
			}
			return new CString(marker.getId(), t);
		}
	}

	@api
	public static class dm_delete_marker extends DynmapMarkerFunction {

		@Override
		public String getName() {
			return "dm_delete_marker";
		}

		@Override
		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRENotFoundException.class};
		}

		@Override
		public String docs() {
			return "void {setID, markerID} Deletes a marker in the set.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t).delete();
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_marker_boosted extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_boosted";
		}

		@Override
		public String docs() {
			return "array {setID, markerID} Returns if the marker resolution is boosted. Only for area and circle markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarker marker = CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t);
			switch (marker.getType()) {
				case AREA:
					return CBoolean.get(((MCDynmapAreaMarker) marker).isBoosted());
				case CIRCLE:
					return CBoolean.get(((MCDynmapCircleMarker) marker).isBoosted());
				default:
					throw new CRENotFoundException("There is no existing area or circle markers with this id.", t);
			}
		}
	}

	@api
	public static class dm_set_marker_boosted extends DynmapMarkerSetterFunction {

		@Override
		public String getName() {
			return "dm_set_marker_boosted";
		}

		@Override
		public String docs() {
			return "void {setID, markerID, boolean} Sets if the marker resolution is boosted. Only for area and circle markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarker marker = CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t);
			switch (marker.getType()) {
				case AREA:
					((MCDynmapAreaMarker) marker).setBoosted(ArgumentValidation.getBoolean(args[2], t));
					break;
				case CIRCLE:
					((MCDynmapCircleMarker) marker).setBoosted(ArgumentValidation.getBoolean(args[2], t));
					break;
				default:
					throw new CRENotFoundException("There is no existing area or circle markers with this id.", t);
			}
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_marker_center extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_center";
		}

		@Override
		public String docs() {
			return "array {setID, markerID} Returns the location of the marker center. Only for circle markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return ObjectGenerator.GetGenerator().location(CHDynmapStatic.getCircleMarker(args[0].val(), args[1].val(), t).getCenter());
		}
	}

	@api
	public static class dm_set_marker_center extends DynmapMarkerSetterFunction {

		@Override
		public String getName() {
			return "dm_set_marker_center";
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRENotFoundException.class, CRECastException.class, CREFormatException.class, CREInvalidWorldException.class};
		}

		@Override
		public String docs() {
			return "void {setID, markerID, locationArray} Sets the center of a marker. Only for circle markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapCircleMarker marker = CHDynmapStatic.getCircleMarker(args[0].val(), args[1].val(), t);
			marker.setCenter(ObjectGenerator.GetGenerator().location(args[2], marker.getWorld(), t));
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_marker_corners extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_corners";
		}

		@Override
		public String docs() {
			return "array {setID, markerID} Returns the corners location of the marker. Only for area and polyline markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarker marker = CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t);
			MCLocation[] corners;
			switch (marker.getType()) {
				case AREA:
					corners = ((MCDynmapAreaMarker) marker).getCorners();
					break;
				case POLYLINE:
					corners = ((MCDynmapPolyLineMarker) marker).getCorners();
					break;
				default:
					throw new CRENotFoundException("There is no existing area or polyline markers with this id.", t);
			}
			CArray cornerArray = new CArray(t);
			for (MCLocation location : corners) {
				cornerArray.push(ObjectGenerator.GetGenerator().location(location), t);
			}
			return cornerArray;
		}
	}

	@api
	public static class dm_set_marker_corners extends DynmapMarkerSetterFunction {

		@Override
		public String getName() {
			return "dm_set_marker_corners";
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRENotFoundException.class, CRECastException.class, CREFormatException.class};
		}

		@Override
		public String docs() {
			return "void {setID, markerID, array} Sets the location of the marker corners (array of location arrays, world is ignored, and for area markers y is ignored). Only for area and polyline markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarker marker = CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t);
			CArray givenCorners = ArgumentValidation.getArray(args[2], t);
			if (givenCorners.inAssociativeMode()) {
				throw new CRECastException("The array must not be associative.", t);
			}
			MCLocation[] corners = new MCLocation[(int) givenCorners.size()];
			MCWorld world = marker.getWorld();
			int i = 0;
			for (Construct corner : givenCorners.asList()) {
				corners[i] = ObjectGenerator.GetGenerator().location(corner, world, t);
				i++;
			}
			switch (marker.getType()) {
				case AREA:
					((MCDynmapAreaMarker) marker).setCorners(corners);
					break;
				case POLYLINE:
					((MCDynmapPolyLineMarker) marker).setCorners(corners);
					break;
				default:
					throw new CRENotFoundException("There is no existing area or polyline markers with this id.", t);
			}
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_marker_description extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_label";
		}

		@Override
		public String docs() {
			return "string {setID, markerID} Returns the description of the marker.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t).getDescription(), t);
		}
	}

	@api
	public static class dm_set_marker_description extends DynmapMarkerSetterFunction {

		@Override
		public String getName() {
			return "dm_set_marker_description";
		}

		@Override
		public String docs() {
			return "void {setID, markerID, htmlDescription} Sets the description of the marker (in HTML).";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t).setDescription(args[2].val());
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_marker_fill_style extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_fill_style";
		}

		@Override
		public String docs() {
			return "array {setID, markerID} Returns the fill style array of the marker. Only for area and circle markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarker marker = CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t);
			MCDynmapMarkerFillStyle fillStyle;
			switch (marker.getType()) {
				case AREA:
					fillStyle = ((MCDynmapAreaMarker) marker).getFillStyle();
					break;
				case CIRCLE:
					fillStyle = ((MCDynmapCircleMarker) marker).getFillStyle();
					break;
				default:
					throw new CRENotFoundException("There is no existing area or circle markers with this id.", t);
			}
			CArray styleArray = new CArray(t);
			styleArray.set("color", ObjectGenerator.GetGenerator().color(fillStyle.getColor(), t), t);
			styleArray.set("opacity", new CDouble(fillStyle.getOpacity(), t), t);
			return styleArray;
		}
	}

	@api
	public static class dm_set_marker_fill_style extends DynmapMarkerSetterFunction {

		@Override
		public String getName() {
			return "dm_set_marker_fill_style";
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRECastException.class, CREFormatException.class};
		}

		@Override
		public String docs() {
			return "void {setID, markerID, array} Sets the marker fill style (array with \"color\" and \"opacity\" optional keys, color is a color r g b array, and opacity a number between 0 and 1 inclusive)."
					+ " Only for area and circle markers";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarker marker = CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t);
			CArray styleArray = ArgumentValidation.getArray(args[2], t);
			Set keys = styleArray.stringKeySet();
			MCDynmapMarkerFillStyle fillStyle;
			switch (marker.getType()) {
				case AREA:
					fillStyle = ((MCDynmapAreaMarker) marker).getFillStyle();
					break;
				case CIRCLE:
					fillStyle = ((MCDynmapCircleMarker) marker).getFillStyle();
					break;
				default:
					throw new CRENotFoundException("There is no existing area or circle markers with this id.", t);
			}
			if (keys.contains("color")) {
				fillStyle.setColor(ObjectGenerator.GetGenerator().color(ArgumentValidation.getArray(styleArray.get("color", t), t), t));
			}
			if (keys.contains("opacity")) {
				double opacity = ArgumentValidation.getDouble(styleArray.get("opacity", t), t);
				if ((opacity < 0) || (opacity > 1)) {
					throw new CRERangeException("Opacity must be between 0 and 1 inclusive.", t);
				} else {
					fillStyle.setOpacity(opacity);
				}
			}
			switch (marker.getType()) {
				case AREA:
					((MCDynmapAreaMarker) marker).setFillStyle(fillStyle);
					break;
				case CIRCLE:
					((MCDynmapCircleMarker) marker).setFillStyle(fillStyle);
					break;
			}
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_marker_icon extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_icon";
		}

		@Override
		public String docs() {
			return "string {setID, markerID} Returns the icon ID of the marker. Only for icon markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHDynmapStatic.getIconMarker(args[0].val(), args[1].val(), t).getIcon().getId(), t);
		}
	}

	@api
	public static class dm_set_marker_icon extends DynmapMarkerSetterFunction {

		@Override
		public String getName() {
			return "dm_set_marker_icon";
		}

		@Override
		public String docs() {
			return "void {setID, markerID, iconID} Sets the icon of a marker. Only for icon markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapIconMarker marker = CHDynmapStatic.getIconMarker(args[0].val(), args[1].val(), t);
			MCDynmapIcon icon = CHDynmapStatic.getIcon(args[2].val(), t);
			if (marker.getSet().iconIsAllowed(icon)) {
				marker.setIcon(icon);
			} else {
				throw new CREPluginInternalException("The icon is not allowed for the markerset.", t);
			}
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_marker_label extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_label";
		}

		@Override
		public String docs() {
			return "string {setID, markerID} Returns the label of the marker.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t).getLabel(), t);
		}
	}

	@api
	public static class dm_marker_label_is_html extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_label_is_html";
		}

		@Override
		public String docs() {
			return "boolean {setID, markerID} Returns if the label of the marker is processed as HTML.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return CBoolean.get(CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t).isLabelMarkup());
		}
	}

	@api
	public static class dm_set_marker_label extends DynmapMarkerSetterFunction {

		@Override
		public String getName() {
			return "dm_set_marker_label";
		}

		@Override
		public String docs() {
			return "void {setID, markerID, label, [isHTML]} Sets the label of the marker, isHTML is a boolean, if true, label will be processed as HTML.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarker marker = CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t);
			boolean isHTML;
			if (args.length == 3) {
				isHTML = false;
			} else {
				isHTML = ArgumentValidation.getBoolean(args[3], t);
			}
			marker.setLabel(args[2].val(), isHTML);
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_marker_line_style extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_line_style";
		}

		@Override
		public String docs() {
			return "array {setID, markerID} Returns the line style array of the marker. Only for area, circle and polyline markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarker marker = CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t);
			MCDynmapMarkerLineStyle lineStyle;
			switch (marker.getType()) {
				case AREA:
					lineStyle = ((MCDynmapAreaMarker) marker).getLineStyle();
					break;
				case CIRCLE:
					lineStyle = ((MCDynmapCircleMarker) marker).getLineStyle();
					break;
				case POLYLINE:
					lineStyle = ((MCDynmapPolyLineMarker) marker).getLineStyle();
					break;
				default:
					throw new CRENotFoundException("There is no existing area, circle or polyline markers with this id.", t);
			}
			CArray styleArray = new CArray(t);
			styleArray.set("color", ObjectGenerator.GetGenerator().color(lineStyle.getColor(), t), t);
			styleArray.set("opacity", new CDouble(lineStyle.getOpacity(), t), t);
			styleArray.set("weight", new CInt(lineStyle.getWeight(), t), t);
			return styleArray;
		}
	}

	@api
	public static class dm_set_marker_line_style extends DynmapMarkerSetterFunction {

		@Override
		public String getName() {
			return "dm_set_marker_line_style";
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRENotFoundException.class, CRECastException.class, CREFormatException.class};
		}

		@Override
		public String docs() {
			return "void {setID, markerID, array} Sets the marker line style (array with \"color\", \"opacity\" and \"weight\" optional keys, color is a color r g b array, opacity a number between 0 and 1 inclusive and weight is an integer)."
					+ " Only for area, circle and polyline markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarker marker = CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t);
			CArray styleArray = ArgumentValidation.getArray(args[2], t);
			Set keys = styleArray.stringKeySet();
			MCDynmapMarkerLineStyle lineStyle;
			switch (marker.getType()) {
				case AREA:
					lineStyle = ((MCDynmapAreaMarker) marker).getLineStyle();
					break;
				case CIRCLE:
					lineStyle = ((MCDynmapCircleMarker) marker).getLineStyle();
					break;
				case POLYLINE:
					lineStyle = ((MCDynmapPolyLineMarker) marker).getLineStyle();
					break;
				default:
					throw new CRENotFoundException("There is no existing area, circle or polyline markers with this id.", t);
			}
			if (keys.contains("color")) {
				lineStyle.setColor(ObjectGenerator.GetGenerator().color(ArgumentValidation.getArray(styleArray.get("color", t), t), t));
			}
			if (keys.contains("opacity")) {
				double opacity = ArgumentValidation.getDouble(styleArray.get("opacity", t), t);
				if ((opacity < 0) || (opacity > 1)) {
					throw new CRERangeException("Opacity must be between 0 and 1 inclusive.", t);
				} else {
					lineStyle.setOpacity(opacity);
				}
			}
			if (keys.contains("weight")) {
				lineStyle.setWeight(ArgumentValidation.getInt32(styleArray.get("weight", t), t));
			}
			switch (marker.getType()) {
				case AREA:
					((MCDynmapAreaMarker) marker).setLineStyle(lineStyle);
					break;
				case CIRCLE:
					((MCDynmapCircleMarker) marker).setLineStyle(lineStyle);
					break;
				case POLYLINE:
					((MCDynmapPolyLineMarker) marker).setLineStyle(lineStyle);
					break;
			}
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_marker_loc extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_loc";
		}

		@Override
		public String docs() {
			return "string {setID, markerID} Returns the location of the marker. Only for icon markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return ObjectGenerator.GetGenerator().location(CHDynmapStatic.getIconMarker(args[0].val(), args[1].val(), t).getLocation());
		}
	}

	@api
	public static class dm_set_marker_loc extends DynmapMarkerSetterFunction {

		@Override
		public String getName() {
			return "dm_set_marker_loc";
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRECastException.class, CREFormatException.class, CREInvalidWorldException.class};
		}

		@Override
		public String docs() {
			return "void {setID, markerID, locationArray} Sets the icon location of a marker. Only for icon markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapIconMarker marker = CHDynmapStatic.getIconMarker(args[0].val(), args[1].val(), t);
			marker.setLocation(ObjectGenerator.GetGenerator().location(args[2], marker.getWorld(), t));
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_set_marker_markerset extends DynmapMarkerSetterFunction {

		@Override
		public String getName() {
			return "dm_set_marker_markerset";
		}

		@Override
		public String docs() {
			return "void {setID, markerID, newSetID} Changes the markerset of the marker.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarker marker = CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t);
			MCDynmapMarkerSet newSet = CHDynmapStatic.getMarkerSet(args[2].val(), t);
			if (newSet.getMarker(marker.getId()) != null) {
				throw new CREPluginInternalException("An other marker with the same ID already exists in the new markerset.", t);
			}
			marker.setSet(newSet);
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_marker_normalized_world extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_normalized_world";
		}

		@Override
		public String docs() {
			return "string {setID, markerID} Returns the normalized world of the marker (used for directory and URL names in Dynmap).";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t).getNormalizedWorld(), t);
		}
	}

	@api
	public static class dm_marker_persistent extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_persistent";
		}

		@Override
		public String docs() {
			return "boolean {setID, markerID} Returns if the marker is persistent.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return CBoolean.get(CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t).isPersistent());
		}
	}

	@api
	public static class dm_marker_radius extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_radius";
		}

		@Override
		public String docs() {
			return "array {setID, markerID} Returns the radius of the marker. Only for circle markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapCircleMarker marker = CHDynmapStatic.getCircleMarker(args[0].val(), args[1].val(), t);
			CArray radius = new CArray(t);
			radius.set("x", new CDouble(marker.getRadiusX(), t), t);
			radius.set("z", new CDouble(marker.getRadiusZ(), t), t);
			return radius;
		}
	}

	@api
	public static class dm_set_marker_radius extends DynmapMarkerSetterFunction {

		@Override
		public String getName() {
			return "dm_set_marker_radius";
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRECastException.class, CREFormatException.class};
		}

		@Override
		public String docs() {
			return "void {setID, markerID, array} Sets the radius of the marker (array with \"x\" and \"z\" keys). Only for circle markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CArray radius = ArgumentValidation.getArray(args[2], t);
			CHDynmapStatic.getCircleMarker(args[0].val(), args[1].val(), t).setRadius(ArgumentValidation.getDouble(radius.get("x", t), t), ArgumentValidation.getDouble(radius.get("z", t), t));
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_marker_range_height extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_range_height";
		}

		@Override
		public String docs() {
			return "array {setID, markerID} Returns the range height of the marker. Only for area markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapAreaMarker marker = CHDynmapStatic.getAreaMarker(args[0].val(), args[1].val(), t);
			CArray range = new CArray(t);
			range.set("bottom", new CDouble(marker.getBottomY(), t), t);
			range.set("top", new CDouble(marker.getTopY(), t), t);
			return range;
		}
	}

	@api
	public static class dm_set_marker_range_height extends DynmapMarkerSetterFunction {

		@Override
		public String getName() {
			return "dm_set_marker_range_height";
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRECastException.class, CREFormatException.class};
		}

		@Override
		public String docs() {
			return "void {setID, markerID, array} Sets the range height of a marker (array with \"top\" and \"bottom\" keys). Only for area markers.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CArray range = ArgumentValidation.getArray(args[2], t);
			CHDynmapStatic.getAreaMarker(args[0].val(), args[1].val(), t).setRangeY(ArgumentValidation.getDouble(range.get("top", t), t), ArgumentValidation.getDouble(range.get("bottom", t), t));
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_marker_type extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_type";
		}

		@Override
		public String docs() {
			return "string {setID, markerID} Returns the type of the marker. Can be one of " + StringUtils.Join(MCDynmapMarkerType.values(), ", ", ", ", " or ") + ", or UNKNOWN.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t).getType().name(), t);
		}
	}

	@api
	public static class dm_marker_world extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_world";
		}

		@Override
		public String docs() {
			return "string {setID, markerID} Returns the world of the marker.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t).getWorld().getName(), t);
		}
	}

	@api
	public static class dm_marker_min_zoom extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_min_zoom";
		}

		@Override
		public String docs() {
			return "int {setID, markerID} Returns the minimum zoom level of the marker (the marker will be hidden when the zoom level is below this setting)."
				+ " -1 means no minimum. This setting bypass the value returned by the {{function|dm_markerset_min_zoom}} function.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CInt(CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t).getMinZoom(), t);
		}
	}

	@api
	public static class dm_set_marker_min_zoom extends DynmapMarkerSetterFunction {

		@Override
		public String getName() {
			return "dm_set_marker_min_zoom";
		}

		@Override
		public String docs() {
			return "void {setID, markerID, int} Sets the minimum zoom level of the marker (the marker will be hidden when the zoom level is below this setting)."
				+ " -1 means no minimum. This setting bypass the value returned by the {{function|dm_markerset_min_zoom}} function.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t).setMinZoom(ArgumentValidation.getInt32(args[2], t));
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_marker_max_zoom extends DynmapMarkerGetterFunction {

		@Override
		public String getName() {
			return "dm_marker_max_zoom";
		}

		@Override
		public String docs() {
			return "int {setID, markerID} Returns the maximum zoom level of the marker (the marker will be hidden when the zoom level is above this setting)."
				+ " -1 means no maximum. This setting bypass the value returned by the {{function|dm_markerset_max_zoom}} function.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CInt(CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t).getMaxZoom(), t);
		}
	}

	@api
	public static class dm_set_marker_max_zoom extends DynmapMarkerSetterFunction {

		@Override
		public String getName() {
			return "dm_set_marker_max_zoom";
		}

		@Override
		public String docs() {
			return "void {setID, markerID, int} Sets the maximum zoom level of the marker (the marker will be hidden when the zoom level is above this setting)."
				+ " -1 means no maximum. This setting bypass the value returned by the {{function|dm_markerset_max_zoom}} function.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHDynmapStatic.getMarker(args[0].val(), args[1].val(), t).setMaxZoom(ArgumentValidation.getInt32(args[2], t));
			return CVoid.VOID;
		}
	}
}