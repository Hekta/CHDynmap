package com.hekta.chdynmap.abstraction.bukkit;

import com.hekta.chdynmap.abstraction.MCDynmapIcon;
import com.hekta.chdynmap.abstraction.MCDynmapIconMarker;
import com.hekta.chdynmap.abstraction.enums.MCDynmapMarkerType;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.StaticLayer;
import com.laytonsmith.annotations.abstraction;
import org.dynmap.markers.Marker;

/**
 *
 * @author Hekta
 */
@abstraction(type = Implementation.Type.BUKKIT)
public class BukkitMCDynmapIconMarker extends BukkitMCDynmapMarker implements MCDynmapIconMarker {

	private static final MCDynmapMarkerType TYPE = MCDynmapMarkerType.ICON;

	private final Marker _marker;

	public BukkitMCDynmapIconMarker(Marker marker) {
		super(marker);
		this._marker = marker;
	}

	public BukkitMCDynmapIconMarker(Object object) {
		this((Marker) object);
	}

	@Override
	public Marker getHandle() {
		return _marker;
	}

	@Override
	public MCDynmapMarkerType getType() {
		return TYPE;
	}

	@Override
	public MCLocation getLocation() {
		return StaticLayer.GetLocation(StaticLayer.GetServer().getWorld(_marker.getWorld()), _marker.getX(), _marker.getY(), _marker.getZ());
	}

	@Override
	public void setLocation(MCLocation location) {
		_marker.setLocation(location.getWorld().getName(), location.getX(), location.getY(), location.getZ());
	}

	@Override
	public MCDynmapIcon getIcon() {
		return new BukkitMCDynmapIcon(_marker.getMarkerIcon());
	}

	@Override
	public boolean setIcon(MCDynmapIcon icon) {
		return _marker.setMarkerIcon(((BukkitMCDynmapIcon) icon).getHandle());
	}
}