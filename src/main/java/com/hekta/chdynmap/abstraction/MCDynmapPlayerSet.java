package com.hekta.chdynmap.abstraction;

import com.laytonsmith.abstraction.AbstractionObject;
import com.laytonsmith.abstraction.MCOfflinePlayer;

/**
 *
 * @author Hekta
 */
public interface MCDynmapPlayerSet extends AbstractionObject {

	public String getId();

	public MCOfflinePlayer[] getPlayers();
	public void setPlayers(MCOfflinePlayer[] players);
	public void setPlayers(Iterable<MCOfflinePlayer> players);
	public void addPlayer(MCOfflinePlayer player);
	public void removePlayer(MCOfflinePlayer player);
	public boolean isPlayerInSet(MCOfflinePlayer player);

	public void delete();

	public boolean isSymmetric();
	public void setSymmetric(boolean isSymmetric);

	public boolean isPersistent();
}