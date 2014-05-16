package com.hekta.chdynmap.abstraction;

import com.hekta.chdynmap.abstraction.enums.MCDynmapMarkerType;
import com.laytonsmith.abstraction.AbstractionObject;
import com.laytonsmith.abstraction.MCWorld;

/**
 *
 * @author Hekta
 */
public abstract interface MCDynmapMarker extends AbstractionObject {

	public MCDynmapMarkerType getType();

	public String getId();

	public MCDynmapMarkerSet getSet();
	public void setSet(MCDynmapMarkerSet newSet);

	public void delete();

	public MCWorld getWorld();
	public String getNormalizedWorld();

	public boolean isPersistent();

	public String getLabel();
	public void setLabel(String label);
	public void setLabel(String label, boolean isHTMLMarkup);
	public boolean isLabelMarkup();

	public String getDescription();
	public void setDescription(String description);

	public int getMinZoom();
	public void setMinZoom(int zoom);
	public int getMaxZoom();
	public void setMaxZoom(int zoom);
}