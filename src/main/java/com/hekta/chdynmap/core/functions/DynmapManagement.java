package com.hekta.chdynmap.core.functions;

import com.laytonsmith.annotations.api;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.MCWorld;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.CommandHelperEnvironment;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.PureUtilities.Version;

import com.hekta.chdynmap.core.CHDynmapStatic;

/**
 *
 * @author Hekta
 */
public class DynmapManagement {

	public static String docs() {
		return "A class of functions that allows to manage and use general features of Dynmap.";
	}

	@api
	public static class dm_broadcast_to_web extends AbstractFunction {

		public String getName() {
			return "dm_broadcast_to_web";
		}

		public Integer[] numArgs() {
			return new Integer[]{1, 2};
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
			return "void {message, [sender]} Send a generic message to all web users,"
					+ " sender is the label for the sender of the message, could be null,"
					+ " message is the message to be sent.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			String senderLabel;
			if (args.length == 2) {
				senderLabel = args[1].val();
			} else {
				senderLabel = "CommandHelper";
			}
			CHDynmapStatic.getDynmapAPI(t).sendBroadcastToWeb(senderLabel, args[0].val());
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_full_radius_renders_paused extends AbstractFunction {

		public String getName() {
			return "dm_full_radius_renders_paused";
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
			return "boolean {} Returns if full and radius renders are paused.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CBoolean(CHDynmapStatic.getDynmapAPI(t).getPauseFullRadiusRenders(), t);
		}
	}

	@api
	public static class dm_set_full_radius_renders_paused extends AbstractFunction {

		public String getName() {
			return "dm_set_full_radius_renders_paused";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
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
			return "void {boolean} Sets if full and radius renders are paused.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHDynmapStatic.getDynmapAPI(t).setPauseFullRadiusRenders(Static.getBoolean(args[0]));
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_update_renders_paused extends AbstractFunction {

		public String getName() {
			return "dm_update_renders_paused";
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
			return "boolean {} Returns if update renders are paused.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CBoolean(CHDynmapStatic.getDynmapAPI(t).getPauseUpdateRenders(), t);
		}
	}

	@api
	public static class dm_set_update_renders_paused extends AbstractFunction {

		public String getName() {
			return "dm_set_update_renders_paused";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
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
			return "void {boolean} Sets if update renders are paused.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHDynmapStatic.getDynmapAPI(t).setPauseUpdateRenders(Static.getBoolean(args[0]));
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_set_chat_to_web_enabled extends AbstractFunction {

		public String getName() {
			return "dm_set_chat_to_web_enabled";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
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
			return "void {boolean} Sets if chat to web processing is enabled or not.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHDynmapStatic.getDynmapAPI(t).setChatToWebProcessingEnabled(Static.getBoolean(args[0]));
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_render_block extends AbstractFunction {

		public String getName() {
			return "dm_render_block";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.CastException, ExceptionType.FormatException, ExceptionType.InvalidWorldException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "void {locationArray} Trigger update on tiles at the given block location.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCPlayer player = environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
			MCWorld world;
			if (player == null) {
				world = null;
			} else {
				world = player.getWorld();
			}
			CHDynmapStatic.getDynmapAPI(t).triggerRenderOfBlock(ObjectGenerator.GetGenerator().location(args[0], world, t));
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_render_volume extends AbstractFunction {

		public String getName() {
			return "dm_render_volume";
		}

		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.CastException, ExceptionType.FormatException, ExceptionType.InvalidWorldException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "void {locationArray, locationArray} Trigger update on tiles in the given volume."
					+ " The volume is a cuboid defined by the two locations (opposite corners).";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCPlayer player = environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
			MCWorld world;
			if (player == null) {
				world = null;
			} else {
				world = player.getWorld();
			}
			try {
				CHDynmapStatic.getDynmapAPI(t).triggerRenderOfVolume(ObjectGenerator.GetGenerator().location(args[0], world, t), ObjectGenerator.GetGenerator().location(args[1], world, t));
			} catch (IllegalArgumentException exception) {
				throw new ConfigRuntimeException(exception.getMessage(), ExceptionType.FormatException, t);
			}
			return CVoid.VOID;
		}
	}

	@api
	public static class dm_marker_api_initialized extends AbstractFunction {

		public String getName() {
			return "dm_marker_api_initialized";
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
			return "boolean {} Returns if the marker API is initialized.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CBoolean(CHDynmapStatic.getDynmapAPI(t).markerAPIInitialized(), t);
		}
	}
}