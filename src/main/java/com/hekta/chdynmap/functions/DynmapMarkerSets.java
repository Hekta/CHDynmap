package com.hekta.chdynmap.functions;

import java.util.HashSet;
import java.util.Set;

import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CNull;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;
import com.laytonsmith.core.Static;
import com.laytonsmith.PureUtilities.Version;

import org.dynmap.markers.MarkerSet;
import org.dynmap.markers.MarkerIcon;

import static com.hekta.chdynmap.util.CHDynmapAPI.getDynmapIcon;
import static com.hekta.chdynmap.util.CHDynmapAPI.getDynmapMarkerSet;
import static com.hekta.chdynmap.util.CHDynmapAPI.markerapi;
import static com.hekta.chdynmap.util.CHDynmapAPI.testDynmapIDValidity;

/*
 *
 * @author Hekta
 */
public class DynmapMarkerSets {

	public static String docs() {
		return "A class of functions to manage the Dynmap markersets.";
	}

	@api
	public static class dm_all_markersets extends AbstractFunction {

		public String getName() {
			return "dm_all_markersets";
		}

		public Integer[] numArgs() {
			return new Integer[]{0};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "array {} Returns an array of all markersets ID.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			CArray setList = new CArray(t);
			for (MarkerSet set : markerapi.getMarkerSets()) {
				setList.push(new CString(set.getMarkerSetID(), t));
			}
			return setList;
		}
	}

	@api
	public static class dm_create_markerset extends AbstractFunction {

		public String getName() {
			return "dm_create_markerset";
		}

		public Integer[] numArgs() {
			return new Integer[]{1, 2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.CastException, ExceptionType.FormatException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "string {newSetID, [optionArray]} Creates a markerset and returns its ID."
					+ " ---- The ID must be unique among markersets and must only contain numbers, letters, periods (.) and underscores (_)."
					+ " The option array is associative and not required, and all its keys are optional."
					+ " <li>KEY - DEFAULT - DESCRIPTION - COMMENT</li>"
					+ " <li>allowed_icons - null - an array of icons allowed in the markerset, null to unrestrict - restriction status can not be changed later, only the list of icons allowed could be modified</li>"
					+ " <li>label - setID - the markerset label</li>"
					+ " <li>persistent - false - sets if the markerset is persistent and can contain persistent markers - can not be changed later</li>";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			String setID = args[0].val();
			//is the id valid ?
			testDynmapIDValidity(setID, t);
			//already exists ?
			if (markerapi.getMarkerSet(setID) != null) {
				throw new ConfigRuntimeException("\"" + setID + "\" is already an existing markerset.", ExceptionType.PluginInternalException, t);
			}
			//create the option array
			CArray optionArray;
			if (args.length == 1) {
				optionArray = new CArray(t);
			} else {
				optionArray = Static.getArray(args[1], t);
			}
			Set keys = optionArray.keySet();
			//set optional values
			//allowed_icons
			HashSet<MarkerIcon> allowedIcons;
			if (keys.contains("allowed_icons")) {
				CArray givenAllowedIcons = Static.getArray(optionArray.get("allowed_icons", t), t);
				if (givenAllowedIcons.inAssociativeMode()) {
					throw new ConfigRuntimeException("The array must not be associative.", ExceptionType.CastException, t);
				}
				allowedIcons = new HashSet<MarkerIcon>();
				for (Construct icon : givenAllowedIcons.asList()) {
					allowedIcons.add(getDynmapIcon(icon.val(), t));
				}
			} else {
				allowedIcons = null;
			}
			//label
			String label;
			if (keys.contains("label")) {
				label = optionArray.get("label", t).val();
			} else {
				label = setID;
			}
			//persistent
			boolean persistent;
			if (keys.contains("persistent")) {
				persistent = Static.getBoolean(optionArray.get("persistent", t));
			} else {
				persistent = false;
			}
			//create marker set
			MarkerSet set = markerapi.createMarkerSet(setID, label, allowedIcons, persistent);
			if (set == null) {
				throw new ConfigRuntimeException("The markerset creation failed.", ExceptionType.PluginInternalException, t);
			}
			return new CString(set.getMarkerSetID(), t);
		}
	}

	@api
	public static class dm_delete_markerset extends AbstractFunction {

		public String getName() {
			return "dm_delete_markerset";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "void {setID} Deletes a marker set.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			getDynmapMarkerSet(args[0].val(), t).deleteMarkerSet();
			return new CVoid(t);
		}
	}

	@api
	public static class dm_markerset_allowed_icons extends AbstractFunction {

		public String getName() {
			return "dm_markerset_allowed_icons";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "array {setID} Returns an array of icons ID allowed for the set (if restricted, else returns null and any icon can be used in set).";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			Set<MarkerIcon> iconList = getDynmapMarkerSet(args[0].val(), t).getAllowedMarkerIcons();
			if (iconList == null) {
				return new CNull(t);
			} else {
				CArray allowed_icons = new CArray(t);
				for(MarkerIcon allowedIcon : iconList){
					allowed_icons.push(new CString(allowedIcon.getMarkerIconID(), t));
				}
				return allowed_icons;
			}
		}
	}

	@api
	public static class dm_set_icon_allowed_for_marketset extends AbstractFunction {

		public String getName() {
			return "dm_set_icon_allowed_for_marketset";
		}

		public Integer[] numArgs() {
			return new Integer[]{3};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "void {setID, iconID, boolean} Sets if an icon is allowed for the markerset (the marketset must have been created restricted).";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			MarkerSet set = getDynmapMarkerSet(args[0].val(), t);
			if (set.getAllowedMarkerIcons() == null) {
				throw new ConfigRuntimeException("The markerset is not restricted.", ExceptionType.PluginInternalException, t);
			}
			MarkerIcon icon = getDynmapIcon(args[1].val(), t);
			if (Static.getBoolean(args[2])) {
				if (set.isAllowedMarkerIcon(icon)) {
					throw new ConfigRuntimeException("The icon is already allowed for the marketset.", ExceptionType.PluginInternalException, t);
				}
				set.addAllowedMarkerIcon(icon);	
			} else {
				if (!set.isAllowedMarkerIcon(icon)) {
					throw new ConfigRuntimeException("The icon is already not allowed for the marketset.", ExceptionType.PluginInternalException, t);
				}
				set.removeAllowedMarkerIcon(icon);
			}
			return new CVoid(t);
		}
	}

	@api
	public static class dm_markerset_default_icon extends AbstractFunction {

		public String getName() {
			return "dm_markerset_default_icon";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "string {setID} Returns the default icon ID for the markers added to this set.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			return new CString(getDynmapMarkerSet(args[0].val(), t).getDefaultMarkerIcon().getMarkerIconID(), t);
		}
	}

	@api
	public static class dm_set_markerset_default_icon extends AbstractFunction {

		public String getName() {
			return "dm_set_markerset_default_icon";
		}

		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "void {setID, iconID} Sets the default icon of the markerset.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			MarkerSet set = getDynmapMarkerSet(args[0].val(), t);
			MarkerIcon icon = getDynmapIcon(args[1].val(), t);
			if (!set.isAllowedMarkerIcon(icon)) {
				throw new ConfigRuntimeException("The icon is not allowed for the marketset.", ExceptionType.PluginInternalException, t);
			}
			set.setDefaultMarkerIcon(icon);
			return new CVoid(t);
		}
	}

	@api
	public static class dm_markerset_hide_by_default extends AbstractFunction {

		public String getName() {
			return "dm_markerset_hide_by_default";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "boolean {setID} Returns if the markerset is hidden by default.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			return new CBoolean(getDynmapMarkerSet(args[0].val(), t).getHideByDefault(), t);
		}
	}

	@api
	public static class dm_set_markerset_hide_by_default extends AbstractFunction {

		public String getName() {
			return "dm_set_markerset_hide_by_default";
		}

		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "void {setID, boolean} Sets if the markerset is hide by default.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			getDynmapMarkerSet(args[0].val(), t).setHideByDefault(Static.getBoolean(args[1]));
			return new CVoid(t);
		}
	}

	@api
	public static class dm_markerset_icons_in_use extends AbstractFunction {

		public String getName() {
			return "dm_markerset_icons_in_use";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "array {setID} Sets the default icon of the markerset.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			CArray icons_in_use = new CArray(t);
			for (MarkerIcon icon : getDynmapMarkerSet(args[0].val(), t).getMarkerIconsInUse()) {
				icons_in_use.push(new CString(icon.getMarkerIconID(), t));
			}
			return icons_in_use;
		}
	}

	@api
	public static class dm_markerset_label extends AbstractFunction {

		public String getName() {
			return "dm_markerset_label";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "string {setID} Returns the markerset label.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			return new CString(getDynmapMarkerSet(args[0].val(), t).getMarkerSetLabel(), t);
		}
	}

	@api
	public static class dm_set_markerset_label extends AbstractFunction {

		public String getName() {
			return "dm_set_markerset_label";
		}

		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "void {setID, label} Sets the label of the markerset.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			getDynmapMarkerSet(args[0].val(), t).setMarkerSetLabel(args[1].val());
			return new CVoid(t);
		}
	}

	@api
	public static class dm_markerset_layer_priority extends AbstractFunction {

		public String getName() {
			return "dm_markerset_layer_priority";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "integer {setID} Returns the markerset layer ordering priority (0=default, low before high in layer order).";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			return new CInt(getDynmapMarkerSet(args[0].val(), t).getLayerPriority(), t);
		}
	}

	@api
	public static class dm_set_markerset_layer_priority extends AbstractFunction {

		public String getName() {
			return "dm_set_markerset_layer_priority";
		}

		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.CastException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "void {setID, integer} Sets the layer priority of the markerset (0=default, low before high in layer order).";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			getDynmapMarkerSet(args[0].val(), t).setLayerPriority(Static.getInt32(args[1], t));
			return new CVoid(t);
		}
	}

	@api
	public static class dm_markerset_min_zoom extends AbstractFunction {

		public String getName() {
			return "dm_markerset_min_zoom";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "integer {setID} Returns the min zoom-in for display of layer (hide when zoom is below this setting, 0 = top, default).";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			return new CInt(getDynmapMarkerSet(args[0].val(), t).getMinZoom(), t);
		}
	}

	@api
	public static class dm_set_markerset_min_zoom extends AbstractFunction {

		public String getName() {
			return "dm_set_markerset_min_zoom";
		}

		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.CastException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "void {setID, integer} Sets the min zoom of the markerset (0 = top, default).";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			getDynmapMarkerSet(args[0].val(), t).setMinZoom(Static.getInt32(args[1], t));
			return new CVoid(t);
		}
	}

	@api
	public static class dm_markerset_persistent extends AbstractFunction {

		public String getName() {
			return "dm_markerset_persistent";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "boolean {setID} Returns if the markerset is persistent and can contain persistent markers.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			return new CBoolean(getDynmapMarkerSet(args[0].val(), t).isMarkerSetPersistent(), t);
		}
	}

	@api
	public static class dm_markerset_show_labels extends AbstractFunction {

		public String getName() {
			return "dm_markerset_show_labels";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "boolean {setID} Returns if labels are shown (if false, hide, show on hover, if null, use global default).";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			MarkerSet set = getDynmapMarkerSet(args[0].val(), t);
			if (set.getLabelShow() == null) {
				return new CNull(t);
			} else {
				return new CBoolean(set.getLabelShow(), t);
			}
		}
	}

	@api
	public static class dm_set_markerset_show_labels extends AbstractFunction {

		public String getName() {
			return "dm_set_markerset_show_labels";
		}

		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "void {setID, mixed} Sets if labels are shown (if false, hide, show on hover, if null, use global default).";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			MarkerSet set = getDynmapMarkerSet(args[0].val(), t);
			Construct showLabels = Static.resolveConstruct(args[1].val(), t);
			if (showLabels instanceof CBoolean) {
				set.setLabelShow(Static.getBoolean(showLabels));
			} else if (showLabels instanceof CNull) {
				set.setLabelShow(null);
			} else {
				throw new ConfigRuntimeException("Value should be a boolean or null.", ExceptionType.CastException, t);
			}
			return new CVoid(t);
		}
	}
}
