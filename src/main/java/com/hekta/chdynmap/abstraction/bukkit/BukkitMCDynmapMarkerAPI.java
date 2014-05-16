package com.hekta.chdynmap.abstraction.bukkit;

import com.hekta.chdynmap.abstraction.MCDynmapIcon;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerAPI;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerSet;
import com.hekta.chdynmap.abstraction.MCDynmapPlayerSet;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCOfflinePlayer;
import com.laytonsmith.annotations.abstraction;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.PlayerSet;

/**
 *
 * @author Hekta
 */
@abstraction(type = Implementation.Type.BUKKIT)
public class BukkitMCDynmapMarkerAPI implements MCDynmapMarkerAPI {

	private final MarkerAPI _api;

	public BukkitMCDynmapMarkerAPI(MarkerAPI dynmapMarkerAPI) {
		_api = dynmapMarkerAPI;
	}

	public BukkitMCDynmapMarkerAPI(Object object) {
		this((MarkerAPI) object);
	}

	@Override
	public MarkerAPI getHandle() {
		return _api;
	}

	@Override
	public String getDefaultMarkerSetID() {
		return MarkerSet.DEFAULT;
	}

	@Override
	public MCDynmapMarkerSet[] getMarkerSets() {
		Set<MarkerSet> ms = _api.getMarkerSets();
		MCDynmapMarkerSet[] markerSets = new MCDynmapMarkerSet[ms.size()];
		int i = 0;
		for (MarkerSet markerSet : ms) {
			markerSets[i] = new BukkitMCDynmapMarkerSet(markerSet);
			i++;
		}
		return markerSets;
	}

	@Override
	public MCDynmapMarkerSet getMarkerSet(String id) {
		MarkerSet markerSet = _api.getMarkerSet(id);
		if (markerSet != null) {
			return new BukkitMCDynmapMarkerSet(markerSet);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapMarkerSet createMarkerSet(String id, String label, MCDynmapIcon[] iconLimit, boolean isPersistent) {
		Set<MarkerIcon> icons;
		if (iconLimit != null) {
			icons = new HashSet<>();
			for (MCDynmapIcon icon : iconLimit) {
				icons.add(((BukkitMCDynmapIcon) icon).getHandle());
			}
		} else {
			icons = null;
		}
		MarkerSet markerSet = _api.createMarkerSet(id, label, icons, isPersistent);
		if (markerSet != null) {
			return new BukkitMCDynmapMarkerSet(markerSet);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapMarkerSet createMarkerSet(String id, String label, Iterable<MCDynmapIcon> iconLimit, boolean isPersistent) {
		Set<MarkerIcon> icons;
		if (iconLimit != null) {
			icons = new HashSet<>();
			for (MCDynmapIcon icon : iconLimit) {
				icons.add(((BukkitMCDynmapIcon) icon).getHandle());
			}
		} else {
			icons = null;
		}
		MarkerSet markerSet = _api.createMarkerSet(id, label, icons, isPersistent);
		if (markerSet != null) {
			return new BukkitMCDynmapMarkerSet(markerSet);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapIcon[] getIcons() {
		Set<MarkerIcon> is = _api.getMarkerIcons();
		MCDynmapIcon[] icons = new MCDynmapIcon[is.size()];
		int i = 0;
		for (MarkerIcon icon : is) {
			icons[i] = new BukkitMCDynmapIcon(icon);
			i++;
		}
		return icons;
	}

	@Override
	public MCDynmapIcon getIcon(String id) {
		MarkerIcon icon = _api.getMarkerIcon(id);
		if (icon != null) {
			return new BukkitMCDynmapIcon(icon);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapIcon createIcon(String id, String label, InputStream pngImage) {
		MarkerIcon icon = _api.createMarkerIcon(id, label, pngImage);
		if (icon != null) {
			return new BukkitMCDynmapIcon(icon);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapPlayerSet[] getPlayerSets() {
		Set<PlayerSet> ps = _api.getPlayerSets();
		MCDynmapPlayerSet[] playerSets = new MCDynmapPlayerSet[ps.size()];
		int i = 0;
		for (PlayerSet playerSet : ps) {
			playerSets[i] = new BukkitMCDynmapPlayerSet(playerSet);
			i++;
		}
		return playerSets;
	}

	@Override
	public MCDynmapPlayerSet getPlayerSet(String id) {
		PlayerSet playerSet = _api.getPlayerSet(id);
		if (playerSet != null) {
			return new BukkitMCDynmapPlayerSet(playerSet);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapPlayerSet createPlayerSet(String id, boolean isSymmetric, MCOfflinePlayer[] players, boolean isPersistent) {
		Set<String> playerNames = new HashSet<>();
		for (MCOfflinePlayer player : players) {
			playerNames.add(player.getName());
		}
		PlayerSet playerSet = _api.createPlayerSet(id, isSymmetric, playerNames, isPersistent);
		if (playerSet != null) {
			return new BukkitMCDynmapPlayerSet(playerSet);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapPlayerSet createPlayerSet(String id, boolean isSymmetric, Iterable<MCOfflinePlayer> players, boolean isPersistent) {
		Set<String> playerNames = new HashSet<>();
		for (MCOfflinePlayer player : players) {
			playerNames.add(player.getName());
		}
		PlayerSet playerSet = _api.createPlayerSet(id, isSymmetric, playerNames, isPersistent);
		if (playerSet != null) {
			return new BukkitMCDynmapPlayerSet(playerSet);
		} else {
			return null;
		}
	}
}