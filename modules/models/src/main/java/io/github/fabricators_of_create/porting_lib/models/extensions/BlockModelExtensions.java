package io.github.fabricators_of_create.porting_lib.models.extensions;

import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;

import org.jetbrains.annotations.ApiStatus;

import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;

@ApiStatus.Internal
public interface BlockModelExtensions {
	void port_lib$setRenderMaterial(RenderMaterial material);
	void port_lib$setBlendMode(BlendMode blendMode);
}
