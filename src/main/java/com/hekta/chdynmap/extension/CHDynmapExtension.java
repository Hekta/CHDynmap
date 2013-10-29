package com.hekta.chdynmap.extension;

import com.laytonsmith.annotations.shutdown;
import com.laytonsmith.annotations.startup;

import com.hekta.chdynmap.abstraction.CHDynmapStaticLayer;
import com.hekta.chdynmap.abstraction.MCDynmapAPI;

/**
 *
 * @author Hekta
 */
public class CHDynmapExtension {

	public static MCDynmapAPI dynmapAPI;

	@startup
	public static void onEnable() {
		try {
			dynmapAPI = CHDynmapStaticLayer.getDynmapAPI();
			System.out.println("[CommandHelper] CHDynmap 1.0 loaded (for Dynmap 1.9).");
		} catch (Exception exception) {
			dynmapAPI = null;
			System.err.println("[CommandHelper] Plugin Dynmap seems to be missing, none of the CHDynmap functions will work.");
		}
	}

	@shutdown
	public static void onDisable() {
		if (dynmapAPI != null) {
			System.out.println("[CommandHelper] CHDynmap unloaded.");
		}
	}
}