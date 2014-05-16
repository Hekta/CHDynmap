package com.hekta.chdynmap.abstraction;

import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.AbstractionObject;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCOfflinePlayer;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.MCPlugin;

/**
 *
 * @author Hekta
 */
public interface MCDynmapAPI extends AbstractionObject {

	public Version getVersion();

	public MCDynmapMarkerAPI getMarkerAPI();
	public boolean markerAPIInitialized();

	public boolean sendBroadcastToWeb(String sender, String message);

	public int triggerRenderOfVolume(MCLocation minLocation, MCLocation maxLocation);
	public int triggerRenderOfBlock(MCLocation location);

	public boolean getPauseFullRadiusRenders();
	public void setPauseFullRadiusRenders(boolean paused);
	public boolean getPauseUpdateRenders();
	public void setPauseUpdateRenders(boolean paused);

	public String getDynmapCoreVersion();

	public boolean setChatToWebProcessingEnabled(boolean enabled);

	public boolean testIfPlayerInfoProtected();

	//players

	public boolean getPlayerVisbility(MCOfflinePlayer player);
	public void setPlayerVisiblity(MCOfflinePlayer player, boolean isVisible);

	public void assertPlayerVisibility(MCOfflinePlayer player, boolean isVisible, MCPlugin plugin);
	public void assertPlayerVisibility(MCOfflinePlayer player, boolean isVisible, String pluginId);
	public void assertPlayerInvisibility(MCOfflinePlayer player, boolean isInvisible, MCPlugin plugin);
	public void assertPlayerInvisibility(MCOfflinePlayer player, boolean isInvisible, String pluginId);

	public void postPlayerMessageToWeb(MCOfflinePlayer player, String message);
	public void postPlayerMessageToWeb(MCPlayer player, String message);
	public void postPlayerMessageToWeb(MCOfflinePlayer player, String displayName, String message);

	public void postPlayerJoinToWeb(MCOfflinePlayer player);
	public void postPlayerJoinToWeb(MCPlayer player);
	public void postPlayerJoinToWeb(MCOfflinePlayer player, String displayName);
	public void postPlayerQuitToWeb(MCOfflinePlayer player);
	public void postPlayerQuitToWeb(MCPlayer player);
	public void postPlayerQuitToWeb(MCOfflinePlayer player, String displayName);

	public boolean testIfPlayerVisibleToPlayer(MCOfflinePlayer player, MCOfflinePlayer playerToSee);
}