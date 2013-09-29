package com.hekta.chdynmap.functions;

import java.io.File;
import java.io.FileInputStream;

import com.laytonsmith.annotations.api;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Security;
import com.laytonsmith.core.Static;
import com.laytonsmith.PureUtilities.StringUtils;
import com.laytonsmith.PureUtilities.Version;

import org.dynmap.markers.MarkerIcon;

import static com.hekta.chdynmap.util.CHDynmapAPI.getDynmapIcon;
import static com.hekta.chdynmap.util.CHDynmapAPI.markerapi;
import static com.hekta.chdynmap.util.CHDynmapAPI.testDynmapIDValidity;

/*
 *
 * @author Hekta
 */
public class DynmapIcons {

	public static String docs() {
		return "A class of functions to manage the Dynmap icons.";
	}

	@api
	public static class dm_all_icons extends AbstractFunction {

		public String getName() {
			return "dm_all_icons";
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
			return "array {} Returns an array containing all the icon IDs.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			CArray iconList = new CArray(t);
			for (MarkerIcon icon : markerapi.getMarkerIcons()) {
				iconList.push(new CString(icon.getMarkerIconID(), t));
			}
			return iconList;
		}
	}

	@api
	public static class dm_create_icon extends AbstractFunction {

		public String getName() {
			return "dm_create_icon";
		}

		public Integer[] numArgs() {
			return new Integer[]{2, 3};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.IOException, ExceptionType.SecurityException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "string {newIconID, [label], imageFile} Registers an icon in Dynmap and returns its ID."
					+ " The icon ID must be unique among icons and must only contain numbers, letters, periods (.) and underscores (_)."
					+ " If the label is not given, it is equals to the icon ID."
					+ " The image file must be encoded in PNG.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			String iconID = args[0].val();
			//is the id valid ?
			testDynmapIDValidity(iconID, t);
			//already exists ?
			if (markerapi.getMarkerIcon(iconID) != null) {
				throw new ConfigRuntimeException("\"" + iconID + "\" is already an existing icon.", ExceptionType.PluginInternalException, t);
			}
			//label and image file
			String label;
			File file;
			if (args.length == 2) {
				label = iconID;
				file = new File(t.file().getParentFile(), args[1].val());
			} else {
				label = args[1].val();
				file = new File(t.file().getParentFile(), args[2].val());
			}
			if (!Security.CheckSecurity(file.getAbsolutePath())) {
				throw new ConfigRuntimeException("You do not have permission to access the file '" + file.getAbsolutePath() + "'", ExceptionType.SecurityException, t);
			}
			FileInputStream image;
			try {
				image = new FileInputStream(file);
			} catch (Exception exception) {
				throw new ConfigRuntimeException(exception.getMessage(), ExceptionType.IOException, t);
			}
			//create icon
			MarkerIcon icon = markerapi.createMarkerIcon(iconID, label, image);
			if (icon == null) {
				throw new ConfigRuntimeException("The icon creation failed.", ExceptionType.PluginInternalException, t);
			}
			return new CString(icon.getMarkerIconID(), t);
		}
	}

	@api
	public static class dm_delete_icon extends AbstractFunction {

		public String getName() {
			return "dm_delete_icon";
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
			return "void {iconID} Deletes an icon (no effect on builtin icons).";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			MarkerIcon icon = getDynmapIcon(args[0].val(), t);
			if (icon.isBuiltIn()) {
				throw new ConfigRuntimeException("Builtin icons can't be deleted.", ExceptionType.PluginInternalException, t);
			}
			icon.deleteIcon();
			return new CVoid(t);
		}
	}

	@api
	public static class dm_icon_is_builtin extends AbstractFunction {

		public String getName() {
			return "dm_icon_is_builtin";
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
			return "boolean {iconID} Returns if an icon is builtin.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			return new CBoolean(getDynmapIcon(args[0].val(), t).isBuiltIn(), t);
		}
	}

	@api
	public static class dm_set_icon_image extends AbstractFunction {

		public String getName() {
			return "dm_set_icon_image";
		}

		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.IOException, ExceptionType.SecurityException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "void {iconID, file} Sets the image of the icon (image format must be PNG).";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			MarkerIcon icon = getDynmapIcon(args[0].val(), t);
			File file = new File(t.file().getParentFile(), args[1].val());
			if (!Security.CheckSecurity(file.getAbsolutePath())) {
				throw new ConfigRuntimeException("You do not have permission to access the file '" + file.getAbsolutePath() + "'", ExceptionType.SecurityException, t);
			}
			FileInputStream image;
			try {
				image = new FileInputStream(file);
			} catch (Exception exception) {
				throw new ConfigRuntimeException(exception.getMessage(), ExceptionType.IOException, t);
			}
			icon.setMarkerIconImage(image);
			return new CVoid(t);
		}
	}

	@api
	public static class dm_icon_label extends AbstractFunction {

		public String getName() {
			return "dm_icon_label";
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
			return "string {iconID} Returns the label of the icon.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			return new CString(getDynmapIcon(args[0].val(), t).getMarkerIconLabel(), t);
		}
	}

	@api
	public static class dm_set_icon_label extends AbstractFunction {

		public String getName() {
			return "dm_set_icon_label";
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
			return "void {iconID, label} Sets the label of the icon.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			getDynmapIcon(args[0].val(), t).setMarkerIconLabel(args[1].val());
			return new CVoid(t);
		}
	}

	@api
	public static class dm_icon_size extends AbstractFunction {

		public String getName() {
			return "dm_icon_size";
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
			return "string {iconID} Returns the size of the icon. Size can be one of " + StringUtils.Join(MarkerIcon.MarkerSize.values(), ", ", ", or ", " or ") + ".";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			return new CString(getDynmapIcon(args[0].val(), t).getMarkerIconSize().getSize(), t);
		}
	}
}
