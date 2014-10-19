package com.hekta.chdynmap.abstraction;

import com.hekta.chdynmap.annotations.CHDynmapConvert;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCColor;
import com.laytonsmith.PureUtilities.ClassLoading.ClassDiscovery;

/**
 *
 * @author Hekta, based on layton's StaticLayer
 */
public final class CHDynmapStaticLayer {

	private CHDynmapStaticLayer(){}

	private static CHDynmapConvertor convertor = null;

	static {
		InitConvertor();
	}

	private static void InitConvertor() {
		for(Class<?> c : ClassDiscovery.getDefaultInstance().loadClassesWithAnnotation(CHDynmapConvert.class)) {
			if (CHDynmapConvertor.class.isAssignableFrom(c)) {
				CHDynmapConvert convert = (CHDynmapConvert) c.getAnnotation(CHDynmapConvert.class);
				if (convert.type() == Implementation.GetServerType()) {
					try {
						if (convertor == null) {
							convertor = (CHDynmapConvertor) c.newInstance();
						} else {
							System.err.println("[CommandHelper] [CHDynmap] More than one CHDynmapConvertor for this server type was detected!");
						}
					} catch (IllegalAccessException | InstantiationException exception) {
						System.err.println("[CommandHelper] [CHDynmap] Tried to instantiate the CHDynmapConvertor, but couldn't: " + exception.getMessage());
					}
				}
			} else {
				System.err.println("[CommandHelper] [CHDynmap] The CHCitizensConvertor " + c.getSimpleName() + " doesn't implement CHDynmapConvertor!");
			}
		}
	}

	public static MCDynmapAPI getDynmap() {
		return convertor.getDynmap();
	}

	public static MCDynmapMarkerFillStyle getFillStyle(MCColor color, double opacity) {
		return convertor.getFillStyle(color, opacity);
	}

	public static MCDynmapMarkerFillStyle getFillStyle(int color, double opacity) {
		return convertor.getFillStyle(color, opacity);
	}

	public static MCDynmapMarkerLineStyle getLineStyle(MCColor color, double opacity, int weight) {
		return convertor.getLineStyle(color, opacity, weight);
	}

	public static MCDynmapMarkerLineStyle getLineStyle(int color, double opacity, int weight) {
		return convertor.getLineStyle(color, opacity, weight);
	}
}