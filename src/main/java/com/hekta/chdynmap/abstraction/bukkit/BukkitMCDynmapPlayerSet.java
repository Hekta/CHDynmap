package com.hekta.chdynmap.abstraction.bukkit;

import com.hekta.chdynmap.abstraction.MCDynmapPlayerSet;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCOfflinePlayer;
import com.laytonsmith.abstraction.StaticLayer;
import com.laytonsmith.annotations.abstraction;
import java.util.HashSet;
import java.util.Set;
import org.dynmap.markers.PlayerSet;

/**
 *
 * @author Hekta
 */
@abstraction(type = Implementation.Type.BUKKIT)
public class BukkitMCDynmapPlayerSet implements MCDynmapPlayerSet {

	private final PlayerSet _set;

	public BukkitMCDynmapPlayerSet(PlayerSet playerSet) {
		_set = playerSet;
	}

	public BukkitMCDynmapPlayerSet(Object object) {
		this((PlayerSet) object);
	}

	@Override
	public PlayerSet getHandle() {
		return _set;
	}

	@Override
	public String getId() {
		return _set.getSetID();
	}

	@Override
	public MCOfflinePlayer[] getPlayers() {
		Set<String> ps = _set.getPlayers();
		MCOfflinePlayer[] players = new MCOfflinePlayer[ps.size()];
		int i = 0;
		for (String playerName : ps) {
			players[i] = StaticLayer.GetServer().getOfflinePlayer(playerName);
			i++;
		}
		return players;
	}

	@Override
	public void setPlayers(MCOfflinePlayer[] players) {
		HashSet<String> playerNames = new HashSet<>();
		for (MCOfflinePlayer player : players) {
			playerNames.add(player.getName());
		}
		_set.setPlayers(playerNames);
	}

	@Override
	public void setPlayers(Iterable<MCOfflinePlayer> players) {
		HashSet<String> playerNames = new HashSet<>();
		for (MCOfflinePlayer player : players) {
			playerNames.add(player.getName());
		}
		_set.setPlayers(playerNames);
	}

	@Override
	public void addPlayer(MCOfflinePlayer player) {
		_set.addPlayer(player.getName());
	}

	@Override
	public void removePlayer(MCOfflinePlayer player) {
		_set.removePlayer(player.getName());
	}

	@Override
	public boolean isPlayerInSet(MCOfflinePlayer player) {
		return _set.isPlayerInSet(player.getName());
	}

	@Override
	public void delete() {
		_set.deleteSet();
	}

	@Override
	public boolean isSymmetric() {
		return _set.isSymmetricSet();
	}

	@Override
	public void setSymmetric(boolean isSymmetric) {
		_set.setSymmetricSet(isSymmetric);
	}

	@Override
	public boolean isPersistent() {
		return _set.isPersistentSet();
	}
}