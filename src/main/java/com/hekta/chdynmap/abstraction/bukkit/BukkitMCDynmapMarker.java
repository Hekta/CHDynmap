package com.hekta.chdynmap.abstraction.bukkit;

import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.CircleMarker;
import org.dynmap.markers.GenericMarker;
import org.dynmap.markers.Marker;
import org.dynmap.markers.PolyLineMarker;

import com.laytonsmith.abstraction.MCWorld;
import com.laytonsmith.abstraction.StaticLayer;

import com.hekta.chdynmap.abstraction.MCDynmapAreaMarker;
import com.hekta.chdynmap.abstraction.MCDynmapCircleMarker;
import com.hekta.chdynmap.abstraction.MCDynmapIconMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerSet;
import com.hekta.chdynmap.abstraction.enums.MCDynmapMarkerType;
import com.hekta.chdynmap.abstraction.MCDynmapPolyLineMarker;

/**
 *
 * @author Hekta
 */
public class BukkitMCDynmapMarker implements MCDynmapMarker {

	GenericMarker m;
	MCDynmapMarkerType type;

	public BukkitMCDynmapMarker(GenericMarker marker) {
		this.m = marker;
		if (marker instanceof AreaMarker) {
			this.type = MCDynmapMarkerType.AREA;
		} else if (marker instanceof CircleMarker) {
			this.type = MCDynmapMarkerType.CIRCLE;
		} else if (marker instanceof Marker) {
			this.type = MCDynmapMarkerType.ICON;
		} else if (marker instanceof PolyLineMarker) {
			this.type = MCDynmapMarkerType.POLYLINE;
		} else {
			throw new IllegalArgumentException("Unknown marker type, is CHDynmap up to date?");
		}
	}

	public GenericMarker getConcrete() {
		return m;
	}

	public MCDynmapMarkerType getType() {
		return type;
	}

	public MCDynmapAreaMarker getAreaMarker() {
		if (type == MCDynmapMarkerType.AREA) {
			return new BukkitMCDynmapAreaMarker((AreaMarker) m);
		} else {
			return null;
		}
	}

	public MCDynmapCircleMarker getCircleMarker() {
		if (type == MCDynmapMarkerType.CIRCLE) {
			return new BukkitMCDynmapCircleMarker((CircleMarker) m);
		} else {
			return null;
		}
	}

	public MCDynmapIconMarker getIconMarker() {
		if (type == MCDynmapMarkerType.ICON) {
			return new BukkitMCDynmapIconMarker((Marker) m);
		} else {
			return null;
		}
	}

	public MCDynmapPolyLineMarker getPolyLineMarker() {
		if (type == MCDynmapMarkerType.POLYLINE) {
			return new BukkitMCDynmapPolyLineMarker((PolyLineMarker) m);
		} else {
			return null;
		}
	}

	public String getId() {
		return m.getMarkerID();
	}

	public MCDynmapMarkerSet getSet() {
		return new BukkitMCDynmapMarkerSet(m.getMarkerSet());
	}

	public void setSet(MCDynmapMarkerSet newSet) {
		m.setMarkerSet(((BukkitMCDynmapMarkerSet) newSet).getConcrete());
	}

	public void delete() {
		m.deleteMarker();
	}

	public MCWorld getWorld() {
		return StaticLayer.GetServer().getWorld(m.getWorld());
	}

	public String getNormalizedWorld() {
		return m.getNormalizedWorld();
	}

	public boolean isPersistent() {
		return m.isPersistentMarker();
	}

	public String getLabel() {
		return m.getLabel();
	}

	public void setLabel(String label) {
		m.setLabel(label);
	}

	public void setLabel(String label, boolean isHTMLMarkup) {
		m.setLabel(label, isHTMLMarkup);
	}

	public boolean isLabelMarkup() {
		return m.isLabelMarkup();
	}

	public String getDescription() {
		switch (type) {
			case AREA:
				return ((AreaMarker) m).getDescription();
			case CIRCLE:
				return ((CircleMarker) m).getDescription();
			case ICON:
				return ((Marker) m).getDescription();
			case POLYLINE:
				return ((PolyLineMarker) m).getDescription();
			default:
				throw new IllegalArgumentException("Unknown marker type, is CHDynmap up to date?");
		}
	}

	public void setDescription(String description) {
		switch (type) {
			case AREA:
				((AreaMarker) m).setDescription(description);
			case CIRCLE:
				((CircleMarker) m).setDescription(description);
			case ICON:
				((Marker) m).setDescription(description);
			case POLYLINE:
				((PolyLineMarker) m).setDescription(description);
		}
	}
}