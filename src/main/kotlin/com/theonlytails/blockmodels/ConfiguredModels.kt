package com.theonlytails.blockmodels

import net.minecraft.util.Direction
import net.minecraftforge.client.model.generators.ConfiguredModel

@BlockModels
fun ConfiguredBuilder.rotationY(
	direction: Direction,
	north: Int,
	east: Int,
	south: Int,
	west: Int
): ConfiguredBuilder = rotationY(
	when (direction) {
		Direction.NORTH -> north
		Direction.EAST -> east
		Direction.SOUTH -> south
		else -> west
	}
)

@BlockModels
fun ConfiguredBuilder.rotationX(
	direction: Direction,
	north: Int,
	east: Int,
	south: Int,
	west: Int
): ConfiguredBuilder = rotationX(
	when (direction) {
		Direction.NORTH -> north
		Direction.EAST -> east
		Direction.SOUTH -> south
		else -> west
	}
)

operator fun ConfiguredBuilder.invoke(body: ConfiguredBuilder.() -> ConfiguredBuilder): Array<ConfiguredModel> =
	this.body().build()