package io.github.fabricators_of_create.porting_lib.render_types;

import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.minecraft.client.renderer.RenderType;

/**
 * A set of functionally equivalent shaders. One using {@link com.mojang.blaze3d.vertex.DefaultVertexFormat#BLOCK},
 * and the other two using {@link com.mojang.blaze3d.vertex.DefaultVertexFormat#NEW_ENTITY}.
 * {@code entityFabulous} may support custom render targets and other aspects of the fabulous pipeline, or can otherwise
 * be the same as {@code entity}.
 */
public record RenderTypeGroup(RenderType block, RenderType entity, RenderType entityFabulous, RenderMaterial material) {
	public RenderTypeGroup {
		if ((block == null) != (entity == null) || (block == null) != (entityFabulous == null))
			throw new IllegalArgumentException("The render types in a group must either be all null, or all non-null.");
	}

	public RenderTypeGroup(RenderType block, RenderType entity, RenderType entityFabulous) {
		this(block, entity, entityFabulous, RendererAccess.INSTANCE.getRenderer().materialFinder().blendMode(BlendMode.fromRenderLayer(block)).find());
	}

	public RenderTypeGroup(RenderType block, RenderType entity) {
		this(block, entity, entity);
	}

	public static RenderTypeGroup EMPTY = new RenderTypeGroup(null, null, null);

	/**
	 * {@return true if this group has render types or not. It either has all, or none}
	 */
	public boolean isEmpty() {
		// We throw an exception in the constructor if nullability doesn't match, so checking this is enough
		return block == null;
	}
}
