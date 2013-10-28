package com.hekta.chdynmap.abstraction.bukkit;

import org.dynmap.markers.CircleMarker;

import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.StaticLayer;

import com.hekta.chdynmap.abstraction.MCDynmapCircleMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerFillStyle;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerLineStyle;

/**
 *
 * @author Hekta
 */
public class BukkitMCDynmapCircleMarker extends BukkitMCDynmapMarker implements MCDynmapCircleMarker {

	CircleMarker cm;

	public BukkitMCDynmapCircleMarker(CircleMarker marker) {
		super(marker);
		this.cm = marker;
	}

	@Override
	public CircleMarker getConcrete() {
		return cm;
	}

	public MCLocation getCenter() {
		return StaticLayer.GetLocation(StaticLayer.GetServer().getWorld(cm.getWorld()), cm.getCenterX(), cm.getCenterY(), cm.getCenterZ());
	}

	public void setCenter(MCLocation location) {
		cm.setCenter(location.getWorld().getName(), location.getX(), location.getY(), location.getZ());
	}

	public double getRadiusX() {
		return cm.getRadiusX();
	}

	public double getRadiusZ() {
		return cm.getRadiusZ();
	}

	public void setRadius(double radiusX, double radiusZ) {
		cm.setRadius(radiusX, radiusZ);
	}

	public MCDynmapMarkerLineStyle getLineStyle() {
		return new BukkitMCDynmapMarkerLineStyle(cm.getLineColor(), cm.getLineOpacity(), cm.getLineWeight());
	}

	public void setLineStyle(MCDynmapMarkerLineStyle style) {
		cm.setLineStyle(style.getWeight(), style.getOpacity(), style.getIntColor());
	}

	public MCDynmapMarkerFillStyle getFillStyle() {
		return new BukkitMCDynmapMarkerFillStyle(cm.getFillColor(), cm.getFillOpacity());
	}

	public void setFillStyle(MCDynmapMarkerFillStyle style) {
		cm.setFillStyle(style.getOpacity(), style.getIntColor());
	}

	public boolean isBoosted() {
		return cm.getBoostFlag();
	}

	public void setBoosted(boolean isBoosted) {
		cm.setBoostFlag(isBoosted);
	}
}