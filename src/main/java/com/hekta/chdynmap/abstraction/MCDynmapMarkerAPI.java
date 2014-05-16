package com.hekta.chdynmap.abstraction;

import com.laytonsmith.abstraction.AbstractionObject;
import com.laytonsmith.abstraction.MCOfflinePlayer;
import java.io.InputStream;

/**
 *
 * @author Hekta
 */
public interface MCDynmapMarkerAPI extends AbstractionObject {

	public abstract String getDefaultMarkerSetID();

	public MCDynmapMarkerSet[] getMarkerSets();
	public MCDynmapMarkerSet getMarkerSet(String id);
	public MCDynmapMarkerSet createMarkerSet(String id, String label, MCDynmapIcon[] iconLimit, boolean isPersistent);
	public MCDynmapMarkerSet createMarkerSet(String id, String label, Iterable<MCDynmapIcon> iconLimit, boolean isPersistent);

	public MCDynmapIcon[] getIcons();
	public MCDynmapIcon getIcon(String id);
	public MCDynmapIcon createIcon(String id, String label, InputStream pngImage);

	public MCDynmapPlayerSet[] getPlayerSets();
	public MCDynmapPlayerSet getPlayerSet(String id);
	public MCDynmapPlayerSet createPlayerSet(String id, boolean isSymmetric, MCOfflinePlayer[] players, boolean isPersistent);
	public MCDynmapPlayerSet createPlayerSet(String id, boolean isSymmetric, Iterable<MCOfflinePlayer> players, boolean isPersistent);
}