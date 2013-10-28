package com.hekta.chdynmap.annotations;

import com.laytonsmith.abstraction.Implementation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Hekta
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CHDynmapConvert {
	Implementation.Type type();
}