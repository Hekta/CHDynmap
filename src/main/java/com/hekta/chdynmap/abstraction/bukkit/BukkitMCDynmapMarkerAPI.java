package com.hekta.chdynmap.abstraction.bukkit;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.PlayerSet;

import com.laytonsmith.abstraction.MCOfflinePlayer;

import com.hekta.chdynmap.abstraction.MCDynmapIcon;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerAPI;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerSet;
import com.hekta.chdynmap.abstraction.MCDynmapPlayerSet;

/**
 *
 * @author Hekta
 */
public class BukkitMCDynmapMarkerAPI implements MCDynmapMarkerAPI {

	MarkerAPI mapi;

	public BukkitMCDynmapMarkerAPI(MarkerAPI dynmapMarkerAPI) {
		this.mapi = dynmapMarkerAPI;
	}

	public MarkerAPI getConcrete() {
		return mapi;
	}

	public Set<MCDynmapMarkerSet> getMarkerSets() {
		Set<MCDynmapMarkerSet> markerSets = new HashSet<MCDynmapMarkerSet>();
		for (MarkerSet markerSet : mapi.getMarkerSets()) {
			markerSets.add(new BukkitMCDynmapMarkerSet(markerSet));
		}
		return markerSets;
	}

	public MCDynmapMarkerSet getMarkerSet(String id) {
		MarkerSet markerSet = mapi.getMarkerSet(id);
		if (markerSet != null) {
			return new BukkitMCDynmapMarkerSet(markerSet);
		} else {
			return null;
		}
	}

	public MCDynmapMarkerSet createMarkerSet(String id, String label, Set<MCDynmapIcon> iconLimit, boolean isPersistent) {
		Set<MarkerIcon> icons;
		if (iconLimit != null) {
			icons = new HashSet<MarkerIcon>();
			for (MCDynmapIcon icon : iconLimit) {
				icons.add(((BukkitMCDynmapIcon) icon).getConcrete());
			}
		} else {
			icons = null;
		}
		MarkerSet markerSet = mapi.createMarkerSet(id, label, icons, isPersistent);
		if (markerSet != null) {
			return new BukkitMCDynmapMarkerSet(markerSet);
		} else {
			return null;
		}
	}

	public Set<MCDynmapIcon> getIcons() {
		Set<MCDynmapIcon> icons = new HashSet<MCDynmapIcon>();
		for (MarkerIcon icon : mapi.getMarkerIcons()) {
			icons.add(new BukkitMCDynmapIcon(icon));
		}
		return icons;
	}

	public MCDynmapIcon getIcon(String id) {
		MarkerIcon icon = mapi.getMarkerIcon(id);
		if (icon != null) {
			return new BukkitMCDynmapIcon(icon);
		} else {
			return null;
		}
	}

	public MCDynmapIcon createIcon(String id, String label, InputStream pngImage) {
		MarkerIcon icon = mapi.createMarkerIcon(id, label, pngImage);
		if (icon != null) {
			return new BukkitMCDynmapIcon(icon);
		} else {
			return null;
		}
	}

	public Set<MCDynmapPlayerSet> getPlayerSets() {
		Set<MCDynmapPlayerSet> playerSets = new HashSet<MCDynmapPlayerSet>();
		for (PlayerSet playerSet : mapi.getPlayerSets()) {
			playerSets.add(new BukkitMCDynmapPlayerSet(playerSet));
		}
		return playerSets;
	}

	public MCDynmapPlayerSet getPlayerSet(String id) {
		PlayerSet playerSet = mapi.getPlayerSet(id);
		if (playerSet != null) {
			return new BukkitMCDynmapPlayerSet(playerSet);
		} else {
			return null;
		}
	}

	public MCDynmapPlayerSet createPlayerSet(String id, boolean isSymmetric, Set<MCOfflinePlayer> players, boolean isPersistent) {
		Set<String> playerNames = new HashSet<String>();
		for (MCOfflinePlayer player : players) {
			playerNames.add(player.getName());
		}
		PlayerSet playerSet = mapi.createPlayerSet(id, isSymmetric, playerNames, isPersistent);
		if (playerSet != null) {
			return new BukkitMCDynmapPlayerSet(playerSet);
		} else {
			return null;
		}
	}
}