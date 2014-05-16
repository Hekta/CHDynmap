package com.hekta.chdynmap.abstraction.bukkit;

import com.hekta.chdynmap.abstraction.MCDynmapAreaMarker;
import com.hekta.chdynmap.abstraction.MCDynmapCircleMarker;
import com.hekta.chdynmap.abstraction.MCDynmapIcon;
import com.hekta.chdynmap.abstraction.MCDynmapIconMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerSet;
import com.hekta.chdynmap.abstraction.MCDynmapMarker;
import com.hekta.chdynmap.abstraction.MCDynmapPolyLineMarker;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCWorld;
import com.laytonsmith.annotations.abstraction;
import java.util.List;
import java.util.Set;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.CircleMarker;
import org.dynmap.markers.GenericMarker;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;
import org.dynmap.markers.PolyLineMarker;

/**
 *
 * @author Hekta
 */
@abstraction(type = Implementation.Type.BUKKIT)
public class BukkitMCDynmapMarkerSet implements MCDynmapMarkerSet {

	private final MarkerSet _set;

	public BukkitMCDynmapMarkerSet(MarkerSet markerSet) {
		_set = markerSet;
	}

	public BukkitMCDynmapMarkerSet(Object object) {
		this((MarkerSet) object);
	}

	@Override
	public MarkerSet getHandle() {
		return _set;
	}

	@Override
	public MCDynmapMarker[] getMarkers() {
		Set<AreaMarker> ams = _set.getAreaMarkers();
		Set<CircleMarker> cms = _set.getCircleMarkers();
		Set<Marker> ims = _set.getMarkers();
		Set<PolyLineMarker> pms = _set.getPolyLineMarkers();
		MCDynmapMarker[] markers = new MCDynmapMarker[ams.size() + cms.size() + ims.size() + pms.size()];
		int i = 0;
		for (AreaMarker marker : ams) {
			markers[i] = new BukkitMCDynmapAreaMarker(marker);
			i++;
		}
		for (CircleMarker marker : cms) {
			markers[i] = new BukkitMCDynmapCircleMarker(marker);
			i++;
		}
		for (Marker marker : ims) {
			markers[i] = new BukkitMCDynmapIconMarker(marker);
			i++;
		}
		for (PolyLineMarker marker : pms) {
			markers[i] = new BukkitMCDynmapPolyLineMarker(marker);
			i++;
		}
		return markers;
	}

	@Override
	public MCDynmapAreaMarker[] getAreaMarkers() {
		Set<AreaMarker> ms = _set.getAreaMarkers();
		MCDynmapAreaMarker[] markers = new MCDynmapAreaMarker[ms.size()];
		int i = 0;
		for (AreaMarker marker : ms) {
			markers[i] = new BukkitMCDynmapAreaMarker(marker);
			i++;
		}
		return markers;
	}

	@Override
	public MCDynmapCircleMarker[] getCircleMarkers() {
		Set<CircleMarker> ms = _set.getCircleMarkers();
		MCDynmapCircleMarker[] markers = new MCDynmapCircleMarker[ms.size()];
		int i = 0;
		for (CircleMarker marker : ms) {
			markers[i] = new BukkitMCDynmapCircleMarker(marker);
			i++;
		}
		return markers;
	}

	@Override
	public MCDynmapIconMarker[] getIconMarkers() {
		Set<Marker> ms = _set.getMarkers();
		MCDynmapIconMarker[] markers = new MCDynmapIconMarker[ms.size()];
		int i = 0;
		for (Marker marker : ms) {
			markers[i] = new BukkitMCDynmapIconMarker(marker);
			i++;
		}
		return markers;
	}

	@Override
	public MCDynmapPolyLineMarker[] getPolyLineMarkers() {
		Set<PolyLineMarker> ms = _set.getPolyLineMarkers();
		MCDynmapPolyLineMarker[] markers = new MCDynmapPolyLineMarker[ms.size()];
		int i = 0;
		for (PolyLineMarker marker : ms) {
			markers[i] = new BukkitMCDynmapPolyLineMarker(marker);
			i++;
		}
		return markers;
	}

	@Override
	public MCDynmapMarker getMarker(String id) {
		AreaMarker am = _set.findAreaMarker(id);
		if (am != null) {
			return new BukkitMCDynmapAreaMarker(am);
		}
		CircleMarker cm = _set.findCircleMarker(id);
		if (cm != null) {
			return new BukkitMCDynmapCircleMarker(cm);
		}
		Marker ic = _set.findMarker(id);
		if (ic != null) {
			return new BukkitMCDynmapIconMarker(ic);
		}
		PolyLineMarker pm = _set.findPolyLineMarker(id);
		if (pm != null) {
			return new BukkitMCDynmapPolyLineMarker(pm);
		}
		return null;
	}

	@Override
	public MCDynmapAreaMarker getAreaMarker(String id) {
		AreaMarker marker = _set.findAreaMarker(id);
		if (marker != null) {
			return new BukkitMCDynmapAreaMarker(marker);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapCircleMarker getCircleMarker(String id) {
		CircleMarker marker = _set.findCircleMarker(id);
		if (marker != null) {
			return new BukkitMCDynmapCircleMarker(marker);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapIconMarker getIconMarker(String id) {
		Marker marker = _set.findMarker(id);
		if (marker != null) {
			return new BukkitMCDynmapIconMarker(marker);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapPolyLineMarker getPolyLineMarker(String id) {
		PolyLineMarker marker = _set.findPolyLineMarker(id);
		if (marker != null) {
			return new BukkitMCDynmapPolyLineMarker(marker);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapMarker getMarkerByLabel(String label) {
		GenericMarker marker = _set.findAreaMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapAreaMarker((AreaMarker) marker);
		}
		marker = _set.findCircleMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapCircleMarker((CircleMarker) marker);
		}
		marker = _set.findMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapIconMarker((Marker) marker);
		}
		marker = _set.findPolyLineMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapPolyLineMarker((PolyLineMarker) marker);
		}
		return null;
	}

	@Override
	public MCDynmapAreaMarker getAreaMarkerByLabel(String label) {
		AreaMarker marker = _set.findAreaMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapAreaMarker(marker);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapCircleMarker getCircleMarkerByLabel(String label) {
		CircleMarker marker = _set.findCircleMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapCircleMarker(marker);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapIconMarker getIconMarkerByLabel(String label) {
		Marker marker = _set.findMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapIconMarker(marker);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapPolyLineMarker getPolyLineMarkerByLabel(String label) {
		PolyLineMarker marker = _set.findPolyLineMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapPolyLineMarker(marker);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapAreaMarker createAreaMarker(String id, String label, boolean isHTML, MCWorld world, MCLocation[] corners, boolean isPersistent) {
		double[] Xs = new double[corners.length];
		double[] Zs = new double[corners.length];
		int i = 0;
		for (MCLocation location : corners) {
			Xs[i] = location.getX();
			Zs[i] = location.getZ();
			i++;
		}
		AreaMarker marker = _set.createAreaMarker(id, label, isHTML, world.getName(), Xs, Zs, isPersistent);
		if (marker != null) {
			return new BukkitMCDynmapAreaMarker(marker);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapAreaMarker createAreaMarker(String id, String label, boolean isHTML, MCWorld world, List<MCLocation> corners, boolean isPersistent) {
		int size = corners.size();
		double[] Xs = new double[size];
		double[] Zs = new double[size];
		int i = 0;
		for (MCLocation location : corners) {
			Xs[i] = location.getX();
			Zs[i] = location.getZ();
			i++;
		}
		AreaMarker marker = _set.createAreaMarker(id, label, isHTML, world.getName(), Xs, Zs, isPersistent);
		if (marker != null) {
			return new BukkitMCDynmapAreaMarker(marker);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapCircleMarker createCircleMarker(String id, String label, boolean isHTML, MCLocation center, double radiusX, double radiusZ, boolean isPersistent) {
		CircleMarker marker = _set.createCircleMarker(id, label, isHTML, center.getWorld().getName(), center.getX(), center.getY(), center.getZ(), radiusX, radiusZ, isPersistent);
		if (marker != null) {
			return new BukkitMCDynmapCircleMarker(marker);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapIconMarker createIconMarker(String id, String label, boolean isHTML, MCLocation location, MCDynmapIcon icon, boolean isPersistent) {
		Marker marker = _set.createMarker(id, label, isHTML, location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), ((BukkitMCDynmapIcon) icon).getHandle(), isPersistent);
		if (marker != null) {
			return new BukkitMCDynmapIconMarker(marker);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapPolyLineMarker createPolyLineMarker(String id, String label, boolean isHTML, MCWorld world, MCLocation[] corners, boolean isPersistent) {
		double[] Xs = new double[corners.length];
		double[] Ys = new double[corners.length];
		double[] Zs = new double[corners.length];
		int i = 0;
		for (MCLocation location : corners) {
			Xs[i] = location.getX();
			Ys[i] = location.getY();
			Zs[i] = location.getZ();
			i++;
		}
		PolyLineMarker marker = _set.createPolyLineMarker(id, label, isHTML, world.getName(), Xs, Ys, Zs, isPersistent);
		if (marker != null) {
			return new BukkitMCDynmapPolyLineMarker(marker);
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapPolyLineMarker createPolyLineMarker(String id, String label, boolean isHTML, MCWorld world, List<MCLocation> corners, boolean isPersistent) {
		int size = corners.size();
		double[] Xs = new double[size];
		double[] Ys = new double[size];
		double[] Zs = new double[size];
		int i = 0;
		for (MCLocation location : corners) {
			Xs[i] = location.getX();
			Ys[i] = location.getY();
			Zs[i] = location.getZ();
			i++;
		}
		PolyLineMarker marker = _set.createPolyLineMarker(id, label, isHTML, world.getName(), Xs, Ys, Zs, isPersistent);
		if (marker != null) {
			return new BukkitMCDynmapPolyLineMarker(marker);
		} else {
			return null;
		}
	}

	@Override
	public String getId() {
		return _set.getMarkerSetID();
	}

	@Override
	public String getLabel() {
		return _set.getMarkerSetLabel();
	}

	@Override
	public void setLabel(String label) {
		_set.setMarkerSetLabel(label);
	}

	@Override
	public boolean isPersistent() {
		return _set.isMarkerSetPersistent();
	}

	@Override
	public boolean isRestricted() {
		return (_set.getAllowedMarkerIcons() != null);
	}

	@Override
	public MCDynmapIcon[] getAllowedIcons() {
		if (isRestricted()) {
			Set<MarkerIcon> is = _set.getAllowedMarkerIcons();
			MCDynmapIcon[] icons = new MCDynmapIcon[is.size()];
			int i = 0;
			for (MarkerIcon icon : is) {
				icons[i] = new BukkitMCDynmapIcon(icon);
				i++;
			}
			return icons;
		} else {
			return null;
		}
	}

	@Override
	public boolean iconIsAllowed(MCDynmapIcon icon) {
		return _set.isAllowedMarkerIcon(((BukkitMCDynmapIcon) icon).getHandle());
	}

	@Override
	public void addAllowedIcon(MCDynmapIcon icon) {
		_set.addAllowedMarkerIcon(((BukkitMCDynmapIcon) icon).getHandle());
	}

	@Override
	public void removeAllowedIcon(MCDynmapIcon icon) {
		_set.removeAllowedMarkerIcon(((BukkitMCDynmapIcon) icon).getHandle());
	}

	@Override
	public MCDynmapIcon[] getIconsInUse() {
		Set<MarkerIcon> is = _set.getMarkerIconsInUse();
		MCDynmapIcon[] icons = new MCDynmapIcon[is.size()];
		int i = 0;
		for (MarkerIcon icon : is) {
			icons[i] = new BukkitMCDynmapIcon(icon);
			i++;
		}
		return icons;
	}

	@Override
	public void delete() {
		_set.deleteMarkerSet();
	}

	@Override
	public boolean isHiddenByDefault() {
		return _set.getHideByDefault();
	}

	@Override
	public void setHiddenByDefault(boolean isHidden) {
		_set.setHideByDefault(isHidden);
	}

	@Override
	public int getLayerPriority() {
		return _set.getLayerPriority();
	}

	@Override
	public void setLayerPriority(int priority) {
		_set.setLayerPriority(priority);
	}

	@Override
	public int getMinZoom() {
		return _set.getMinZoom();
	}

	@Override
	public void setMinZoom(int minZoom) {
		_set.setMinZoom(minZoom);
	}

	@Override
	public int getMaxZoom() {
		return _set.getMaxZoom();
	}

	@Override
	public void setMaxZoom(int minZoom) {
		_set.setMaxZoom(minZoom);
	}

	@Override
	public Boolean labelIsShown() {
		return _set.getLabelShow();
	}

	@Override
	public void setlabelIsShown(Boolean labelShown) {
		_set.setLabelShow(labelShown);
	}

	@Override
	public MCDynmapIcon getDefaultIcon() {
		return new BukkitMCDynmapIcon(_set.getDefaultMarkerIcon());
	}

	@Override
	public void setDefaultIcon(MCDynmapIcon icon) {
		_set.setDefaultMarkerIcon(((BukkitMCDynmapIcon) icon).getHandle());
	}
}