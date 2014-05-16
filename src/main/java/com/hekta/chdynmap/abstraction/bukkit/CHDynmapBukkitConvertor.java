package com.hekta.chdynmap.abstraction.bukkit;

import com.hekta.chdynmap.CHDynmap;
import com.hekta.chdynmap.abstraction.CHDynmapConvertor;
import com.hekta.chdynmap.abstraction.MCDynmapAPI;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerFillStyle;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerLineStyle;
import com.hekta.chdynmap.annotations.CHDynmapConvert;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCColor;
import com.laytonsmith.abstraction.MCPlugin;
import com.laytonsmith.commandhelper.CommandHelperPlugin;

/**
 *
 * @author Hekta
 */
@CHDynmapConvert(type = Implementation.Type.BUKKIT)
public class CHDynmapBukkitConvertor implements CHDynmapConvertor {

	@Override
	public MCDynmapAPI getDynmap() {
		MCPlugin plugin = CommandHelperPlugin.myServer.getPluginManager().getPlugin(CHDynmap.DYNMAP_NAME.toLowerCase());
		if (plugin != null) {
			return new BukkitMCDynmapAPI(plugin.getHandle());
		} else {
			return null;
		}
	}

	@Override
	public MCDynmapMarkerFillStyle getFillStyle(MCColor color, double opacity) {
		return new BukkitMCDynmapMarkerFillStyle(color, opacity);
	}

	@Override
	public MCDynmapMarkerFillStyle getFillStyle(int color, double opacity) {
		return new BukkitMCDynmapMarkerFillStyle(color, opacity);
	}

	@Override
	public MCDynmapMarkerLineStyle getLineStyle(MCColor color, double opacity, int weight) {
		return new BukkitMCDynmapMarkerLineStyle(color, opacity, weight);
	}

	@Override
	public MCDynmapMarkerLineStyle getLineStyle(int color, double opacity, int weight) {
		return new BukkitMCDynmapMarkerLineStyle(color, opacity, weight);
	}
}