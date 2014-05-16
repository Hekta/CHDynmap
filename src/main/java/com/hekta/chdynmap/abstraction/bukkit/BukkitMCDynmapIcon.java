package com.hekta.chdynmap.abstraction.bukkit;

import com.hekta.chdynmap.abstraction.MCDynmapIcon;
import com.hekta.chdynmap.abstraction.enums.MCDynmapIconSize;
import com.hekta.chdynmap.abstraction.enums.bukkit.BukkitMCDynmapIconSize;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.annotations.abstraction;
import java.io.InputStream;
import org.dynmap.markers.MarkerIcon;

/**
 *
 * @author Hekta
 */
@abstraction(type = Implementation.Type.BUKKIT)
public class BukkitMCDynmapIcon implements MCDynmapIcon {

	private final MarkerIcon _icon;

	public BukkitMCDynmapIcon(MarkerIcon icon) {
		_icon = icon;
	}

	public BukkitMCDynmapIcon(Object object) {
		this((MarkerIcon) object);
	}

	@Override
	public MarkerIcon getHandle() {
		return _icon;
	}

	@Override
	public String getId() {
		return _icon.getMarkerIconID();
	}

	@Override
	public String getLabel() {
		return _icon.getMarkerIconLabel();
	}

	@Override
	public void setLabel(String label) {
		_icon.setMarkerIconLabel(label);
	}

	@Override
	public void setImage(InputStream image) {
		_icon.setMarkerIconImage(image);
	}

	@Override
	public void delete() {
		_icon.deleteIcon();
	}

	@Override
	public boolean isBuiltIn() {
		return _icon.isBuiltIn();
	}

	@Override
	public MCDynmapIconSize getSize() {
		return BukkitMCDynmapIconSize.getConvertor().getAbstractedEnum(_icon.getMarkerIconSize());
	}
}