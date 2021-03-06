package com.theonlytails.blockmodels

import net.minecraft.block.Block
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.generators.*
import net.minecraftforge.client.model.generators.ModelBuilder.Perspective
import net.minecraftforge.common.data.ExistingFileHelper
import kotlin.annotation.AnnotationTarget.*

typealias BlockModel = ModelBuilder<BlockModelBuilder>
typealias Element = ModelBuilder<BlockModelBuilder>.ElementBuilder
typealias ElementRotation = ModelBuilder<BlockModelBuilder>.ElementBuilder.RotationBuilder
typealias ConfiguredBuilder = ConfiguredModel.Builder<*>
typealias DisplayBuilder = ModelBuilder<BlockModelBuilder>.TransformsBuilder

/**
 * An annotation that marks every member of this DSL.
 *
 * @author TheOnlyTails
 */
@DslMarker
@MustBeDocumented
@Target(FUNCTION, CLASS, CONSTRUCTOR)
annotation class BlockModels

/**
 * Holds a 3D point in space.
 *
 * @author TheOnlyTails
 */
@BlockModels
data class Point3D(val x: Float, val y: Float, val z: Float) {

	/**
	 * Creates a 3D point in space from a single value, using it for all 3 coordinates.
	 *
	 * @author TheOnlyTails
	 */
	@BlockModels
	constructor(value: Float) : this(value, value, value)
}

/**
 * Holds a display transform of a model.
 *
 * @property perspective the perspective of the user while looking at the model.
 * @property translation by how much to move the model.
 * @property rotation    by how much to rotate the model.
 * @property scale       by how much to scale the model.
 * @author TheOnlyTails
 */
@BlockModels
data class DisplayTransform(
	val perspective: Perspective,
	val translation: Point3D,
	val rotation: Point3D,
	val scale: Point3D,
)

/**
 * Creates an array of [ConfiguredModel] for use in variant builders.
 *
 * @param path the name of the generated file
 * @author TheOnlyTails
 */
@BlockModels
fun BlockStateProvider.model(path: String, body: BlockModel.() -> Unit): Array<ConfiguredModel> =
	modelBuilder(path, body).build()

/**
 * Creates an array of [ConfiguredModel] for use in variant builders.
 *
 * @param block the block the model will be applied to (and the name of the file)
 * @author TheOnlyTails
 */
@BlockModels
fun BlockStateProvider.model(block: Block, body: BlockModel.() -> Unit): Array<ConfiguredModel> =
	modelBuilder(block, body).build()

/**
 * Creates a [ConfiguredModel.Builder] for use in variant builders.
 *
 * @param path the name of the generated file
 * @author TheOnlyTails
 */
@BlockModels
fun BlockStateProvider.modelBuilder(path: String, body: BlockModel.() -> Unit): ConfiguredBuilder =
	ConfiguredModel.builder().modelFile(models().getBuilder(path).apply(body))

/**
 * Creates a [ConfiguredModel.Builder] for use in variant builders.
 *
 * @param block the block the model will be applied to (and the name of the file)
 * @author TheOnlyTails
 */
@BlockModels
fun BlockStateProvider.modelBuilder(block: Block, body: BlockModel.() -> Unit) =
	modelBuilder(path = block.registryName?.path ?: "", body = body)

/**
 * Sets the parent model.
 *
 * @author TheOnlyTails
 */
@BlockModels
fun BlockModel.parent(parent: BlockModel.() -> ModelFile): BlockModel = parent(parent())

/**
 * Sets the parent model.
 *
 * @author TheOnlyTails
 */
@BlockModels
fun BlockModel.parent(existingFileHelper: ExistingFileHelper, parent: BlockModel.() -> String) = parent {
	getExistingFile(ResourceLocation("minecraft", parent()), existingFileHelper)
}

/**
 * Toggles ambient occlusion.
 *
 * @author TheOnlyTails
 */
@BlockModels
fun BlockModel.ambientOcclusion(ambientOcclusion: Boolean): BlockModel = ao(ambientOcclusion)

/**
 * Adds a new transform setting for different perspectives.
 *
 * @author TheOnlyTails
 */
@BlockModels
fun BlockModel.display(transform: DisplayTransform): DisplayBuilder = transforms()
	.transform(transform.perspective)
	.translation(transform.translation.x, transform.translation.y, transform.translation.z)
	.rotation(transform.rotation.x, transform.rotation.y, transform.rotation.z)
	.scale(transform.scale.x, transform.scale.y, transform.scale.z)
	.end()

/**
 * Adds a new texture key.
 *
 * @author TheOnlyTails
 */
@BlockModels
fun BlockModel.texture(vararg textures: Pair<String, ResourceLocation>) = apply {
	textures.forEach { texture(it.first, it.second) }
}
