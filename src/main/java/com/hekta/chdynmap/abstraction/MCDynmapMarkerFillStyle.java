package com.hekta.chdynmap.abstraction;

import com.laytonsmith.abstraction.MCColor;

/**
 *
 * @author Hekta
 */
public interface MCDynmapMarkerFillStyle {

	public MCColor getColor();
	public int getIntColor();
	public void setColor(MCColor color);
	public void setColor(int color);

	public double getOpacity();
	public void setOpacity(double opacity);
}