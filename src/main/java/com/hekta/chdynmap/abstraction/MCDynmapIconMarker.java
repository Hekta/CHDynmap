package com.hekta.chdynmap.abstraction;

import com.laytonsmith.abstraction.MCLocation;

/**
 *
 * @author Hekta
 */
public interface MCDynmapIconMarker extends MCDynmapMarker {

	public MCLocation getLocation();
	public void setLocation(MCLocation location);

	public MCDynmapIcon getIcon();
	public boolean setIcon(MCDynmapIcon icon);
}