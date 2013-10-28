package com.hekta.chdynmap.abstraction.bukkit;

import java.util.HashSet;
import java.util.Set;

import org.dynmap.markers.PlayerSet;

import com.laytonsmith.abstraction.MCOfflinePlayer;
import com.laytonsmith.abstraction.StaticLayer;

import com.hekta.chdynmap.abstraction.MCDynmapPlayerSet;

/**
 *
 * @author Hekta
 */
public class BukkitMCDynmapPlayerSet implements MCDynmapPlayerSet {

	PlayerSet set;

	public BukkitMCDynmapPlayerSet(PlayerSet playerSet) {
		this.set = playerSet;
	}

	public PlayerSet getConcrete() {
		return set;
	}

	public String getId() {
		return set.getSetID();
	}

	public Set<MCOfflinePlayer> getPlayers() {
		Set<MCOfflinePlayer> players = new HashSet<MCOfflinePlayer>();
		for (String playerName : set.getPlayers()) {
			players.add(StaticLayer.GetServer().getOfflinePlayer(playerName));
		}
		return players;
	}

	public void setPlayers(Set<MCOfflinePlayer> players) {
		HashSet<String> playerNames = new HashSet<String>();
		for (MCOfflinePlayer player : players) {
			playerNames.add(player.getName());
		}
		set.setPlayers(playerNames);
	}

	public void addPlayer(MCOfflinePlayer player) {
		set.addPlayer(player.getName());
	}

	public void removePlayer(MCOfflinePlayer player) {
		set.removePlayer(player.getName());
	}

	public boolean isPlayerInSet(MCOfflinePlayer player) {
		return set.isPlayerInSet(player.getName());
	}

	public void delete() {
		set.deleteSet();
	}

	public boolean isSymmetric() {
		return set.isSymmetricSet();
	}

	public void setSymmetric(boolean isSymmetric) {
		set.setSymmetricSet(isSymmetric);
	}

	public boolean isPersistent() {
		return set.isPersistentSet();
	}
}