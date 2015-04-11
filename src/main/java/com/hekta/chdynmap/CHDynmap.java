package com.hekta.chdynmap;

import com.hekta.chdynmap.abstraction.CHDynmapStaticLayer;
import com.hekta.chdynmap.abstraction.MCDynmapAPI;
import com.hekta.chdynmap.abstraction.bukkit.events.drivers.BukkitDynmapListener;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;

/**
 *
 * @author Hekta
 */
@MSExtension("CHDynmap")
public final class CHDynmap extends AbstractExtension {

	public static final String DYNMAP_NAME = "Dynmap";

	private static final Version VERSION = new SimpleVersion(1, 1, 1);

	private static MCDynmapAPI _dynmap;

	@Override
	public Version getVersion() {
		return VERSION;
	}

	@Override
	public void onStartup() {
		_dynmap = CHDynmapStaticLayer.getDynmap();
		if (_dynmap != null) {
			BukkitDynmapListener.register();
			Static.getLogger().info(String.format("%s %s enabled.", getName(), VERSION));
		} else {
			Static.getLogger().severe(String.format("Plugin %s seems to be missing, none of the %s functions will work.", DYNMAP_NAME, getName()));
		}
	}

	@Override
	public void onShutdown() {
		if (_dynmap != null) {
			BukkitDynmapListener.unregister();
			Static.getLogger().info(String.format("%s unloaded.", getName()));
		}
	}

	public static MCDynmapAPI getDynmapAPI() {
		return _dynmap;
	}
}