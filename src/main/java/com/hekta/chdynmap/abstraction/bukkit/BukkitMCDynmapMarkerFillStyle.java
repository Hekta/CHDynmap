package com.hekta.chdynmap.abstraction.bukkit;

import com.hekta.chdynmap.abstraction.MCDynmapMarkerFillStyle;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCColor;
import com.laytonsmith.abstraction.StaticLayer;
import com.laytonsmith.annotations.abstraction;
import java.awt.Color;

/**
 *
 * @author Hekta
 */
@abstraction(type = Implementation.Type.BUKKIT)
public class BukkitMCDynmapMarkerFillStyle implements MCDynmapMarkerFillStyle {

	private MCColor _color;
	private int _intColor;
	private double _opacity;

	BukkitMCDynmapMarkerFillStyle(MCColor color, double opacity) {
		_color = color;
		_intColor = (new Color(color.getRed(), color.getGreen(), color.getBlue()).getRGB()) + 16777216;
		_opacity = opacity;
	}

	BukkitMCDynmapMarkerFillStyle(int color, double opacity) {
		Color c = new Color(color);
		_color = StaticLayer.GetConvertor().GetColor(c.getRed(), c.getGreen(), c.getBlue());
		_intColor = color;
		_opacity = opacity;
	}

	@Override
	public MCColor getColor() {
		return _color;
	}

	@Override
	public int getIntColor() {
		return _intColor;
	}

	@Override
	public void setColor(MCColor color) {
		_color = color;
		_intColor = (new Color(color.getRed(), color.getGreen(), color.getBlue()).getRGB()) + 16777216;
	}

	@Override
	public void setColor(int color) {
		Color c = new Color(color);
		_color = StaticLayer.GetConvertor().GetColor(c.getRed(), c.getGreen(), c.getBlue());
		_intColor = color;
	}

	@Override
	public double getOpacity() {
		return _opacity;
	}

	@Override
	public void setOpacity(double opacity) {
		_opacity = opacity;
	}
}