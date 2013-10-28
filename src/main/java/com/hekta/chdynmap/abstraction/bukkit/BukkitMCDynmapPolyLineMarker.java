package com.hekta.chdynmap.abstraction.bukkit;

import java.util.ArrayList;
import java.util.List;

import org.dynmap.markers.PolyLineMarker;

import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCWorld;
import com.laytonsmith.abstraction.StaticLayer;

import com.hekta.chdynmap.abstraction.MCDynmapPolyLineMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerLineStyle;

/**
 *
 * @author Hekta
 */
public class BukkitMCDynmapPolyLineMarker extends BukkitMCDynmapMarker implements MCDynmapPolyLineMarker {

	PolyLineMarker pm;

	public BukkitMCDynmapPolyLineMarker(PolyLineMarker marker) {
		super(marker);
		this.pm = marker;
	}

	@Override
	public PolyLineMarker getConcrete() {
		return pm;
	}

	public int getCornerCount() {
		return pm.getCornerCount();
	}

	public MCLocation getCorner(int n) {
		return StaticLayer.GetLocation(StaticLayer.GetServer().getWorld(pm.getWorld()), pm.getCornerX(n), pm.getCornerY(n), pm.getCornerZ(n));
	}

	public List<MCLocation> getCorners() {
		int count = pm.getCornerCount();
		List<MCLocation> corners = new ArrayList<MCLocation>();
		MCWorld world = StaticLayer.GetServer().getWorld(pm.getWorld());
		for (int i = 0 ; i < count ; i++) {
			corners.add(StaticLayer.GetLocation(world, pm.getCornerX(i), pm.getCornerY(i), pm.getCornerZ(i)));
		}
		return corners;
	}

	public void setCorner(int n, MCLocation location) {
		pm.setCornerLocation(n, location.getX(), location.getY(), location.getZ());
	}

	public void setCorners(List<MCLocation> locations) {
		int size = locations.size();
		double[] Xs = new double[size];
		double[] Ys = new double[size];
		double[] Zs = new double[size];
		int i = 0;
		for (MCLocation location : locations) {
			Xs[i] = location.getX();
			Ys[i] = location.getY();
			Zs[i] = location.getZ();
			i++;
		}
		pm.setCornerLocations(Xs, Ys, Zs);
	}

	public void deleteCorner(int n) {
		pm.deleteCorner(n);
	}

	public MCDynmapMarkerLineStyle getLineStyle() {
		return new BukkitMCDynmapMarkerLineStyle(pm.getLineColor(), pm.getLineOpacity(), pm.getLineWeight());
	}

	public void setLineStyle(MCDynmapMarkerLineStyle style) {
		pm.setLineStyle(style.getWeight(), style.getOpacity(), style.getIntColor());
	}
}