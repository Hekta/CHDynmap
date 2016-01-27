package com.hekta.chdynmap.core.functions;

import com.hekta.chdynmap.abstraction.MCDynmapIcon;
import com.hekta.chdynmap.abstraction.enums.MCDynmapIconSize;
import com.hekta.chdynmap.core.CHDynmapStatic;
import com.laytonsmith.PureUtilities.Common.StringUtils;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.Security;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREIOException;
import com.laytonsmith.core.exceptions.CRE.CREInvalidPluginException;
import com.laytonsmith.core.exceptions.CRE.CRENotFoundException;
import com.laytonsmith.core.exceptions.CRE.CREPluginInternalException;
import com.laytonsmith.core.exceptions.CRE.CRESecurityException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author Hekta
 */
public class DynmapIcons {

	public static String docs() {
		return "A class of functions to manage the Dynmap icons.";
	}

	public static abstract class DynmapIconFunction extends AbstractFunction {

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

	public static abstract class DynmapIconGetterFunction extends DynmapIconFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRENotFoundException.class};
		}
	}

	@api
	public static class dm_all_icons extends DynmapIconFunction {

		@Override
		public String getName() {
			return "dm_all_icons";
		}

		@Override
		public Integer[] numArgs() {
			return new Integer[]{0};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class};
		}

		@Override
		public String docs() {
			return "array {} Returns an array containing all the icon IDs.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CArray iconArray = new CArray(t);
			for (MCDynmapIcon icon : CHDynmapStatic.getMarkerAPI(t).getIcons()) {
				iconArray.push(new CString(icon.getId(), t), t);
			}
			return iconArray;
		}
	}

	@api
	public static class dm_create_icon extends DynmapIconFunction {

		@Override
		public String getName() {
			return "dm_create_icon";
		}

		@Override
		public Integer[] numArgs() {
			return new Integer[]{2, 3};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRENotFoundException.class, CREIOException.class, CRESecurityException.class};
		}

		@Override
		public String docs() {
			return "string {newIconID, [label], imageFile} Registers an icon in Dynmap and returns its ID."
					+ " The icon ID must be unique among icons and must only contain numbers, letters, periods (.) and underscores (_)."
					+ " If the label is not given, it is equals to the icon ID."
					+ " The image file must be encoded in PNG.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			String iconID = args[0].val();
			//is the id valid ?
			CHDynmapStatic.testDynmapIDValidity(iconID, t);
			//already exists ?
			if (CHDynmapStatic.getMarkerAPI(t).getIcon(iconID) != null) {
				throw new CREPluginInternalException("\"" + iconID + "\" is already an existing icon.", t);
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
				throw new CRESecurityException("You do not have permission to access the file '" + file.getAbsolutePath() + "'", t);
			}
			FileInputStream image;
			try {
				image = new FileInputStream(file);
			} catch (FileNotFoundException exception) {
				throw new CREIOException(exception.getMessage(), t);
			}
			//create icon
			MCDynmapIcon icon = CHDynmapStatic.getMarkerAPI(t).createIcon(iconID, label, image);
			if (icon != null) {
				return new CString(icon.getId(), t);
			} else {
				throw new CREPluginInternalException("The icon creation failed.", t);
			}
		}
	}

	@api
	public static class dm_delete_icon extends DynmapIconFunction {

		@Override
		public String getName() {
			return "dm_delete_icon";
		}

		@Override
		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRENotFoundException.class};
		}

		@Override
		public String docs() {
			return "void {iconID} Deletes an icon (can't be used on builtin icons).";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapIcon icon = CHDynmapStatic.getIcon(args[0].val(), t);
			if (icon.isBuiltIn()) {
				throw new CREPluginInternalException("Builtin icons can't be deleted.", t);
			} else {
				icon.delete();
			}
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_icon_is_builtin extends DynmapIconGetterFunction {

		@Override
		public String getName() {
			return "dm_icon_is_builtin";
		}

		@Override
		public String docs() {
			return "boolean {iconID} Returns if an icon is builtin.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return CBoolean.get(CHDynmapStatic.getIcon(args[0].val(), t).isBuiltIn());
		}
	}

	@api
	public static class dm_set_icon_image extends DynmapIconFunction {

		@Override
		public String getName() {
			return "dm_set_icon_image";
		}

		@Override
		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CREPluginInternalException.class, CRENotFoundException.class, CREIOException.class, CRESecurityException.class};
		}

		@Override
		public String docs() {
			return "void {iconID, file} Sets the image of the icon (image format must be PNG).";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCDynmapIcon icon = CHDynmapStatic.getIcon(args[0].val(), t);
			File file = new File(t.file().getParentFile(), args[1].val());
			if (!Security.CheckSecurity(file.getAbsolutePath())) {
				throw new CRESecurityException("You do not have permission to access the file '" + file.getAbsolutePath() + "'", t);
			}
			FileInputStream image;
			try {
				image = new FileInputStream(file);
			} catch (FileNotFoundException exception) {
				throw new CREIOException(exception.getMessage(), t);
			}
			icon.setImage(image);
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_icon_label extends DynmapIconGetterFunction {

		@Override
		public String getName() {
			return "dm_icon_label";
		}

		@Override
		public String docs() {
			return "string {iconID} Returns the label of the icon.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHDynmapStatic.getIcon(args[0].val(), t).getLabel(), t);
		}
	}

	@api
	public static class dm_set_icon_label extends DynmapIconFunction {

		@Override
		public String getName() {
			return "dm_set_icon_label";
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
			return "void {iconID, label} Sets the label of the icon.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHDynmapStatic.getIcon(args[0].val(), t).setLabel(args[1].val());
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_icon_size extends DynmapIconGetterFunction {

		@Override
		public String getName() {
			return "dm_icon_size";
		}

		@Override
		public String docs() {
			return "string {iconID} Returns the size of the icon. Size can be one of " + StringUtils.Join(MCDynmapIconSize.values(), ", ", ", or ", " or ") + ".";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHDynmapStatic.getIcon(args[0].val(), t).getSize().name(), t);
		}
	}
}