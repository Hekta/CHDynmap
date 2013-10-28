package com.hekta.chdynmap.abstraction.bukkit;

import java.util.ArrayList;
import java.util.List;

import org.dynmap.markers.AreaMarker;

import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCWorld;
import com.laytonsmith.abstraction.StaticLayer;

import com.hekta.chdynmap.abstraction.MCDynmapAreaMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerFillStyle;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerLineStyle;

/**
 *
 * @author Hekta
 */
public class BukkitMCDynmapAreaMarker extends BukkitMCDynmapMarker implements MCDynmapAreaMarker {

	AreaMarker am;

	public BukkitMCDynmapAreaMarker(AreaMarker marker) {
		super(marker);
		this.am = marker;
	}

	@Override
	public AreaMarker getConcrete() {
		return am;
	}

	public double getTopY() {
		return am.getTopY();
	}

	public double getBottomY() {
		return am.getBottomY();
	}

	public void setRangeY(double yTop, double yBottom) {
		am.setRangeY(yTop, yBottom);
	}

	public void setRangeY(MCLocation top, MCLocation bottom) {
		am.setRangeY(top.getY(), bottom.getY());
	}

	public int getCornerCount() {
		return am.getCornerCount();
	}

	public MCLocation getCorner(int n) {
		return StaticLayer.GetLocation(StaticLayer.GetServer().getWorld(am.getWorld()), am.getCornerX(n), 0, am.getCornerZ(n));
	}

	public List<MCLocation> getCorners() {
		int count = am.getCornerCount();
		List<MCLocation> corners = new ArrayList<MCLocation>();
		MCWorld world = StaticLayer.GetServer().getWorld(m.getWorld());
		for (int i = 0 ; i < count ; i++) {
			corners.add(StaticLayer.GetLocation(world, am.getCornerX(i), 0, am.getCornerZ(i)));
		}
		return corners;
	}

	public void setCorner(int n, MCLocation location) {
		am.setCornerLocation(n, location.getX(), location.getZ());
	}

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
		am.setCornerLocations(Xs, Zs);
	}

	public void deleteCorner(int n) {
		am.deleteCorner(n);
	}

	public MCDynmapMarkerLineStyle getLineStyle() {
		return new BukkitMCDynmapMarkerLineStyle(am.getLineColor(), am.getLineOpacity(), am.getLineWeight());
	}

	public void setLineStyle(MCDynmapMarkerLineStyle style) {
		am.setLineStyle(style.getWeight(), style.getOpacity(), style.getIntColor());
	}

	public MCDynmapMarkerFillStyle getFillStyle() {
		return new BukkitMCDynmapMarkerFillStyle(am.getFillColor(), am.getFillOpacity());
	}

	public void setFillStyle(MCDynmapMarkerFillStyle style) {
		am.setFillStyle(style.getOpacity(), style.getIntColor());
	}

	public boolean isBoosted() {
		return am.getBoostFlag();
	}

	public void setBoosted(boolean isBoosted) {
		am.setBoostFlag(isBoosted);
	}
}