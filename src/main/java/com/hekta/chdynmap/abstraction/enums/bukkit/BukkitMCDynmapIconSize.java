package com.hekta.chdynmap.abstraction.enums.bukkit;

import org.dynmap.markers.MarkerIcon.MarkerSize;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.enums.EnumConvertor;
import com.laytonsmith.annotations.abstractionenum;

import com.hekta.chdynmap.abstraction.enums.MCDynmapIconSize;

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

	private static com.hekta.chdynmap.abstraction.enums.bukkit.BukkitMCDynmapIconSize instance;

	public static com.hekta.chdynmap.abstraction.enums.bukkit.BukkitMCDynmapIconSize getConvertor() {
		if (instance == null) {
			instance = new com.hekta.chdynmap.abstraction.enums.bukkit.BukkitMCDynmapIconSize();
		}
		return instance;
	}
}