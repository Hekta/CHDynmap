package com.hekta.chdynmap.abstraction.bukkit;

import com.hekta.chdynmap.abstraction.MCDynmapMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerSet;
import com.hekta.chdynmap.abstraction.enums.MCDynmapMarkerType;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCWorld;
import com.laytonsmith.abstraction.StaticLayer;
import com.laytonsmith.annotations.abstraction;
import org.dynmap.markers.GenericMarker;
import org.dynmap.markers.MarkerDescription;

/**
 *
 * @author Hekta
 */
@abstraction(type = Implementation.Type.BUKKIT)
public abstract class BukkitMCDynmapMarker implements MCDynmapMarker {

	private final MarkerDescription _marker;

	public BukkitMCDynmapMarker(MarkerDescription marker) {
		_marker = marker;
	}

	@Override
	public abstract GenericMarker getHandle();

	@Override
	public abstract MCDynmapMarkerType getType();

	@Override
	public String getId() {
		return _marker.getMarkerID();
	}

	@Override
	public MCDynmapMarkerSet getSet() {
		return new BukkitMCDynmapMarkerSet(_marker.getMarkerSet());
	}

	@Override
	public void setSet(MCDynmapMarkerSet newSet) {
		_marker.setMarkerSet(((BukkitMCDynmapMarkerSet) newSet).getHandle());
	}

	@Override
	public void delete() {
		_marker.deleteMarker();
	}

	@Override
	public MCWorld getWorld() {
		return StaticLayer.GetServer().getWorld(_marker.getWorld());
	}

	@Override
	public String getNormalizedWorld() {
		return _marker.getNormalizedWorld();
	}

	@Override
	public boolean isPersistent() {
		return _marker.isPersistentMarker();
	}

	@Override
	public String getLabel() {
		return _marker.getLabel();
	}

	@Override
	public void setLabel(String label) {
		_marker.setLabel(label);
	}

	@Override
	public void setLabel(String label, boolean isHTMLMarkup) {
		_marker.setLabel(label, isHTMLMarkup);
	}

	@Override
	public boolean isLabelMarkup() {
		return _marker.isLabelMarkup();
	}

	@Override
	public String getDescription() {
		return _marker.getDescription();
	}

	@Override
	public void setDescription(String description) {
		_marker.setDescription(description);
	}

	@Override
	public int getMinZoom() {
		return _marker.getMinZoom();
	}

	@Override
	public void setMinZoom(int zoom) {
		_marker.setMinZoom(zoom);
	}

	@Override
	public int getMaxZoom() {
		return _marker.getMinZoom();
	}

	@Override
	public void setMaxZoom(int zoom) {
		_marker.setMaxZoom(zoom);
	}
}