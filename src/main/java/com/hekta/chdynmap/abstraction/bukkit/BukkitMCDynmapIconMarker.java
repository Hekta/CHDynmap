package com.hekta.chdynmap.abstraction.bukkit;

import org.dynmap.markers.Marker;

import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.StaticLayer;

import com.hekta.chdynmap.abstraction.MCDynmapIcon;
import com.hekta.chdynmap.abstraction.MCDynmapIconMarker;

/**
 *
 * @author Hekta
 */
public class BukkitMCDynmapIconMarker extends BukkitMCDynmapMarker implements MCDynmapIconMarker {

	Marker im;

	public BukkitMCDynmapIconMarker(Marker marker) {
		super(marker);
		this.im = marker;
	}

	@Override
	public Marker getConcrete() {
		return im;
	}

	public MCLocation getLocation() {
		return StaticLayer.GetLocation(StaticLayer.GetServer().getWorld(m.getWorld()), im.getX(), im.getY(), im.getZ());
	}

	public void setLocation(MCLocation location) {
		im.setLocation(location.getWorld().getName(), location.getX(), location.getY(), location.getZ());
	}

	public MCDynmapIcon getIcon() {
		return new BukkitMCDynmapIcon(im.getMarkerIcon());
	}

	public boolean setIcon(MCDynmapIcon icon) {
		return im.setMarkerIcon(((BukkitMCDynmapIcon) icon).getConcrete());
	}
}