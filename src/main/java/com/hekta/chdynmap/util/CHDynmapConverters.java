package com.hekta.chdynmap.util;

import java.awt.Color;

import com.laytonsmith.abstraction.Convertor;
import com.laytonsmith.abstraction.MCColor;
import com.laytonsmith.abstraction.StaticLayer;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;

/*
 *
 * @author Hekta
 */
public class CHDynmapConverters {

	private static Convertor convertor = StaticLayer.GetConvertor();
	private static ObjectGenerator generator = ObjectGenerator.GetGenerator();

	public static CArray getColorArray(int RGB, Target t) {
		Color color = new Color(RGB);
		return generator.color(convertor.GetColor(color.getRed(), color.getGreen(), color.getBlue()), t);
	}

	public static int getColorInt(Construct colorArray, Target t) {
		MCColor mccolor = generator.color(Static.getArray(colorArray, t), t);
		return (new Color(mccolor.getRed(), mccolor.getGreen(), mccolor.getBlue()).getRGB()) + 16777216;
	}

	public static CArray getLocationArray(double x, double y, double z, String worldName) {
		CArray location = generator.location(convertor.GetLocation(Static.getServer().getWorld(worldName), x, y, z, 0, 0));
		location.remove(new CString("4", Target.UNKNOWN));
		location.remove(new CString("5", Target.UNKNOWN));
		location.remove(new CString("yaw", Target.UNKNOWN));
		location.remove(new CString("pitch", Target.UNKNOWN));
		return location;
	}
}
