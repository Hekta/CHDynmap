package com.hekta.chdynmap.abstraction.bukkit;

import com.hekta.chdynmap.abstraction.MCDynmapAPI;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerAPI;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCOfflinePlayer;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.MCPlugin;
import com.laytonsmith.annotations.abstraction;
import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapAPI;

/**
 *
 * @author Hekta
 */
@abstraction(type = Implementation.Type.BUKKIT)
public class BukkitMCDynmapAPI implements MCDynmapAPI {

	private final DynmapAPI _api;

	public BukkitMCDynmapAPI(Plugin dynmapPlugin) {
		this((DynmapAPI) dynmapPlugin);
	}

	public BukkitMCDynmapAPI(DynmapAPI dynmapAPI) {
		_api = dynmapAPI;
	}

	public BukkitMCDynmapAPI(Object object) {
		this((DynmapAPI) object);
	}

	@Override
	public DynmapAPI getHandle() {
		return _api;
	}

	@Override
	public Version getVersion() {
		String s = _api.getDynmapVersion();
		if (s != null) {
			Version v = new SimpleVersion(_api.getDynmapVersion());
			if ((v.getMajor() == 0) && (v.getMinor() == 0) && (v.getSupplemental() == 0)) {
				return null;
			} else {
				return v;
			}
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapMarkerAPI getMarkerAPI() {
		return new BukkitMCDynmapMarkerAPI(_api.getMarkerAPI());
	}

	@Override
	public boolean markerAPIInitialized() {
		return _api.markerAPIInitialized();
	}

	@Override
	public boolean sendBroadcastToWeb(String sender, String message) {
		return _api.sendBroadcastToWeb(sender, message);
	}

	@Override
	public int triggerRenderOfVolume(MCLocation minLocation, MCLocation maxLocation) {
		if (minLocation.getWorld() == maxLocation.getWorld()) {
			return _api.triggerRenderOfVolume(minLocation.getWorld().getName(), minLocation.getBlockX(), minLocation.getBlockY(), minLocation.getBlockZ(), maxLocation.getBlockX(), maxLocation.getBlockY(), maxLocation.getBlockZ());
		} else {
			throw new IllegalArgumentException("The locations must be in the same world.");
		}
	}

	@Override
	public int triggerRenderOfBlock(MCLocation location) {
		return _api.triggerRenderOfBlock(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}

	@Override
	public boolean getPauseFullRadiusRenders() {
		return _api.getPauseFullRadiusRenders();
	}

	@Override
	public void setPauseFullRadiusRenders(boolean paused) {
		_api.setPauseFullRadiusRenders(paused);
	}

	@Override
	public boolean getPauseUpdateRenders() {
		return _api.getPauseUpdateRenders();
	}

	@Override
	public void setPauseUpdateRenders(boolean paused) {
		_api.setPauseUpdateRenders(paused);
	}

	@Override
	public String getDynmapCoreVersion() {
		return _api.getDynmapCoreVersion();
	}

	@Override
	public boolean setChatToWebProcessingEnabled(boolean enabled) {
		return _api.setDisableChatToWebProcessing(!enabled);
	}

	@Override
	public boolean testIfPlayerInfoProtected() {
		return _api.testIfPlayerInfoProtected();
	}

	//players

	@Override
	public boolean getPlayerVisbility(MCOfflinePlayer player) {
		return _api.getPlayerVisbility(player.getName());
	}

	@Override
	public void setPlayerVisiblity(MCOfflinePlayer player, boolean isVisible) {
		_api.setPlayerVisiblity(player.getName(), isVisible);
	}

	@Override
	public void assertPlayerVisibility(MCOfflinePlayer player, boolean isVisible, MCPlugin plugin) {
		_api.assertPlayerVisibility(player.getName(), isVisible, ((Plugin) plugin.getHandle()).getName());
	}

	@Override
	public void assertPlayerVisibility(MCOfflinePlayer player, boolean isVisible, String pluginId) {
		_api.assertPlayerVisibility(player.getName(), isVisible, pluginId);
	}

	@Override
	public void assertPlayerInvisibility(MCOfflinePlayer player, boolean isInvisible, MCPlugin plugin) {
		_api.assertPlayerInvisibility(player.getName(), isInvisible, ((Plugin) plugin.getHandle()).getName());
	}

	@Override
	public void assertPlayerInvisibility(MCOfflinePlayer player, boolean isInvisible, String pluginId) {
		_api.assertPlayerInvisibility(player.getName(), isInvisible, pluginId);
	}

	@Override
	public void postPlayerMessageToWeb(MCOfflinePlayer player, String message) {
		_api.postPlayerMessageToWeb(player.getName(), player.getName(), message);
	}

	@Override
	public void postPlayerMessageToWeb(MCPlayer player, String message) {
		_api.postPlayerMessageToWeb(player.getName(), player.getDisplayName(), message);
	}

	@Override
	public void postPlayerMessageToWeb(MCOfflinePlayer player, String displayName, String message) {
		_api.postPlayerMessageToWeb(player.getName(), displayName, message);
	}

	@Override
	public void postPlayerJoinToWeb(MCOfflinePlayer player) {
		_api.postPlayerJoinQuitToWeb(player.getName(), player.getName(), true);
	}

	@Override
	public void postPlayerJoinToWeb(MCPlayer player) {
		_api.postPlayerJoinQuitToWeb(player.getName(), player.getDisplayName(), true);
	}

	@Override
	public void postPlayerJoinToWeb(MCOfflinePlayer player, String displayName) {
		_api.postPlayerJoinQuitToWeb(player.getName(), displayName, true);
	}

	@Override
	public void postPlayerQuitToWeb(MCOfflinePlayer player) {
		_api.postPlayerJoinQuitToWeb(player.getName(), player.getName(), false);
	}

	@Override
	public void postPlayerQuitToWeb(MCPlayer player) {
		_api.postPlayerJoinQuitToWeb(player.getName(), player.getDisplayName(), false);
	}

	@Override
	public void postPlayerQuitToWeb(MCOfflinePlayer player, String displayName) {
		_api.postPlayerJoinQuitToWeb(player.getName(), displayName, false);
	}

	@Override
	public boolean testIfPlayerVisibleToPlayer(MCOfflinePlayer player, MCOfflinePlayer playerToSee) {
		return _api.testIfPlayerVisibleToPlayer(player.getName(), playerToSee.getName());
	}
}