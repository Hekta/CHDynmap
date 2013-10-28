package com.hekta.chdynmap.abstraction;

import java.util.List;

import com.laytonsmith.abstraction.MCLocation;

/**
 *
 * @author Hekta
 */
public interface MCDynmapPolyLineMarker extends MCDynmapMarker {

	public int getCornerCount();
	public MCLocation getCorner(int n);
	public List<MCLocation> getCorners();
	public void setCorner(int n, MCLocation location);
	public void setCorners(List<MCLocation> locations);
	public void deleteCorner(int n);

	public MCDynmapMarkerLineStyle getLineStyle();
	public void setLineStyle(MCDynmapMarkerLineStyle style);
}