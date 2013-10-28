package com.hekta.chdynmap.abstraction;

import com.hekta.chdynmap.abstraction.enums.MCDynmapIconSize;
import java.io.InputStream;

/**
 *
 * @author Hekta
 */
public interface MCDynmapIcon {

	public String getId();

	public String getLabel();
	public void setLabel(String label);

	public void setImage(InputStream image);

	public void delete();

	public boolean isBuiltIn();

	public MCDynmapIconSize getSize();
}