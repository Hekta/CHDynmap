package com.hekta.chdynmap.core.functions;

import com.laytonsmith.abstraction.MCOfflinePlayer;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.annotations.api;
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
import com.laytonsmith.core.Static;
import com.laytonsmith.PureUtilities.Version;

import com.hekta.chdynmap.core.CHDynmapStatic;

/**
 *
 * @author Hekta
 */
public class DynmapPlayers {

	public static String docs() {
		return "A class of functions using the Dynmap features for players.";
	}

	public static abstract class DynmapPlayerFunction extends AbstractFunction {

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

	public static abstract class DynmapPlayerGetterFunction extends DynmapPlayerFunction {

		public Integer[] numArgs() {
			return new Integer[]{0, 1};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PlayerOfflineException};
		}
	}

	@api
	public static class dm_pvisible extends DynmapPlayerGetterFunction {

		public String getName() {
			return "dm_pvisible";
		}

		public String docs() {
			return "boolean {[playerName]} Returns if the player is visible on the Dynmap."
					+ " This will not throw a PlayerOfflineException (exept from console), so the name must be exact.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCOfflinePlayer player;
			if (args.length == 0) {
				MCPlayer psender = environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
				if (psender == null) {
					throw new ConfigRuntimeException("No player was specified!", ExceptionType.PlayerOfflineException, t);
				} else {
					player = psender;
				}
			} else {
				player = Static.getServer().getOfflinePlayer(args[0].val());
			}
			return new CBoolean(CHDynmapStatic.getDynmapAPI(t).getPlayerVisbility(player), t);
		}
	}

	@api
	public static class dm_set_pvisible extends DynmapPlayerFunction {

		public String getName() {
			return "dm_set_pvisible";
		}

		public Integer[] numArgs() {
			return new Integer[]{1, 2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PlayerOfflineException};
		}

		public String docs() {
			return "void {[playerName], boolean} Sets if the player is visible on the Dynmap."
					+ " This will not throw a PlayerOfflineException (exept from console), so the name must be exact.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCOfflinePlayer player;
			boolean isVisible;
			if (args.length == 1) {
				MCPlayer psender = environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
				if (psender == null) {
					throw new ConfigRuntimeException("No player was specified!", ExceptionType.PlayerOfflineException, t);
				} else {
					player = psender;
					isVisible = Static.getBoolean(args[0]);
				}
			} else {
				player = Static.getServer().getOfflinePlayer(args[0].val());
				isVisible = Static.getBoolean(args[1]);
			}
			CHDynmapStatic.getDynmapAPI(t).setPlayerVisiblity(player, isVisible);
			return new CVoid(t);
		}
	}

	@api
	public static class dm_assert_pvisibility extends DynmapPlayerFunction {

		public String getName() {
			return "dm_assert_pvisibility";
		}

		public Integer[] numArgs() {
			return new Integer[]{1, 2, 3};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PlayerOfflineException};
		}

		public String docs() {
			return "void {[playerName], boolean | playerName, boolean, [pluginID]} Asserts the player visibility (transient, if player is configured to be visible, it is made hidden if one or more plugins assert its invisibility),"
					+ " pluginID is the id that will be used to assert, default to 'CommandHelper'."
					+ " This will not throw a PlayerOfflineException (exept from console), so the name must be exact.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCOfflinePlayer player;
			boolean isVisible;
			String plugin;
			if (args.length == 1) {
				MCPlayer psender = environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
				if (psender == null) {
					throw new ConfigRuntimeException("No player was specified!", ExceptionType.PlayerOfflineException, t);
				} else {
					player = psender;
					isVisible = Static.getBoolean(args[0]);
					plugin = "CommandHelper";
				}
			} else if (args.length == 2) {
				player = Static.getServer().getOfflinePlayer(args[0].val());
				isVisible = Static.getBoolean(args[1]);
				plugin = "CommandHelper";
			} else {
				player = Static.getServer().getOfflinePlayer(args[0].val());
				isVisible = Static.getBoolean(args[1]);
				plugin = args[2].val();
			}
			CHDynmapStatic.getDynmapAPI(t).assertPlayerVisibility(player, isVisible, plugin);
			return new CVoid(t);
		}
	}

	@api
	public static class dm_assert_pinvisibility extends DynmapPlayerFunction {

		public String getName() {
			return "dm_assert_pinvisibility";
		}

		public Integer[] numArgs() {
			return new Integer[]{1, 2, 3};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PlayerOfflineException};
		}

		public String docs() {
			return "void {[playerName], boolean | playerName, boolean, [pluginID]} Asserts the player invisibility (transient, if player is configured to be hidden, it is made visibile if one or more plugins assert its visibility),"
					+ " pluginID is the id that will be used to assert, default to 'CommandHelper'."
					+ " This will not throw a PlayerOfflineException (exept from console), so the name must be exact.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCOfflinePlayer player;
			boolean isInvisible;
			String plugin;
			if (args.length == 1) {
				MCPlayer psender = environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
				if (psender == null) {
					throw new ConfigRuntimeException("No player was specified!", ExceptionType.PlayerOfflineException, t);
				} else {
					player = psender;
					isInvisible = Static.getBoolean(args[0]);
					plugin = "CommandHelper";
				}
			} else if (args.length == 2) {
				player = Static.getServer().getOfflinePlayer(args[0].val());
				isInvisible = Static.getBoolean(args[1]);
				plugin = "CommandHelper";
			} else {
				player = Static.getServer().getOfflinePlayer(args[0].val());
				isInvisible = Static.getBoolean(args[1]);
				plugin = args[2].val();
			}
			CHDynmapStatic.getDynmapAPI(t).assertPlayerInvisibility(player, isInvisible, plugin);
			return new CVoid(t);
		}
	}

	@api
	public static class dm_pcan_see extends DynmapPlayerGetterFunction {

		public String getName() {
			return "dm_pcan_see";
		}

		public String docs() {
			return "void {[playerName], otherPlayerName} Returns if the player can see the other player."
					+ " This will not throw a PlayerOfflineException (exept from console), so the name must be exact.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCOfflinePlayer player;
			MCOfflinePlayer otherPlayer;
			if (args.length == 1) {
				MCPlayer psender = environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
				if (psender == null) {
					throw new ConfigRuntimeException("No player was specified!", ExceptionType.PlayerOfflineException, t);
				} else {
					player = psender;
					otherPlayer = Static.getServer().getOfflinePlayer(args[0].val());
				}
			} else {
				player = Static.getServer().getOfflinePlayer(args[0].val());
				otherPlayer = Static.getServer().getOfflinePlayer(args[1].val());
			}
			return new CBoolean(CHDynmapStatic.getDynmapAPI(t).testIfPlayerVisibleToPlayer(player, otherPlayer), t);
		}
	}

	@api
	public static class dm_post_pchat extends DynmapPlayerFunction {

		public String getName() {
			return "dm_post_pchat";
		}

		public Integer[] numArgs() {
			return new Integer[]{1, 2, 3};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PlayerOfflineException};
		}

		public String docs() {
			return "void {[playerName], message | playerName, message, [displayName]} Post message from player to web, if displayName is an empty string, it is not noticed."
					+ " This will not throw a PlayerOfflineException (exept from console), so the name must be exact.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCOfflinePlayer player;
			String displayName;
			String message;
			if (args.length == 1) {
				MCPlayer psender = environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
				if (psender == null) {
					throw new ConfigRuntimeException("No player was specified!", ExceptionType.PlayerOfflineException, t);
				} else {
					player = psender;
					displayName = psender.getDisplayName();
					message = args[0].val();
				}
			} else if (args.length == 2) {
				MCPlayer mcplayer = Static.getServer().getPlayer(args[0].val());
				if (mcplayer == null) {
					player = Static.getServer().getOfflinePlayer(args[0].val());
					displayName = player.getName();
				} else {
					player = mcplayer;
					displayName = mcplayer.getDisplayName();
				}
				message = args[1].val();
			} else {
				player = Static.getServer().getOfflinePlayer(args[0].val());
				displayName = args[2].val();
				message = args[1].val();
			}
			CHDynmapStatic.getDynmapAPI(t).postPlayerMessageToWeb(player, displayName, message);
			return new CVoid(t);
		}
	}

	@api
	public static class dm_post_pjoin extends DynmapPlayerGetterFunction {

		public String getName() {
			return "dm_post_pjoin";
		}

		public String docs() {
			return "void {[playerName]} Post a join message for player to web."
					+ " This will not throw a PlayerOfflineException (exept from console), so the name must be exact.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCOfflinePlayer player;
			String displayName;
			if (args.length == 0) {
				MCPlayer psender = environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
				if (psender == null) {
					throw new ConfigRuntimeException("No player was specified!", ExceptionType.PlayerOfflineException, t);
				} else {
					player = psender;
					displayName = psender.getDisplayName();
				}
			} else {
				MCPlayer mcplayer = Static.getServer().getPlayer(args[0].val());
				if (mcplayer == null) {
					player = Static.getServer().getOfflinePlayer(args[0].val());
					displayName = player.getName();
				} else {
					player = mcplayer;
					displayName = mcplayer.getDisplayName();
				}
			}
			CHDynmapStatic.getDynmapAPI(t).postPlayerJoinToWeb(player, displayName);
			return new CVoid(t);
		}
	}

	@api
	public static class dm_post_pquit extends DynmapPlayerGetterFunction {

		public String getName() {
			return "dm_post_pquit";
		}

		public String docs() {
			return "void {[playerName]} Post a quit message for player to web."
					+ " This will not throw a PlayerOfflineException (exept from console), so the name must be exact.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCOfflinePlayer player;
			String displayName;
			if (args.length == 0) {
				MCPlayer psender = environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
				if (psender == null) {
					throw new ConfigRuntimeException("No player was specified!", ExceptionType.PlayerOfflineException, t);
				} else {
					player = psender;
					displayName = psender.getDisplayName();
				}
			} else {
				MCPlayer mcplayer = Static.getServer().getPlayer(args[0].val());
				if (mcplayer == null) {
					player = Static.getServer().getOfflinePlayer(args[0].val());
					displayName = player.getName();
				} else {
					player = mcplayer;
					displayName = mcplayer.getDisplayName();
				}
			}
			CHDynmapStatic.getDynmapAPI(t).postPlayerQuitToWeb(player, displayName);
			return new CVoid(t);
		}
	}
}