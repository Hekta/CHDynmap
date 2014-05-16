package com.hekta.chdynmap.abstraction;

import com.laytonsmith.abstraction.MCLocation;
import java.util.List;

/**
 *
 * @author Hekta
 */
public interface MCDynmapPolyLineMarker extends MCDynmapMarker {

	public int getCornerCount();
	public MCLocation getCorner(int n);
	public MCLocation[] getCorners();
	public void setCorner(int n, MCLocation location);
	public void setCorners(MCLocation[] locations);
	public void setCorners(List<MCLocation> locations);
	public void deleteCorner(int n);

	public MCDynmapMarkerLineStyle getLineStyle();
	public void setLineStyle(MCDynmapMarkerLineStyle style);
}