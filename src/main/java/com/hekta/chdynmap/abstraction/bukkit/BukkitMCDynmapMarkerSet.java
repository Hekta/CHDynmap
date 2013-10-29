package com.hekta.chdynmap.abstraction.bukkit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCWorld;

import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.CircleMarker;
import org.dynmap.markers.GenericMarker;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;
import org.dynmap.markers.PolyLineMarker;

import com.hekta.chdynmap.abstraction.MCDynmapAreaMarker;
import com.hekta.chdynmap.abstraction.MCDynmapCircleMarker;
import com.hekta.chdynmap.abstraction.MCDynmapIcon;
import com.hekta.chdynmap.abstraction.MCDynmapIconMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerSet;
import com.hekta.chdynmap.abstraction.MCDynmapMarker;
import com.hekta.chdynmap.abstraction.MCDynmapPolyLineMarker;

/**
 *
 * @author Hekta
 */
public class BukkitMCDynmapMarkerSet implements MCDynmapMarkerSet {

	MarkerSet set;

	public BukkitMCDynmapMarkerSet(MarkerSet markerSet) {
		this.set = markerSet;
	}

	public MarkerSet getConcrete() {
		return set;
	}

	public Set<MCDynmapMarker> getMarkers() {
		Set<MCDynmapMarker> markers = new HashSet<MCDynmapMarker>();
		for (AreaMarker marker : set.getAreaMarkers()) {
			markers.add(new BukkitMCDynmapAreaMarker(marker));
		}
		for (CircleMarker marker : set.getCircleMarkers()) {
			markers.add(new BukkitMCDynmapCircleMarker(marker));
		}
		for (Marker marker : set.getMarkers()) {
			markers.add(new BukkitMCDynmapIconMarker(marker));
		}
		for (PolyLineMarker marker : set.getPolyLineMarkers()) {
			markers.add(new BukkitMCDynmapPolyLineMarker(marker));
		}
		return markers;
	}

	public Set<MCDynmapAreaMarker> getAreaMarkers() {
		Set<MCDynmapAreaMarker> markers = new HashSet<MCDynmapAreaMarker>();
		for (AreaMarker marker : set.getAreaMarkers()) {
			markers.add(new BukkitMCDynmapAreaMarker(marker));
		}
		return markers;
	}

	public Set<MCDynmapCircleMarker> getCircleMarkers() {
		Set<MCDynmapCircleMarker> markers = new HashSet<MCDynmapCircleMarker>();
		for (CircleMarker marker : set.getCircleMarkers()) {
			markers.add(new BukkitMCDynmapCircleMarker(marker));
		}
		return markers;
	}

	public Set<MCDynmapIconMarker> getIconMarkers() {
		Set<MCDynmapIconMarker> markers = new HashSet<MCDynmapIconMarker>();
		for (Marker marker : set.getMarkers()) {
			markers.add(new BukkitMCDynmapIconMarker(marker));
		}
		return markers;
	}

	public Set<MCDynmapPolyLineMarker> getPolyLineMarkers() {
		Set<MCDynmapPolyLineMarker> markers = new HashSet<MCDynmapPolyLineMarker>();
		for (PolyLineMarker marker : set.getPolyLineMarkers()) {
			markers.add(new BukkitMCDynmapPolyLineMarker(marker));
		}
		return markers;
	}

	public MCDynmapMarker getMarker(String id) {
		AreaMarker am = set.findAreaMarker(id);
		if (am != null) {
			return new BukkitMCDynmapAreaMarker(am);
		}
		CircleMarker cm = set.findCircleMarker(id);
		if (cm != null) {
			return new BukkitMCDynmapCircleMarker(cm);
		}
		Marker ic = set.findMarker(id);
		if (ic != null) {
			return new BukkitMCDynmapIconMarker(ic);
		}
		PolyLineMarker pm = set.findPolyLineMarker(id);
		if (pm != null) {
			return new BukkitMCDynmapPolyLineMarker(pm);
		}
		return null;
	}

	public MCDynmapAreaMarker getAreaMarker(String id) {
		AreaMarker marker = set.findAreaMarker(id);
		if (marker != null) {
			return new BukkitMCDynmapAreaMarker(marker);
		} else {
			return null;
		}
	}

	public MCDynmapCircleMarker getCircleMarker(String id) {
		CircleMarker marker = set.findCircleMarker(id);
		if (marker != null) {
			return new BukkitMCDynmapCircleMarker(marker);
		} else {
			return null;
		}
	}

	public MCDynmapIconMarker getIconMarker(String id) {
		Marker marker = set.findMarker(id);
		if (marker != null) {
			return new BukkitMCDynmapIconMarker(marker);
		} else {
			return null;
		}
	}

	public MCDynmapPolyLineMarker getPolyLineMarker(String id) {
		PolyLineMarker marker = set.findPolyLineMarker(id);
		if (marker != null) {
			return new BukkitMCDynmapPolyLineMarker(marker);
		} else {
			return null;
		}
	}

	public MCDynmapMarker getMarkerByLabel(String label) {
		GenericMarker marker = set.findAreaMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapMarker(marker);
		}
		marker = set.findCircleMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapMarker(marker);
		}
		marker = set.findMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapMarker(marker);
		}
		marker = set.findPolyLineMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapMarker(marker);
		}
		return null;
	}

	public MCDynmapAreaMarker getAreaMarkerByLabel(String label) {
		AreaMarker marker = set.findAreaMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapAreaMarker(marker);
		} else {
			return null;
		}
	}

	public MCDynmapCircleMarker getCircleMarkerByLabel(String label) {
		CircleMarker marker = set.findCircleMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapCircleMarker(marker);
		} else {
			return null;
		}
	}

	public MCDynmapIconMarker getIconMarkerByLabel(String label) {
		Marker marker = set.findMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapIconMarker(marker);
		} else {
			return null;
		}
	}

	public MCDynmapPolyLineMarker getPolyLineMarkerByLabel(String label) {
		PolyLineMarker marker = set.findPolyLineMarkerByLabel(label);
		if (marker != null) {
			return new BukkitMCDynmapPolyLineMarker(marker);
		} else {
			return null;
		}
	}

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
		AreaMarker marker = set.createAreaMarker(id, label, isHTML, world.getName(), Xs, Zs, isPersistent);
		if (marker != null) {
			return new BukkitMCDynmapAreaMarker(marker);
		} else {
			return null;
		}
	}

	public MCDynmapCircleMarker createCircleMarker(String id, String label, boolean isHTML, MCLocation center, double radiusX, double radiusZ, boolean isPersistent) {
		CircleMarker marker = set.createCircleMarker(id, label, isHTML, center.getWorld().getName(), center.getX(), center.getY(), center.getZ(), radiusX, radiusZ, isPersistent);
		if (marker != null) {
			return new BukkitMCDynmapCircleMarker(marker);
		} else {
			return null;
		}
	}

	public MCDynmapIconMarker createIconMarker(String id, String label, boolean isHTML, MCLocation location, MCDynmapIcon icon, boolean isPersistent) {
		Marker marker = set.createMarker(id, label, isHTML, location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), ((BukkitMCDynmapIcon) icon).getConcrete(), isPersistent);
		if (marker != null) {
			return new BukkitMCDynmapIconMarker(marker);
		} else {
			return null;
		}
	}

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
		PolyLineMarker marker = set.createPolyLineMarker(id, label, isHTML, world.getName(), Xs, Ys, Zs, isPersistent);
		if (marker != null) {
			return new BukkitMCDynmapPolyLineMarker(marker);
		} else {
			return null;
		}
	}

	public String getId() {
		return set.getMarkerSetID();
	}

	public String getLabel() {
		return set.getMarkerSetLabel();
	}

	public void setLabel(String label) {
		set.setMarkerSetLabel(label);
	}

	public boolean isPersistent() {
		return set.isMarkerSetPersistent();
	}

	public boolean isRestricted() {
		return (set.getAllowedMarkerIcons() != null);
	}

	public Set<MCDynmapIcon> getAllowedIcons() {
		if (this.isRestricted()) {
			Set<MCDynmapIcon> icons = new HashSet<MCDynmapIcon>();
			for (MarkerIcon icon : set.getAllowedMarkerIcons()) {
				icons.add(new BukkitMCDynmapIcon(icon));
			}
			return icons;
		} else {
			return null;
		}
	}

	public boolean iconIsAllowed(MCDynmapIcon icon) {
		return set.isAllowedMarkerIcon(((BukkitMCDynmapIcon) icon).getConcrete());
	}

	public void addAllowedIcon(MCDynmapIcon icon) {
		set.addAllowedMarkerIcon(((BukkitMCDynmapIcon) icon).getConcrete());
	}

	public void removeAllowedIcon(MCDynmapIcon icon) {
		set.removeAllowedMarkerIcon(((BukkitMCDynmapIcon) icon).getConcrete());
	}

	public Set<MCDynmapIcon> getIconsInUse() {
		Set<MCDynmapIcon> icons = new HashSet<MCDynmapIcon>();
		for (MarkerIcon icon : set.getMarkerIconsInUse()) {
			icons.add(new BukkitMCDynmapIcon(icon));
		}
		return icons;
	}

	public void delete() {
		set.deleteMarkerSet();
	}

	public boolean isHiddenByDefault() {
		return set.getHideByDefault();
	}

	public void setHiddenByDefault(boolean isHidden) {
		set.setHideByDefault(isHidden);
	}

	public int getLayerPriority() {
		return set.getLayerPriority();
	}

	public void setLayerPriority(int priority) {
		set.setLayerPriority(priority);
	}

	public int getMinZoom() {
		return set.getMinZoom();
	}

	public void setMinZoom(int minZoom) {
		set.setMinZoom(minZoom);
	}

	public Boolean labelIsShown() {
		return set.getLabelShow();
	}

	public void setlabelIsShown(Boolean labelShown) {
		set.setLabelShow(labelShown);
	}

	public MCDynmapIcon getDefaultIcon() {
		return new BukkitMCDynmapIcon(set.getDefaultMarkerIcon());
	}

	public void setDefaultIcon(MCDynmapIcon icon) {
		set.setDefaultMarkerIcon(((BukkitMCDynmapIcon) icon).getConcrete());
	}
}