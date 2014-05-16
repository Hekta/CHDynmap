package com.hekta.chdynmap.abstraction.enums.bukkit;

import com.hekta.chdynmap.abstraction.enums.MCDynmapIconSize;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.enums.EnumConvertor;
import com.laytonsmith.annotations.abstractionenum;
import org.dynmap.markers.MarkerIcon.MarkerSize;

/**
 *
 * @author Hekta
 */
@abstractionenum(
		implementation=Implementation.Type.BUKKIT,
		forAbstractEnum=MCDynmapIconSize.class,
		forConcreteEnum=MarkerSize.class
		)
public class BukkitMCDynmapIconSize extends EnumConvertor<MCDynmapIconSize, MarkerSize> {

	private static BukkitMCDynmapIconSize instance;

	public static BukkitMCDynmapIconSize getConvertor() {
		if (instance == null) {
			instance = new com.hekta.chdynmap.abstraction.enums.bukkit.BukkitMCDynmapIconSize();
		}
		return instance;
	}
}