package com.hekta.chdynmap.core.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.laytonsmith.annotations.api;
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
import com.laytonsmith.core.Security;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.PureUtilities.Common.StringUtils;

import com.hekta.chdynmap.abstraction.MCDynmapIcon;
import com.hekta.chdynmap.abstraction.enums.MCDynmapIconSize;
import com.hekta.chdynmap.core.CHDynmapStatic;

/**
 *
 * @author Hekta
 */
public class DynmapIcons {

	public static String docs() {
		return "A class of functions to manage the Dynmap icons.";
	}

	public static abstract class DynmapIconFunction extends AbstractFunction {

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

	public static abstract class DynmapIconGetterFunction extends DynmapIconFunction {

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.NotFoundException};
		}
	}

	@api
	public static class dm_all_icons extends DynmapIconFunction {

		public String getName() {
			return "dm_all_icons";
		}

		public Integer[] numArgs() {
			return new Integer[]{0};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException};
		}

		public String docs() {
			return "array {} Returns an array containing all the icon IDs.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CArray iconArray = new CArray(t);
			for (MCDynmapIcon icon : CHDynmapStatic.getMarkerAPI(t).getIcons()) {
				iconArray.push(new CString(icon.getId(), t));
			}
			return iconArray;
		}
	}

	@api
	public static class dm_create_icon extends DynmapIconFunction {

		public String getName() {
			return "dm_create_icon";
		}

		public Integer[] numArgs() {
			return new Integer[]{2, 3};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.NotFoundException, ExceptionType.IOException, ExceptionType.SecurityException};
		}

		public String docs() {
			return "string {newIconID, [label], imageFile} Registers an icon in Dynmap and returns its ID."
					+ " The icon ID must be unique among icons and must only contain numbers, letters, periods (.) and underscores (_)."
					+ " If the label is not given, it is equals to the icon ID."
					+ " The image file must be encoded in PNG.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			String iconID = args[0].val();
			//is the id valid ?
			CHDynmapStatic.testDynmapIDValidity(iconID, t);
			//already exists ?
			if (CHDynmapStatic.getMarkerAPI(t).getIcon(iconID) != null) {
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
			} catch (FileNotFoundException exception) {
				throw new ConfigRuntimeException(exception.getMessage(), ExceptionType.IOException, t);
			}
			//create icon
			MCDynmapIcon icon = CHDynmapStatic.getMarkerAPI(t).createIcon(iconID, label, image);
			if (icon != null) {
				return new CString(icon.getId(), t);
			} else {
				throw new ConfigRuntimeException("The icon creation failed.", ExceptionType.PluginInternalException, t);
			}
		}
	}

	@api
	public static class dm_delete_icon extends DynmapIconFunction {

		public String getName() {
			return "dm_delete_icon";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.NotFoundException};
		}

		public String docs() {
			return "void {iconID} Deletes an icon (can't be used on builtin icons).";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapIcon icon = CHDynmapStatic.getIcon(args[0].val(), t);
			if (icon.isBuiltIn()) {
				throw new ConfigRuntimeException("Builtin icons can't be deleted.", ExceptionType.PluginInternalException, t);
			} else {
				icon.delete();
			}
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_icon_is_builtin extends DynmapIconGetterFunction {

		public String getName() {
			return "dm_icon_is_builtin";
		}

		public String docs() {
			return "boolean {iconID} Returns if an icon is builtin.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CBoolean(CHDynmapStatic.getIcon(args[0].val(), t).isBuiltIn(), t);
		}
	}

	@api
	public static class dm_set_icon_image extends DynmapIconFunction {

		public String getName() {
			return "dm_set_icon_image";
		}

		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.NotFoundException, ExceptionType.IOException, ExceptionType.SecurityException};
		}

		public String docs() {
			return "void {iconID, file} Sets the image of the icon (image format must be PNG).";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapIcon icon = CHDynmapStatic.getIcon(args[0].val(), t);
			File file = new File(t.file().getParentFile(), args[1].val());
			if (!Security.CheckSecurity(file.getAbsolutePath())) {
				throw new ConfigRuntimeException("You do not have permission to access the file '" + file.getAbsolutePath() + "'", ExceptionType.SecurityException, t);
			}
			FileInputStream image;
			try {
				image = new FileInputStream(file);
			} catch (FileNotFoundException exception) {
				throw new ConfigRuntimeException(exception.getMessage(), ExceptionType.IOException, t);
			}
			icon.setImage(image);
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_icon_label extends DynmapIconGetterFunction {

		public String getName() {
			return "dm_icon_label";
		}

		public String docs() {
			return "string {iconID} Returns the label of the icon.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHDynmapStatic.getIcon(args[0].val(), t).getLabel(), t);
		}
	}

	@api
	public static class dm_set_icon_label extends DynmapIconFunction {

		public String getName() {
			return "dm_set_icon_label";
		}

		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.NotFoundException};
		}

		public String docs() {
			return "void {iconID, label} Sets the label of the icon.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHDynmapStatic.getIcon(args[0].val(), t).setLabel(args[1].val());
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_icon_size extends DynmapIconGetterFunction {

		public String getName() {
			return "dm_icon_size";
		}

		public String docs() {
			return "string {iconID} Returns the size of the icon. Size can be one of " + StringUtils.Join(MCDynmapIconSize.values(), ", ", ", or ", " or ") + ".";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHDynmapStatic.getIcon(args[0].val(), t).getSize().name(), t);
		}
	}
}