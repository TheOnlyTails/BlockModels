package com.theonlytails.blockmodels

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.theonlytails.blockmodels.TestBlocks.blocks
import com.theonlytails.blockmodels.TestBlocks.exampleBlock
import mu.KotlinLogging
import net.minecraft.block.AbstractBlock.Properties.copy
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.data.DataGenerator
import net.minecraft.util.Direction.*
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.KDeferredRegister
import thedarkcolour.kotlinforforge.forge.MOD_BUS

const val MOD_ID = "blockmodels"

val gson: Gson = GsonBuilder()
	.disableHtmlEscaping()
	.setPrettyPrinting()
	.create()

val logger = KotlinLogging.logger { }

fun BlockStateProvider.testModel(name: String, model: BlockModel.() -> Unit) = model(name, model).apply {
	logger.info { gson.toJson(this) }
}

@Mod(MOD_ID)
class DataModelsTest {
	init {
		MOD_BUS.addListener(::gatherData)
		blocks.register(MOD_BUS)
	}
}

object TestBlocks {
	val blocks = KDeferredRegister(ForgeRegistries.BLOCKS, MOD_ID)
	val exampleBlock by blocks.registerObject("example") { Block(copy(Blocks.GOLD_BLOCK)) }
}

fun gatherData(event: GatherDataEvent) {
	if (event.includeClient()) event.generator.addProvider(
		DataModelGenerator(
			event.generator,
			event.existingFileHelper
		)
	)
}

class DataModelGenerator(generator: DataGenerator, helper: ExistingFileHelper) :
	BlockStateProvider(generator, MOD_ID, helper) {

	override fun registerStatesAndModels() {
		getVariantBuilder(exampleBlock).forAllStates {
			testModel("first") {
				ambientOcclusion(false)
				texture(
					"particle" to ResourceLocation("rubymod", "block/logic_gate"),
					"top" to ResourceLocation("rubymod", "block/logic_gate"),
					"unlit" to ResourceLocation("minecraft", "block/redstone_torch_off"),
					"slab" to ResourceLocation("minecraft", "block/smooth_stone")
				)
				element {
					start(0, 0, 0)
					finish(16, 2, 16)
					allFaces { dir, face ->
						face {
							when (dir) {
								NORTH -> {
									uvs(0f, 14f, 16f, 16f)
									texture("#slab")
									cullface(dir)
								}
								SOUTH -> {
									uvs(0f, 14f, 16f, 16f)
									texture("#slab")
									cullface(dir)
								}
								EAST -> {
									uvs(0f, 14f, 16f, 16f)
									texture("#slab")
									cullface(dir)
								}
								WEST -> {
									uvs(0f, 14f, 16f, 16f)
									texture("#slab")
									cullface(dir)
								}
								UP -> {
									uvs(0f, 0f, 16f, 16f)
									texture("#top")
								}
								else -> {
									uvs(0f, 0f, 16f, 16f)
									texture("#slab")
									cullface(dir)
								}
							}
						}
					}
				}
			}
		}
	}
}