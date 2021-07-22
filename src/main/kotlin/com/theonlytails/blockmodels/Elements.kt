package com.theonlytails.blockmodels

import net.minecraft.util.Direction

/**
 * Adds a new element to a model.
 *
 * @author TheOnlyTails
 */
@BlockModels
fun BlockModel.element(body: Element.() -> Element): BlockModel = this.element().body().end()

/**
 * Sets the origin position for the bounding box of the element.
 *
 * @author TheOnlyTails
 */
@BlockModels
fun Element.start(x: Float, y: Float, z: Float): Element = from(x, y, z)

/**
 * Sets the opposite position to the origin point for the bounding box of the element.
 *
 * @author TheOnlyTails
 */
@BlockModels
fun Element.finish(x: Float, y: Float, z: Float): Element = to(x, y, z)

/**
 * Sets the origin position for the bounding box of the element.
 *
 * @author TheOnlyTails
 */
@BlockModels
fun Element.start(x: Int, y: Int, z: Int): Element = from(x.toFloat(), y.toFloat(), z.toFloat())

/**
 * Sets the opposite position to the origin point for the bounding box of the element.
 *
 * @author TheOnlyTails
 */
@BlockModels
fun Element.finish(x: Int, y: Int, z: Int): Element = to(x.toFloat(), y.toFloat(), z.toFloat())

/**
 * Configures the rotation of the element.
 *
 * @author TheOnlyTails
 */
@BlockModels
fun Element.rotation(body: ElementRotation.() -> ElementRotation): Element = rotation().body().end()

/**
 * Configures the rotation of the element directly.
 *
 * @author TheOnlyTails
 */
@BlockModels
fun Element.rotation(angle: Float, axis: Direction.Axis, x: Float, y: Float, z: Float, rescale: Boolean = false) =
	rotation {
		angle(angle)
		axis(axis)
		origin(x, y, z)
		rescale(rescale)
	}