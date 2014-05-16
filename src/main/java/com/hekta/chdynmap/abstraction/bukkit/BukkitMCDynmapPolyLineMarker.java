package com.hekta.chdynmap.abstraction.bukkit;

import com.hekta.chdynmap.abstraction.MCDynmapPolyLineMarker;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerLineStyle;
import com.hekta.chdynmap.abstraction.enums.MCDynmapMarkerType;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCWorld;
import com.laytonsmith.abstraction.StaticLayer;
import com.laytonsmith.annotations.abstraction;
import java.util.List;
import org.dynmap.markers.PolyLineMarker;

/**
 *
 * @author Hekta
 */
@abstraction(type = Implementation.Type.BUKKIT)
public class BukkitMCDynmapPolyLineMarker extends BukkitMCDynmapMarker implements MCDynmapPolyLineMarker {

	private static final MCDynmapMarkerType TYPE = MCDynmapMarkerType.POLYLINE;

	private final PolyLineMarker _marker;

	public BukkitMCDynmapPolyLineMarker(PolyLineMarker marker) {
		super(marker);
		_marker = marker;
	}

	public BukkitMCDynmapPolyLineMarker(Object object) {
		this((PolyLineMarker) object);
	}

	@Override
	public PolyLineMarker getHandle() {
		return _marker;
	}

	@Override
	public MCDynmapMarkerType getType() {
		return TYPE;
	}

	@Override
	public int getCornerCount() {
		return _marker.getCornerCount();
	}

	@Override
	public MCLocation getCorner(int n) {
		return StaticLayer.GetLocation(StaticLayer.GetServer().getWorld(_marker.getWorld()), _marker.getCornerX(n), _marker.getCornerY(n), _marker.getCornerZ(n));
	}

	@Override
	public MCLocation[] getCorners() {
		int count = _marker.getCornerCount();
		MCLocation[] corners = new MCLocation[count];
		MCWorld world = StaticLayer.GetServer().getWorld(_marker.getWorld());
		for (int i = 0 ; i < count ; i++) {
			corners[i] = StaticLayer.GetLocation(world, _marker.getCornerX(i), _marker.getCornerY(i), _marker.getCornerZ(i));
		}
		return corners;
	}

	@Override
	public void setCorner(int n, MCLocation location) {
		_marker.setCornerLocation(n, location.getX(), location.getY(), location.getZ());
	}

	@Override
	public void setCorners(MCLocation[] locations) {
		double[] Xs = new double[locations.length];
		double[] Ys = new double[locations.length];
		double[] Zs = new double[locations.length];
		int i = 0;
		for (MCLocation location : locations) {
			Xs[i] = location.getX();
			Ys[i] = location.getY();
			Zs[i] = location.getZ();
			i++;
		}
		_marker.setCornerLocations(Xs, Ys, Zs);
	}

	@Override
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
		_marker.setCornerLocations(Xs, Ys, Zs);
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
}