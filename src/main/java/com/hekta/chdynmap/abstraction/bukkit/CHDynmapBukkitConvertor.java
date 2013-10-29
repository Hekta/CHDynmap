package com.hekta.chdynmap.abstraction.bukkit;

import org.bukkit.plugin.Plugin;

import org.dynmap.DynmapAPI;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCColor;
import com.laytonsmith.abstraction.bukkit.BukkitMCPlugin;

import com.hekta.chdynmap.abstraction.MCDynmapAPI;
import com.hekta.chdynmap.abstraction.CHDynmapConvertor;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerFillStyle;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerLineStyle;
import com.hekta.chdynmap.annotations.CHDynmapConvert;

/**
 *
 * @author Hekta
 */
@CHDynmapConvert(type=Implementation.Type.BUKKIT)
public class CHDynmapBukkitConvertor implements CHDynmapConvertor {

	public MCDynmapAPI getDynmapAPI() {
		Plugin plugin = ((BukkitMCPlugin) com.laytonsmith.commandhelper.CommandHelperPlugin.myServer.getPluginManager().getPlugin("dynmap")).getPlugin();
		if (plugin != null) {
			return new BukkitMCDynmapAPI((DynmapAPI) plugin);
		} else {
			return null;
		}
	}

	public MCDynmapMarkerFillStyle getFillStyle(MCColor color, double opacity) {
		return new BukkitMCDynmapMarkerFillStyle(color, opacity);
	}

	public MCDynmapMarkerFillStyle getFillStyle(int color, double opacity) {
		return new BukkitMCDynmapMarkerFillStyle(color, opacity);
	}

	public MCDynmapMarkerLineStyle getLineStyle(MCColor color, double opacity, int weight) {
		return new BukkitMCDynmapMarkerLineStyle(color, opacity, weight);
	}

	public MCDynmapMarkerLineStyle getLineStyle(int color, double opacity, int weight) {
		return new BukkitMCDynmapMarkerLineStyle(color, opacity, weight);
	}
}