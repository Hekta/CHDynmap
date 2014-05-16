package com.hekta.chdynmap.abstraction.bukkit;

import com.hekta.chdynmap.abstraction.MCDynmapCircleMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerFillStyle;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerLineStyle;
import com.hekta.chdynmap.abstraction.enums.MCDynmapMarkerType;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.StaticLayer;
import com.laytonsmith.annotations.abstraction;
import org.dynmap.markers.CircleMarker;

/**
 *
 * @author Hekta
 */
@abstraction(type = Implementation.Type.BUKKIT)
public class BukkitMCDynmapCircleMarker extends BukkitMCDynmapMarker implements MCDynmapCircleMarker {

	private static final MCDynmapMarkerType TYPE = MCDynmapMarkerType.CIRCLE;

	private final CircleMarker _marker;

	public BukkitMCDynmapCircleMarker(CircleMarker marker) {
		super(marker);
		_marker = marker;
	}

	public BukkitMCDynmapCircleMarker(Object object) {
		this((CircleMarker) object);
	}

	@Override
	public CircleMarker getHandle() {
		return _marker;
	}

	@Override
	public MCDynmapMarkerType getType() {
		return TYPE;
	}

	@Override
	public MCLocation getCenter() {
		return StaticLayer.GetLocation(StaticLayer.GetServer().getWorld(_marker.getWorld()), _marker.getCenterX(), _marker.getCenterY(), _marker.getCenterZ());
	}

	@Override
	public void setCenter(MCLocation location) {
		_marker.setCenter(location.getWorld().getName(), location.getX(), location.getY(), location.getZ());
	}

	@Override
	public double getRadiusX() {
		return _marker.getRadiusX();
	}

	@Override
	public double getRadiusZ() {
		return _marker.getRadiusZ();
	}

	@Override
	public void setRadius(double radiusX, double radiusZ) {
		_marker.setRadius(radiusX, radiusZ);
	}

	@Override
	public MCDynmapMarkerLineStyle getLineStyle() {
		return new BukkitMCDynmapMarkerLineStyle(_marker.getLineColor(), _marker.getLineOpacity(), _marker.getLineWeight());
	}

	@Override
	public void setLineStyle(MCDynmapMarkerLineStyle style) {
		_marker.setLineStyle(style.getWeight(), style.getOpacity(), style.getIntColor());
	}

	@Override
	public MCDynmapMarkerFillStyle getFillStyle() {
		return new BukkitMCDynmapMarkerFillStyle(_marker.getFillColor(), _marker.getFillOpacity());
	}

	@Override
	public void setFillStyle(MCDynmapMarkerFillStyle style) {
		_marker.setFillStyle(style.getOpacity(), style.getIntColor());
	}

	@Override
	public boolean isBoosted() {
		return _marker.getBoostFlag();
	}

	@Override
	public void setBoosted(boolean isBoosted) {
		_marker.setBoostFlag(isBoosted);
	}
}