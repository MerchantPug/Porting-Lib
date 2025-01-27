package io.github.fabricators_of_create.porting_lib.extensions.extensions;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public interface DimensionSpecialEffectsExtensions {
	/**
	 * Renders the clouds of this dimension.
	 *
	 * @return true to prevent vanilla cloud rendering
	 */
	default boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f modelViewMatrix, Matrix4f projectionMatrix) {
		return false;
	}

	/**
	 * Renders the sky of this dimension.
	 *
	 * @return true to prevent vanilla sky rendering
	 */
	default boolean renderSky(ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
		return false;
	}

	/**
	 * Renders the snow and rain effects of this dimension.
	 *
	 * @return true to prevent vanilla snow and rain rendering
	 */
	default boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
		return false;
	}

	/**
	 * Ticks the rain of this dimension.
	 *
	 * @return true to prevent vanilla rain ticking
	 */
	default boolean tickRain(ClientLevel level, int ticks, Camera camera) {
		return false;
	}

	/**
	 * Allows for manipulating the coloring of the lightmap texture.
	 * Will be called for each 16*16 combination of sky/block light values.
	 *
	 * @param level        The current level (client-side).
	 * @param partialTicks Progress between ticks.
	 * @param skyDarken    Current darkness of the sky.
	 * @param skyLight     Sky light brightness factor.
	 * @param blockLight   Block light brightness factor.
	 * @param pixelX       X-coordinate of the lightmap texture.
	 * @param pixelY       Y-coordinate of the lightmap texture.
	 * @param colors       The color values that will be used: [r, g, b].
	 * @see LightTexture#updateLightTexture(float)
	 */
	default void adjustLightmapColors(ClientLevel level, float partialTicks, float skyDarken, float skyLight, float blockLight, int pixelX, int pixelY, Vector3f colors) {}
}
