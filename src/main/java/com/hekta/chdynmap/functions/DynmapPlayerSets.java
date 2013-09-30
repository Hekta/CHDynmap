package com.hekta.chdynmap.functions;

import java.util.HashSet;
import java.util.Set;

import com.laytonsmith.annotations.api;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.CString;
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

import org.dynmap.markers.PlayerSet;

import static com.hekta.chdynmap.util.CHDynmapAPI.getDynmapPlayerSet;
import static com.hekta.chdynmap.util.CHDynmapAPI.markerapi;
import static com.hekta.chdynmap.util.CHDynmapAPI.testDynmapIDValidity;

/*
 *
 * @author Hekta
 */
public class DynmapPlayerSets {

	public static String docs() {
		return "A class of functions to manage the Dynmap playersets.";
	}

	@api
	public static class dm_all_playersets extends AbstractFunction {

		public String getName() {
			return "dm_all_playersets";
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
			return "array {} Returns an array containing all the playerset IDs.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			CArray setList = new CArray(t);
			for (PlayerSet set : markerapi.getPlayerSets()) {
				setList.push(new CString(set.getSetID(), t));
			}
			return setList;
		}
	}

	@api
	public static class dm_create_playerset extends AbstractFunction {

		public String getName() {
			return "dm_create_playerset";
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
			return "string {newSetID, [optionArray]} Creates a playerset and returns its ID."
					+ " ---- The ID must be unique among playersets and must only contain numbers, letters, periods (.) and underscores (_)."
					+ " The option array is associative and not required, and all its keys are optional."
					+ " <li>KEY - DEFAULT - DESCRIPTION - COMMENT</li>"
					+ " <li>persistent - false - sets if the playerset is persistent - can not be changed later</li>"
					+ " <li>players - empty array - an array of players the playerset will contain</li>"
					+ " <li>symmetric - false - sets if the playerset will be symmetric (players in set can see other the players in set)</li>";
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
			if (markerapi.getPlayerSet(setID) != null) {
				throw new ConfigRuntimeException("\"" + setID + "\" is already an existing playerset.", ExceptionType.PluginInternalException, t);
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
			//players
			HashSet<String> players = new HashSet<String>();
			if (keys.contains("players")) {
				CArray givenPlayers = Static.getArray(optionArray.get("players", t), t);
				if (givenPlayers.inAssociativeMode()) {
					throw new ConfigRuntimeException("The array must not be associative.", ExceptionType.CastException, t);
				}
				for (Construct player : givenPlayers.asList()) {
					players.add(Static.getServer().getOfflinePlayer(player.val()).getName());
				}
			}
			//persistent
			boolean persistent;
			if (keys.contains("persistent")) {
				persistent = Static.getBoolean(optionArray.get("persistent", t));
			} else {
				persistent = false;
			}
			//symmetric
			boolean symmetric;
			if (keys.contains("symmetric")) {
				symmetric = Static.getBoolean(optionArray.get("symmetric", t));
			} else {
				symmetric = false;
			}
			//create player set
			PlayerSet set = markerapi.createPlayerSet(setID, symmetric, players, persistent);
			if (set == null) {
				throw new ConfigRuntimeException("The markerset creation failed.", ExceptionType.PluginInternalException, t);
			}
			return new CString(set.getSetID(), t);
		}
	}

	@api
	public static class dm_delete_playerset extends AbstractFunction {

		public String getName() {
			return "dm_delete_playerset";
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
			return "void {setID} Deletes a playerset.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			getDynmapPlayerSet(args[0].val(), t).deleteSet();
			return new CVoid(t);
		}
	}

	@api
	public static class dm_players_in_playerset extends AbstractFunction {

		public String getName() {
			return "dm_players_in_playerset";
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
			return "array {setID} Returns an array containing all the players in the playerset.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			CArray pList = new CArray(t);
			for (String player : getDynmapPlayerSet(args[0].val(), t).getPlayers()) {
				pList.push(new CString(player, t));
			}
			return pList;
		}
	}

	@api
	public static class dm_set_players_in_playerset extends AbstractFunction {

		public String getName() {
			return "dm_set_players_in_playerset";
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
			return "void {setID, array} Sets the players in the playerset."
					+ " This will not throw a PlayerOfflineException, so the name must be exact.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			CArray givenPlayers = Static.getArray(args[1], t);
			if (givenPlayers.inAssociativeMode()) {
				throw new ConfigRuntimeException("The array must not be associative.", ExceptionType.CastException, t);
			}
			Set<String> pList = new HashSet<String>();
			for (Construct player : givenPlayers.asList()) {
				pList.add(Static.getServer().getOfflinePlayer(player.val()).getName());
			}
			getDynmapPlayerSet(args[0].val(), t).setPlayers(pList);
			return new CVoid(t);
		}
	}

	@api
	public static class dm_pis_in_playerset extends AbstractFunction {

		public String getName() {
			return "dm_pis_in_playerset";
		}

		public Integer[] numArgs() {
			return new Integer[]{1, 2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.PlayerOfflineException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "boolean {setID, [playerName]} Returns if a player is in the playerset."
					+ " This will not throw a PlayerOfflineException (exept from console), so the name must be exact.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			PlayerSet set = getDynmapPlayerSet(args[0].val(), t);
			String player;
			if (args.length == 1) {
				MCPlayer psender = environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
				if (psender == null) {
					throw new ConfigRuntimeException("No player was specified!", ExceptionType.PlayerOfflineException, t);
				} else {
					player = Static.getServer().getOfflinePlayer(psender.getName()).getName();
				}
			} else {
				player = Static.getServer().getOfflinePlayer(args[1].val()).getName();
			}
			return new CBoolean(getDynmapPlayerSet(args[0].val(), t).isPlayerInSet(player), t);
		}
	}

	@api
	public static class dm_set_pis_in_playerset extends AbstractFunction {

		public String getName() {
			return "dm_set_pis_in_playerset";
		}

		public Integer[] numArgs() {
			return new Integer[]{2, 3};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.PluginInternalException, ExceptionType.PlayerOfflineException};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public String docs() {
			return "void {setID, [playerName], boolean} Sets if a player is in the playerset."
					+ " This will not throw a PlayerOfflineException (exept from console), so the name must be exact.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			PlayerSet set = getDynmapPlayerSet(args[0].val(), t);
			String player;
			boolean isInSet;
			if (args.length == 2) {
				MCPlayer psender = environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
				if (psender == null) {
					throw new ConfigRuntimeException("No player was specified!", ExceptionType.PlayerOfflineException, t);
				} else {
					player = Static.getServer().getOfflinePlayer(psender.getName()).getName();
					isInSet = Static.getBoolean(args[1]);
				}
			} else {
				player = Static.getServer().getOfflinePlayer(args[1].val()).getName();
				isInSet = Static.getBoolean(args[2]);
			}
			if (isInSet) {
				if (set.isPlayerInSet(player)) {
					throw new ConfigRuntimeException("The player is already in the playerset.", ExceptionType.PluginInternalException, t);
				}
				set.addPlayer(player);	
			} else {
				if (!set.isPlayerInSet(player)) {
					throw new ConfigRuntimeException("The player is already not in the playerset.", ExceptionType.PluginInternalException, t);
				}
				set.removePlayer(player);
			}
			return new CVoid(t);
		}
	}

	@api
	public static class dm_playerset_persistent extends AbstractFunction {

		public String getName() {
			return "dm_playerset_persistent";
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
			return "boolean {setID} Returns if the playerset is persistent.";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			return new CBoolean(getDynmapPlayerSet(args[0].val(), t).isPersistentSet(), t);
		}
	}

	@api
	public static class dm_playerset_symmetric extends AbstractFunction {

		public String getName() {
			return "dm_playerset_symmetric";
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
			return "boolean {setID} Returns if the playerset is symmetric (if true players in set can see other the players in set).";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			return new CBoolean(getDynmapPlayerSet(args[0].val(), t).isSymmetricSet(), t);
		}
	}

	@api
	public static class dm_set_playerset_symmetric extends AbstractFunction {

		public String getName() {
			return "dm_set_playerset_symmetric";
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
			return "void {setID, boolean} Sets if the playerset is symmetric (if true, players in set can see the players in set, if false, privilege is always required).";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Static.checkPlugin("dynmap", t);
			getDynmapPlayerSet(args[0].val(), t).setSymmetricSet(Static.getBoolean(args[1]));
			return new CVoid(t);
		}
	}
}
