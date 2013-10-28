package com.hekta.chdynmap.abstraction.bukkit;

import java.awt.Color;

import com.laytonsmith.abstraction.MCColor;
import com.laytonsmith.abstraction.StaticLayer;

import com.hekta.chdynmap.abstraction.MCDynmapMarkerFillStyle;

/**
 *
 * @author Hekta
 */
public class BukkitMCDynmapMarkerFillStyle implements MCDynmapMarkerFillStyle {

	private MCColor color;
	private int intColor;
	private double opacity;

	BukkitMCDynmapMarkerFillStyle(MCColor color, double opacity) {
		this.color = color;
		this.intColor = (new Color(color.getRed(), color.getGreen(), color.getBlue()).getRGB()) + 16777216;
		this.opacity = opacity;
	}

	BukkitMCDynmapMarkerFillStyle(int color, double opacity) {
		Color c = new Color(color);
		this.color = StaticLayer.GetConvertor().GetColor(c.getRed(), c.getGreen(), c.getBlue());
		this.intColor = color;
		this.opacity = opacity;
	}

	public MCColor getColor() {
		return this.color;
	}

	public int getIntColor() {
		return this.intColor;
	}

	public void setColor(MCColor color) {
		this.color = color;
	}

	public void setColor(int color) {
		Color c = new Color(color);
		this.color = StaticLayer.GetConvertor().GetColor(c.getRed(), c.getGreen(), c.getBlue());
		this.intColor = color;
	}

	public double getOpacity() {
		return this.opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}
}