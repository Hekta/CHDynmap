package com.hekta.chdynmap.abstraction.bukkit;

import java.awt.Color;

import com.laytonsmith.abstraction.MCColor;
import com.laytonsmith.abstraction.StaticLayer;

import com.hekta.chdynmap.abstraction.MCDynmapMarkerLineStyle;

/**
 *
 * @author Hekta
 */
public class BukkitMCDynmapMarkerLineStyle implements MCDynmapMarkerLineStyle {

	private MCColor color;
	private int intColor;
	private double opacity;
	private int weight;

	BukkitMCDynmapMarkerLineStyle(MCColor color, double opacity, int weight) {
		this.color = color;
		this.intColor = (new Color(color.getRed(), color.getGreen(), color.getBlue()).getRGB()) + 16777216;
		this.opacity = opacity;
		this.weight = weight;
	}

	BukkitMCDynmapMarkerLineStyle(int color, double opacity, int weight) {
		Color c = new Color(color);
		this.color = StaticLayer.GetConvertor().GetColor(c.getRed(), c.getGreen(), c.getBlue());
		this.intColor = color;
		this.opacity = opacity;
		this.weight = weight;
	}

	public MCColor getColor() {
		return this.color;
	}

	public int getIntColor() {
		return this.intColor;
	}

	public void setColor(MCColor color) {
		this.color = color;
		this.intColor = (new Color(color.getRed(), color.getGreen(), color.getBlue()).getRGB()) + 16777216;
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

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}