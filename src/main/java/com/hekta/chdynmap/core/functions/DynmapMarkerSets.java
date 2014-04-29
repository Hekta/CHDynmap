package com.hekta.chdynmap.core.functions;

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

import com.hekta.chdynmap.abstraction.MCDynmapIcon;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerSet;
import com.hekta.chdynmap.core.CHDynmapStatic;

/**
 *
 * @author Hekta
 */
public class DynmapMarkerSets {

	public static String docs() {
		return "A class of functions to manage the Dynmap markersets.";
	}

	public static abstract class DynmapMarkerSetFunction extends AbstractFunction {

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}
	}

	public static abstract class DynmapMarkerSetGetterFunction extends DynmapMarkerSetFunction {

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.NotFoundException};
		}
	}

	public static abstract class DynmapMarkerSetSetterFunction extends DynmapMarkerSetFunction {

		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.NotFoundException, ExceptionType.CastException};
		}
	}

	@api
	public static class dm_all_markersets extends DynmapMarkerSetFunction {

		public String getName() {
			return "dm_all_markersets";
		}

		public Integer[] numArgs() {
			return new Integer[]{0};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public String docs() {
			return "array {} Returns an array of all markersets ID.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CArray setArray = new CArray(t);
			for (MCDynmapMarkerSet set : CHDynmapStatic.getMarkerAPI(t).getMarkerSets()) {
				setArray.push(new CString(set.getId(), t));
			}
			return setArray;
		}
	}

	@api
	public static class dm_create_markerset extends DynmapMarkerSetFunction {

		public String getName() {
			return "dm_create_markerset";
		}

		public Integer[] numArgs() {
			return new Integer[]{1, 2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.CastException, ExceptionType.FormatException};
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

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			String setID = args[0].val();
			//is the id valid ?
			CHDynmapStatic.testDynmapIDValidity(setID, t);
			//already exists ?
			if (CHDynmapStatic.getMarkerAPI(t).getMarkerSet(setID) != null) {
				throw new ConfigRuntimeException("\"" + setID + "\" is already an existing markerset.", ExceptionType.PluginInternalException, t);
			}
			//create the option array
			CArray optionArray;
			if (args.length == 1) {
				optionArray = new CArray(t);
			} else {
				optionArray = Static.getArray(args[1], t);
			}
			Set<String> keys = optionArray.stringKeySet();
			//set optional values
			//allowed_icons
			HashSet<MCDynmapIcon> allowedIcons;
			if (keys.contains("allowed_icons")) {
				CArray givenAllowedIcons = Static.getArray(optionArray.get("allowed_icons"), t);
				if (givenAllowedIcons.inAssociativeMode()) {
					throw new ConfigRuntimeException("The array must not be associative.", ExceptionType.CastException, t);
				}
				allowedIcons = new HashSet<MCDynmapIcon>();
				for (Construct icon : givenAllowedIcons.asList()) {
					allowedIcons.add(CHDynmapStatic.getIcon(icon.val(), t));
				}
			} else {
				allowedIcons = null;
			}
			//label
			String label;
			if (keys.contains("label")) {
				label = optionArray.get("label").val();
			} else {
				label = setID;
			}
			//persistent
			boolean persistent;
			if (keys.contains("persistent")) {
				persistent = Static.getBoolean(optionArray.get("persistent"));
			} else {
				persistent = false;
			}
			//create marker set
			MCDynmapMarkerSet set = CHDynmapStatic.getMarkerAPI(t).createMarkerSet(setID, label, allowedIcons, persistent);
			if (set == null) {
				throw new ConfigRuntimeException("The markerset creation failed.", ExceptionType.PluginInternalException, t);
			}
			return new CString(set.getId(), t);
		}
	}

	@api
	public static class dm_delete_markerset extends DynmapMarkerSetFunction {

		public String getName() {
			return "dm_delete_markerset";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public String docs() {
			return "void {setID} Deletes a marker set.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHDynmapStatic.getMarkerSet(args[0].val(), t).delete();
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_markerset_allowed_icons extends DynmapMarkerSetGetterFunction {

		public String getName() {
			return "dm_markerset_allowed_icons";
		}

		public String docs() {
			return "array {setID} Returns an array of icons ID allowed for the set (if restricted, else returns null and any icon can be used in set).";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Set<MCDynmapIcon> iconSet = CHDynmapStatic.getMarkerSet(args[0].val(), t).getAllowedIcons();
			if (iconSet == null) {
				return CNull.NULL;
			} else {
				CArray allowedIcons = new CArray(t);
				for(MCDynmapIcon allowedIcon : iconSet){
					allowedIcons.push(new CString(allowedIcon.getId(), t));
				}
				return allowedIcons;
			}
		}
	}

	@api
	public static class dm_set_icon_allowed_for_marketset extends DynmapMarkerSetFunction {

		public String getName() {
			return "dm_set_icon_allowed_for_marketset";
		}

		public Integer[] numArgs() {
			return new Integer[]{3};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public String docs() {
			return "void {setID, iconID, boolean} Sets if an icon is allowed for the markerset (the marketset must have been created restricted).";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarkerSet set = CHDynmapStatic.getMarkerSet(args[0].val(), t);
			if (set.getAllowedIcons() == null) {
				throw new ConfigRuntimeException("The markerset is not restricted.", ExceptionType.PluginInternalException, t);
			}
			MCDynmapIcon icon = CHDynmapStatic.getIcon(args[1].val(), t);
			if (Static.getBoolean(args[2])) {
				if (set.iconIsAllowed(icon)) {
					throw new ConfigRuntimeException("The icon is already allowed for the marketset.", ExceptionType.PluginInternalException, t);
				}
				set.addAllowedIcon(icon);	
			} else {
				if (!set.iconIsAllowed(icon)) {
					throw new ConfigRuntimeException("The icon is already not allowed for the marketset.", ExceptionType.PluginInternalException, t);
				}
				set.removeAllowedIcon(icon);
			}
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_markerset_default_icon extends DynmapMarkerSetGetterFunction {

		public String getName() {
			return "dm_markerset_default_icon";
		}

		public String docs() {
			return "string {setID} Returns the default icon ID for the markers added to this set.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHDynmapStatic.getMarkerSet(args[0].val(), t).getDefaultIcon().getId(), t);
		}
	}

	@api
	public static class dm_set_markerset_default_icon extends DynmapMarkerSetSetterFunction {

		public String getName() {
			return "dm_set_markerset_default_icon";
		}

		public String docs() {
			return "void {setID, iconID} Sets the default icon of the markerset.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarkerSet set = CHDynmapStatic.getMarkerSet(args[0].val(), t);
			MCDynmapIcon icon = CHDynmapStatic.getIcon(args[1].val(), t);
			if (!set.iconIsAllowed(icon)) {
				throw new ConfigRuntimeException("The icon is not allowed for the marketset.", ExceptionType.PluginInternalException, t);
			}
			set.setDefaultIcon(icon);
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_markerset_hide_by_default extends DynmapMarkerSetGetterFunction {

		public String getName() {
			return "dm_markerset_hide_by_default";
		}

		public String docs() {
			return "boolean {setID} Returns if the markerset is hidden by default.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CBoolean(CHDynmapStatic.getMarkerSet(args[0].val(), t).isHiddenByDefault(), t);
		}
	}

	@api
	public static class dm_set_markerset_hide_by_default extends DynmapMarkerSetSetterFunction {

		public String getName() {
			return "dm_set_markerset_hide_by_default";
		}

		public String docs() {
			return "void {setID, boolean} Sets if the markerset is hide by default.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHDynmapStatic.getMarkerSet(args[0].val(), t).setHiddenByDefault(Static.getBoolean(args[1]));
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_markerset_icons_in_use extends DynmapMarkerSetGetterFunction {

		public String getName() {
			return "dm_markerset_icons_in_use";
		}

		public String docs() {
			return "array {setID} Sets the default icon of the markerset.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CArray iconsInUse = new CArray(t);
			for (MCDynmapIcon icon : CHDynmapStatic.getMarkerSet(args[0].val(), t).getIconsInUse()) {
				iconsInUse.push(new CString(icon.getId(), t));
			}
			return iconsInUse;
		}
	}

	@api
	public static class dm_markerset_label extends DynmapMarkerSetGetterFunction {

		public String getName() {
			return "dm_markerset_label";
		}

		public String docs() {
			return "string {setID} Returns the markerset label.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHDynmapStatic.getMarkerSet(args[0].val(), t).getLabel(), t);
		}
	}

	@api
	public static class dm_set_markerset_label extends DynmapMarkerSetSetterFunction {

		public String getName() {
			return "dm_set_markerset_label";
		}

		public String docs() {
			return "void {setID, label} Sets the label of the markerset.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHDynmapStatic.getMarkerSet(args[0].val(), t).setLabel(args[1].val());
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_markerset_layer_priority extends DynmapMarkerSetGetterFunction {

		public String getName() {
			return "dm_markerset_layer_priority";
		}

		public String docs() {
			return "integer {setID} Returns the markerset layer ordering priority (0=default, low before high in layer order).";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CInt(CHDynmapStatic.getMarkerSet(args[0].val(), t).getLayerPriority(), t);
		}
	}

	@api
	public static class dm_set_markerset_layer_priority extends DynmapMarkerSetSetterFunction {

		public String getName() {
			return "dm_set_markerset_layer_priority";
		}

		public String docs() {
			return "void {setID, integer} Sets the layer priority of the markerset (0=default, low before high in layer order).";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHDynmapStatic.getMarkerSet(args[0].val(), t).setLayerPriority(Static.getInt32(args[1], t));
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_markerset_min_zoom extends DynmapMarkerSetGetterFunction {

		public String getName() {
			return "dm_markerset_min_zoom";
		}

		public String docs() {
			return "integer {setID} Returns the min zoom-in for display of layer (hide when zoom is below this setting, 0 = top, default).";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CInt(CHDynmapStatic.getMarkerSet(args[0].val(), t).getMinZoom(), t);
		}
	}

	@api
	public static class dm_set_markerset_min_zoom extends DynmapMarkerSetSetterFunction {

		public String getName() {
			return "dm_set_markerset_min_zoom";
		}

		public String docs() {
			return "void {setID, integer} Sets the min zoom of the markerset (0 = top, default).";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHDynmapStatic.getMarkerSet(args[0].val(), t).setMinZoom(Static.getInt32(args[1], t));
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_markerset_persistent extends DynmapMarkerSetGetterFunction {

		public String getName() {
			return "dm_markerset_persistent";
		}

		public String docs() {
			return "boolean {setID} Returns if the markerset is persistent and can contain persistent markers.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CBoolean(CHDynmapStatic.getMarkerSet(args[0].val(), t).isPersistent(), t);
		}
	}

	@api
	public static class dm_markerset_show_labels extends DynmapMarkerSetGetterFunction {

		public String getName() {
			return "dm_markerset_show_labels";
		}

		public String docs() {
			return "boolean {setID} Returns if labels are shown (if false, hide, show on hover, if null, use global default).";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarkerSet set = CHDynmapStatic.getMarkerSet(args[0].val(), t);
			if (set.labelIsShown() == null) {
				return CNull.NULL;
			} else {
				return new CBoolean(set.labelIsShown(), t);
			}
		}
	}

	@api
	public static class dm_set_markerset_show_labels extends DynmapMarkerSetSetterFunction {

		public String getName() {
			return "dm_set_markerset_show_labels";
		}

		public String docs() {
			return "void {setID, mixed} Sets if labels are shown (if false, hide, show on hover, if null, use global default).";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapMarkerSet set = CHDynmapStatic.getMarkerSet(args[0].val(), t);
			Construct showLabels = Static.resolveConstruct(args[1].val(), t);
			if (showLabels instanceof CBoolean) {
				set.setlabelIsShown(Static.getBoolean(showLabels));
			} else if (showLabels instanceof CNull) {
				set.setlabelIsShown(null);
			} else {
				throw new ConfigRuntimeException("Value should be a boolean or null.", ExceptionType.CastException, t);
			}
			return CVoid.VOID;
		}
	}
}