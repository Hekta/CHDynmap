package com.hekta.chdynmap.abstraction.enums;

import com.laytonsmith.annotations.MEnum;

/**
 *
 * @author Hekta
 */
@MEnum("IconSize")
public enum MCDynmapIconSize {
	MARKER_8x8("8x8"),
	MARKER_16x16("16x16"),
	MARKER_32x32("32x32");

	private final String size;

	MCDynmapIconSize(String size) {
		this.size = size;
	}

	public String getSize() {
		return this.size;
	}
}