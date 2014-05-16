package com.hekta.chdynmap.abstraction.bukkit;

import com.hekta.chdynmap.abstraction.MCDynmapAreaMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerFillStyle;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerLineStyle;
import com.hekta.chdynmap.abstraction.enums.MCDynmapMarkerType;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCWorld;
import com.laytonsmith.abstraction.StaticLayer;
import com.laytonsmith.annotations.abstraction;
import java.util.List;
import org.dynmap.markers.AreaMarker;

/**
 *
 * @author Hekta
 */
@abstraction(type = Implementation.Type.BUKKIT)
public class BukkitMCDynmapAreaMarker extends BukkitMCDynmapMarker implements MCDynmapAreaMarker {

	private static final MCDynmapMarkerType TYPE = MCDynmapMarkerType.AREA;

	private final AreaMarker _marker;

	public BukkitMCDynmapAreaMarker(AreaMarker marker) {
		super(marker);
		_marker = marker;
	}

	public BukkitMCDynmapAreaMarker(Object object) {
		this((AreaMarker) object);
	}

	@Override
	public AreaMarker getHandle() {
		return _marker;
	}

	@Override
	public MCDynmapMarkerType getType() {
		return TYPE;
	}

	@Override
	public double getTopY() {
		return _marker.getTopY();
	}

	@Override
	public double getBottomY() {
		return _marker.getBottomY();
	}

	@Override
	public void setRangeY(double yTop, double yBottom) {
		_marker.setRangeY(yTop, yBottom);
	}

	@Override
	public void setRangeY(MCLocation top, MCLocation bottom) {
		_marker.setRangeY(top.getY(), bottom.getY());
	}

	@Override
	public int getCornerCount() {
		return _marker.getCornerCount();
	}

	@Override
	public MCLocation getCorner(int n) {
		return StaticLayer.GetLocation(StaticLayer.GetServer().getWorld(_marker.getWorld()), _marker.getCornerX(n), 0, _marker.getCornerZ(n));
	}

	@Override
	public MCLocation[] getCorners() {
		int count = _marker.getCornerCount();
		MCLocation[] corners = new MCLocation[count];
		MCWorld world = StaticLayer.GetServer().getWorld(_marker.getWorld());
		for (int i = 0 ; i < count ; i++) {
			corners[i] = StaticLayer.GetLocation(world, _marker.getCornerX(i), 0, _marker.getCornerZ(i));
		}
		return corners;
	}

	@Override
	public void setCorner(int n, MCLocation location) {
		_marker.setCornerLocation(n, location.getX(), location.getZ());
	}

	@Override
	public void setCorners(MCLocation[] locations) {
		double[] Xs = new double[locations.length];
		double[] Zs = new double[locations.length];
		int i = 0;
		for (MCLocation location : locations) {
			Xs[i] = location.getX();
			Zs[i] = location.getZ();
			i++;
		}
		_marker.setCornerLocations(Xs, Zs);
	}

	@Override
	public void setCorners(List<MCLocation> locations) {
		int size = locations.size();
		double[] Xs = new double[size];
		double[] Zs = new double[size];
		int i = 0;
		for (MCLocation location : locations) {
			Xs[i] = location.getX();
			Zs[i] = location.getZ();
			i++;
		}
		_marker.setCornerLocations(Xs, Zs);
	}

	@Override
	public void deleteCorner(int n) {
		_marker.deleteCorner(n);
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