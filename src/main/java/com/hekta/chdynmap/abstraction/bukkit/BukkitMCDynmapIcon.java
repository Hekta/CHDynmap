package com.hekta.chdynmap.abstraction.bukkit;

import com.hekta.chdynmap.abstraction.enums.bukkit.BukkitMCDynmapIconSize;
import java.io.InputStream;

import org.dynmap.markers.MarkerIcon;

import com.hekta.chdynmap.abstraction.MCDynmapIcon;
import com.hekta.chdynmap.abstraction.enums.MCDynmapIconSize;

/**
 *
 * @author Hekta
 */
public class BukkitMCDynmapIcon implements MCDynmapIcon {

	MarkerIcon icon;

	public BukkitMCDynmapIcon(MarkerIcon icon) {
		this.icon = icon;
	}

	public MarkerIcon getConcrete() {
		return icon;
	}

	public String getId() {
		return icon.getMarkerIconID();
	}

	public String getLabel() {
		return icon.getMarkerIconLabel();
	}

	public void setLabel(String label) {
		icon.setMarkerIconLabel(label);
	}

	public void setImage(InputStream image) {
		icon.setMarkerIconImage(image);
	}

	public void delete() {
		icon.deleteIcon();
	}

	public boolean isBuiltIn() {
		return icon.isBuiltIn();
	}

	public MCDynmapIconSize getSize() {
		return BukkitMCDynmapIconSize.getConvertor().getAbstractedEnum(icon.getMarkerIconSize());
	}
}