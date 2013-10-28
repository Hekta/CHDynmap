package com.hekta.chdynmap.abstraction;

import java.util.Set;

import com.laytonsmith.abstraction.MCOfflinePlayer;

/**
 *
 * @author Hekta
 */
public interface MCDynmapPlayerSet {

	public String getId();

	public Set<MCOfflinePlayer> getPlayers();
	public void setPlayers(Set<MCOfflinePlayer> players);
	public void addPlayer(MCOfflinePlayer player);
	public void removePlayer(MCOfflinePlayer player);
	public boolean isPlayerInSet(MCOfflinePlayer player);

	public void delete();

	public boolean isSymmetric();
	public void setSymmetric(boolean isSymmetric);

	public boolean isPersistent();
}