package com.theonlytails.blockmodels

import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.ModelBuilder

typealias ElementFace = ModelBuilder<BlockModelBuilder>.ElementBuilder.FaceBuilder

operator fun ElementFace.invoke(body: ElementFace.() -> ElementFace): Element = this.body().end()
