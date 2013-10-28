package com.hekta.chdynmap.abstraction.bukkit;

import org.bukkit.plugin.Plugin;

import org.dynmap.DynmapAPI;

import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCOfflinePlayer;
import com.laytonsmith.abstraction.MCPlayer;

import com.hekta.chdynmap.abstraction.MCDynmapAPI;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerAPI;
import com.laytonsmith.abstraction.MCPlugin;
import com.laytonsmith.abstraction.bukkit.BukkitMCPlugin;

/**
 *
 * @author Hekta
 */
public class BukkitMCDynmapAPI implements MCDynmapAPI {

	DynmapAPI api;

	public BukkitMCDynmapAPI(Plugin dynmapPlugin) {
		this.api = (DynmapAPI) dynmapPlugin;
	}

	public BukkitMCDynmapAPI(DynmapAPI dynmapAPI) {
		this.api = dynmapAPI;
	}

	public DynmapAPI getConcrete() {
		return api;
	}

	public MCDynmapMarkerAPI getMarkerAPI() {
		return new BukkitMCDynmapMarkerAPI(api.getMarkerAPI());
	}

	public boolean markerAPIInitialized() {
		return api.markerAPIInitialized();
	}

	public boolean sendBroadcastToWeb(String sender, String message) {
		return api.sendBroadcastToWeb(sender, message);
	}

	public int triggerRenderOfVolume(MCLocation minLocation, MCLocation maxLocation) {
		if (minLocation.getWorld() == maxLocation.getWorld()) {
			return api.triggerRenderOfVolume(minLocation.getWorld().getName(), minLocation.getBlockX(), minLocation.getBlockY(), minLocation.getBlockZ(), maxLocation.getBlockX(), maxLocation.getBlockY(), maxLocation.getBlockZ());
		} else {
			throw new IllegalArgumentException("The locations must be in the same world.");
		}
	}

	public int triggerRenderOfBlock(MCLocation location) {
		return api.triggerRenderOfBlock(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}

	public boolean getPauseFullRadiusRenders() {
		return api.getPauseFullRadiusRenders();
	}

	public void setPauseFullRadiusRenders(boolean paused) {
		api.setPauseFullRadiusRenders(paused);
	}

	public boolean getPauseUpdateRenders() {
		return api.getPauseUpdateRenders();
	}

	public void setPauseUpdateRenders(boolean paused) {
		api.setPauseUpdateRenders(paused);
	}

	public String getDynmapCoreVersion() {
		return api.getDynmapCoreVersion();
	}

	public boolean setChatToWebProcessingEnabled(boolean enabled) {
		return api.setDisableChatToWebProcessing(!enabled);
	}

	public boolean testIfPlayerInfoProtected() {
		return api.testIfPlayerInfoProtected();
	}

	//players

	public boolean getPlayerVisbility(MCOfflinePlayer player) {
		return api.getPlayerVisbility(player.getName());
	}

	public void setPlayerVisiblity(MCOfflinePlayer player, boolean isVisible) {
		api.setPlayerVisiblity(player.getName(), isVisible);
	}

	public void assertPlayerVisibility(MCOfflinePlayer player, boolean isVisible, MCPlugin plugin) {
		api.assertPlayerVisibility(player.getName(), isVisible, ((BukkitMCPlugin) plugin).getPlugin().getName());
	}

	public void assertPlayerVisibility(MCOfflinePlayer player, boolean isVisible, String pluginId) {
		api.assertPlayerVisibility(player.getName(), isVisible, pluginId);
	}

	public void assertPlayerInvisibility(MCOfflinePlayer player, boolean isInvisible, MCPlugin plugin) {
		api.assertPlayerInvisibility(player.getName(), isInvisible, ((BukkitMCPlugin) plugin).getPlugin().getName());
	}

	public void assertPlayerInvisibility(MCOfflinePlayer player, boolean isInvisible, String pluginId) {
		api.assertPlayerInvisibility(player.getName(), isInvisible, pluginId);
	}

	public void postPlayerMessageToWeb(MCOfflinePlayer player, String message) {
		api.postPlayerMessageToWeb(player.getName(), player.getName(), message);
	}

	public void postPlayerMessageToWeb(MCPlayer player, String message) {
		api.postPlayerMessageToWeb(player.getName(), player.getDisplayName(), message);
	}

	public void postPlayerMessageToWeb(MCOfflinePlayer player, String displayName, String message) {
		api.postPlayerMessageToWeb(player.getName(), displayName, message);
	}

	public void postPlayerJoinToWeb(MCOfflinePlayer player) {
		api.postPlayerJoinQuitToWeb(player.getName(), player.getName(), true);
	}

	public void postPlayerJoinToWeb(MCPlayer player) {
		api.postPlayerJoinQuitToWeb(player.getName(), player.getDisplayName(), true);
	}

	public void postPlayerJoinToWeb(MCOfflinePlayer player, String displayName) {
		api.postPlayerJoinQuitToWeb(player.getName(), displayName, true);
	}

	public void postPlayerQuitToWeb(MCOfflinePlayer player) {
		api.postPlayerJoinQuitToWeb(player.getName(), player.getName(), false);
	}

	public void postPlayerQuitToWeb(MCPlayer player) {
		api.postPlayerJoinQuitToWeb(player.getName(), player.getDisplayName(), false);
	}

	public void postPlayerQuitToWeb(MCOfflinePlayer player, String displayName) {
		api.postPlayerJoinQuitToWeb(player.getName(), displayName, false);
	}

	public boolean testIfPlayerVisibleToPlayer(MCOfflinePlayer player, MCOfflinePlayer playerToSee) {
		return api.testIfPlayerVisibleToPlayer(player.getName(), playerToSee.getName());
	}
}