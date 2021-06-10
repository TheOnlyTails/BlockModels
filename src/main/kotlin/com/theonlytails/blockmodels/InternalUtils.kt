package com.theonlytails.blockmodels

import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile
import net.minecraftforge.common.data.ExistingFileHelper

fun extendWithFolder(rl: ResourceLocation) =
	if (rl.path.contains("/")) rl else ResourceLocation(rl.namespace, "block/" + rl.path)

fun getExistingFile(path: ResourceLocation, existingFileHelper: ExistingFileHelper) =
	ExistingModelFile(extendWithFolder(path), existingFileHelper).apply { assertExistence() }