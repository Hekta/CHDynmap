package com.hekta.chdynmap.abstraction;

import java.util.List;

import com.laytonsmith.abstraction.MCLocation;

/**
 *
 * @author Hekta
 */
public interface MCDynmapAreaMarker extends MCDynmapMarker {

	public double getTopY();
	public double getBottomY();
	public void setRangeY(double yTop, double yBottom);
	public void setRangeY(MCLocation top, MCLocation bottom);

	public int getCornerCount();
	public MCLocation getCorner(int n);
	public List<MCLocation> getCorners();
	public void setCorner(int n, MCLocation location);
	public void setCorners(List<MCLocation> locations);
	public void deleteCorner(int n);

	public MCDynmapMarkerLineStyle getLineStyle();
	public void setLineStyle(MCDynmapMarkerLineStyle style);

	public MCDynmapMarkerFillStyle getFillStyle();
	public void setFillStyle(MCDynmapMarkerFillStyle style);

	public boolean isBoosted();
	public void setBoosted(boolean isBoosted);
}