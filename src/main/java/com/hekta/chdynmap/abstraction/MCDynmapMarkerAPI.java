package com.hekta.chdynmap.abstraction;

import java.io.InputStream;
import java.util.Set;

import com.laytonsmith.abstraction.MCOfflinePlayer;

/**
 *
 * @author Hekta
 */
public interface MCDynmapMarkerAPI {

	public Set<MCDynmapMarkerSet> getMarkerSets();
	public MCDynmapMarkerSet getMarkerSet(String id);
	public MCDynmapMarkerSet createMarkerSet(String id, String label, Set<MCDynmapIcon> iconLimit, boolean isPersistent);

	public Set<MCDynmapIcon> getIcons();
	public MCDynmapIcon getIcon(String id);
	public MCDynmapIcon createIcon(String id, String label, InputStream pngImage);

	public Set<MCDynmapPlayerSet> getPlayerSets();
	public MCDynmapPlayerSet getPlayerSet(String id);
	public MCDynmapPlayerSet createPlayerSet(String id, boolean isSymmetric, Set<MCOfflinePlayer> players, boolean isPersistent);
}