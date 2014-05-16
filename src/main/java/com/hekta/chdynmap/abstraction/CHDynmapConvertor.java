package com.hekta.chdynmap.abstraction;

import com.laytonsmith.abstraction.MCColor;

/**
 *
 * @author Hekta
 */
public interface CHDynmapConvertor {

	public MCDynmapAPI getDynmap();

	public MCDynmapMarkerFillStyle getFillStyle(MCColor color, double opacity);

	public MCDynmapMarkerFillStyle getFillStyle(int color, double opacity);

	public MCDynmapMarkerLineStyle getLineStyle(MCColor color, double opacity, int weight);

	public MCDynmapMarkerLineStyle getLineStyle(int color, double opacity, int weight);
}