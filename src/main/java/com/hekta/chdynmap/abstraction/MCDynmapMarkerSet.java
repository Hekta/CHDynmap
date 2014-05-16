package com.hekta.chdynmap.abstraction;

import com.laytonsmith.abstraction.AbstractionObject;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCWorld;
import java.util.List;

/**
 *
 * @author Hekta
 */
public interface MCDynmapMarkerSet extends AbstractionObject {

	public MCDynmapMarker[] getMarkers();
	public MCDynmapAreaMarker[] getAreaMarkers();
	public MCDynmapCircleMarker[] getCircleMarkers();
	public MCDynmapIconMarker[] getIconMarkers();
	public MCDynmapPolyLineMarker[] getPolyLineMarkers();

	public MCDynmapMarker getMarker(String id);
	public MCDynmapAreaMarker getAreaMarker(String id);
	public MCDynmapCircleMarker getCircleMarker(String id);
	public MCDynmapIconMarker getIconMarker(String id);
	public MCDynmapPolyLineMarker getPolyLineMarker(String id);

	public MCDynmapMarker getMarkerByLabel(String label);
	public MCDynmapAreaMarker getAreaMarkerByLabel(String label);
	public MCDynmapCircleMarker getCircleMarkerByLabel(String label);
	public MCDynmapIconMarker getIconMarkerByLabel(String label);
	public MCDynmapPolyLineMarker getPolyLineMarkerByLabel(String label);

	public MCDynmapAreaMarker createAreaMarker(String id, String label, boolean isHTML, MCWorld world, MCLocation[] corners, boolean isPersistent);
	public MCDynmapAreaMarker createAreaMarker(String id, String label, boolean isHTML, MCWorld world, List<MCLocation> corners, boolean isPersistent);
	public MCDynmapCircleMarker createCircleMarker(String id, String label, boolean isHTML, MCLocation center, double radiusX, double radiusZ, boolean isPersistent);
	public MCDynmapIconMarker createIconMarker(String id, String label, boolean isHTML, MCLocation location, MCDynmapIcon icon, boolean isPersistent);
	public MCDynmapPolyLineMarker createPolyLineMarker(String id, String label, boolean isHTML, MCWorld world, MCLocation[] corners, boolean isPersistent);
	public MCDynmapPolyLineMarker createPolyLineMarker(String id, String label, boolean isHTML, MCWorld world, List<MCLocation> corners, boolean isPersistent);

	public String getId();

	public String getLabel();
	public void setLabel(String label);

	public boolean isPersistent();

	public boolean isRestricted();
	public MCDynmapIcon[] getAllowedIcons();
	public boolean iconIsAllowed(MCDynmapIcon icon);
	public void addAllowedIcon(MCDynmapIcon icon);
	public void removeAllowedIcon(MCDynmapIcon icon);

	public MCDynmapIcon[] getIconsInUse();

	public void delete();

	public boolean isHiddenByDefault();
	public void setHiddenByDefault(boolean isHidden);

	public int getLayerPriority();
	public void setLayerPriority(int priority);

	public int getMinZoom();
	public void setMinZoom(int minZoom);
	public int getMaxZoom();
	public void setMaxZoom(int zoom);

	public Boolean labelIsShown();
	public void setlabelIsShown(Boolean labelShown);

	public MCDynmapIcon getDefaultIcon();
	public void setDefaultIcon(MCDynmapIcon icon);
}