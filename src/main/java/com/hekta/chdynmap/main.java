package com.hekta.chdynmap;

import com.laytonsmith.annotations.shutdown;
import com.laytonsmith.annotations.startup;
import com.laytonsmith.core.Static;

/*
 *
 * @author Hekta
 */
public class main {
		
	@startup
	public static void onEnable(){
		if (Static.getServer().getPluginManager().getPlugin("dynmap") == null) {
			System.out.println("[CommandHelper] Plugin Dynmap is missing, none of the CHDynmap functions will work.");
		} else {
			System.out.println("[CommandHelper] CHDynmap 1.0 loaded (for Dynmap 1.9).");
		}
	}

	@shutdown
	public static void onDisable(){
		if (Static.getServer().getPluginManager().getPlugin("dynmap") != null) {
			System.out.println("[CommandHelper] CHDynmap unloaded.");
		}
	}
}
