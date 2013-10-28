package com.hekta.chdynmap.extension;

import com.laytonsmith.annotations.shutdown;
import com.laytonsmith.annotations.startup;

import com.hekta.chdynmap.abstraction.CHDynmapStaticLayer;
import com.hekta.chdynmap.abstraction.MCDynmapAPI;
import com.hekta.chdynmap.abstraction.MCDynmapMarkerAPI;

/**
 *
 * @author Hekta
 */
public class CHDynmapExtension {

	public static MCDynmapAPI dynmapAPI;

	@startup
	public static void onEnable() {
		dynmapAPI = CHDynmapStaticLayer.getDynmapAPI();
		if (dynmapAPI != null) {
			System.out.println("[CommandHelper] CHDynmap 1.0 loaded (for Dynmap 1.9).");
		} else {
			System.out.println("[CommandHelper] Plugin Dynmap is missing, none of the CHDynmap functions will work.");
		}
	}

	@shutdown
	public static void onDisable() {
		if (dynmapAPI != null) {
			System.out.println("[CommandHelper] CHDynmap unloaded.");
		}
	}
}