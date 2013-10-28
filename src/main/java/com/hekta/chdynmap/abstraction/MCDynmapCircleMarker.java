package com.hekta.chdynmap.abstraction;

import com.laytonsmith.abstraction.MCLocation;

/**
 *
 * @author Hekta
 */
public interface MCDynmapCircleMarker extends MCDynmapMarker {

	public MCLocation getCenter();
	public void setCenter(MCLocation location);

	public double getRadiusX();
	public double getRadiusZ();
	public void setRadius(double radiusX, double radiusZ);

	public MCDynmapMarkerLineStyle getLineStyle();
	public void setLineStyle(MCDynmapMarkerLineStyle style);

	public MCDynmapMarkerFillStyle getFillStyle();
	public void setFillStyle(MCDynmapMarkerFillStyle style);

	public boolean isBoosted();
	public void setBoosted(boolean isBoosted);
}