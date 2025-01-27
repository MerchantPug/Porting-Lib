package io.github.fabricators_of_create.porting_lib.models.pipeline;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;

import io.github.fabricators_of_create.porting_lib.models.IQuadTransformer;
import io.github.fabricators_of_create.porting_lib.textures.UnitTextureAtlasSprite;
import net.minecraft.Util;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;

/**
 * Vertex consumer that outputs {@linkplain BakedQuad baked quads}.
 * <p>
 * This consumer accepts data in {@link com.mojang.blaze3d.vertex.DefaultVertexFormat#BLOCK} and is not picky about
 * ordering or missing elements, but will not automatically populate missing data (color will be black, for example).
 * <p>
 * Built quads must be retrieved after building four vertices
 * <p>
 * Usage of this class on Fabric is highly discouraged, {@link net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter} should be used instead whenever possible.
 */
public class QuadBakingVertexConsumer implements VertexConsumer {
	private final Map<VertexFormatElement, Integer> ELEMENT_OFFSETS = Util.make(new IdentityHashMap<>(), map -> {
		for (var element : DefaultVertexFormat.BLOCK.getElements())
			map.put(element, DefaultVertexFormat.BLOCK.getOffset(element) / 4); // Int offset
	});
	private static final int QUAD_DATA_SIZE = IQuadTransformer.STRIDE * 4;

	private final int[] quadData = new int[QUAD_DATA_SIZE];
	private int vertexIndex = 0;
	private boolean building = false;

	private int tintIndex = -1;
	private Direction direction = Direction.DOWN;
	private TextureAtlasSprite sprite = UnitTextureAtlasSprite.INSTANCE;
	private boolean shade;
	private int lightEmission;
	private boolean hasAmbientOcclusion;

	@Override
	public VertexConsumer addVertex(float x, float y, float z) {
		if (building) {
			if (++vertexIndex > 4) {
				throw new IllegalStateException("Expected quad export after fourth vertex");
			}
		}
		building = true;

		int offset = vertexIndex * IQuadTransformer.STRIDE + IQuadTransformer.POSITION;
		quadData[offset] = Float.floatToRawIntBits(x);
		quadData[offset + 1] = Float.floatToRawIntBits(y);
		quadData[offset + 2] = Float.floatToRawIntBits(z);
		return this;
	}

	@Override
	public VertexConsumer setNormal(float x, float y, float z) {
		int offset = vertexIndex * IQuadTransformer.STRIDE + IQuadTransformer.NORMAL;
		quadData[offset] = ((int) (x * 127.0f) & 0xFF) |
				(((int) (y * 127.0f) & 0xFF) << 8) |
				(((int) (z * 127.0f) & 0xFF) << 16);
		return this;
	}

	@Override
	public VertexConsumer setColor(int r, int g, int b, int a) {
		int offset = vertexIndex * IQuadTransformer.STRIDE + IQuadTransformer.COLOR;
		quadData[offset] = ((a & 0xFF) << 24) |
				((b & 0xFF) << 16) |
				((g & 0xFF) << 8) |
				(r & 0xFF);
		return this;
	}

	@Override
	public VertexConsumer setUv(float u, float v) {
		int offset = vertexIndex * IQuadTransformer.STRIDE + IQuadTransformer.UV0;
		quadData[offset] = Float.floatToRawIntBits(u);
		quadData[offset + 1] = Float.floatToRawIntBits(v);
		return this;
	}

	@Override
	public VertexConsumer setUv1(int u, int v) {
		if (IQuadTransformer.UV1 >= 0) { // Vanilla doesn't support this, but it may be added by a 3rd party
			int offset = vertexIndex * IQuadTransformer.STRIDE + IQuadTransformer.UV1;
			quadData[offset] = (u & 0xFFFF) | ((v & 0xFFFF) << 16);
		}
		return this;
	}

	@Override
	public VertexConsumer setUv2(int u, int v) {
		int offset = vertexIndex * IQuadTransformer.STRIDE + IQuadTransformer.UV2;
		quadData[offset] = (u & 0xFFFF) | ((v & 0xFFFF) << 16);
		return this;
	}

//	@Override forge method
	public VertexConsumer misc(VertexFormatElement element, int... rawData) {
		Integer baseOffset = ELEMENT_OFFSETS.get(element);
		if (baseOffset != null) {
			int offset = vertexIndex * IQuadTransformer.STRIDE + baseOffset;
			System.arraycopy(rawData, 0, quadData, offset, rawData.length);
		}
		return this;
	}

	public void setTintIndex(int tintIndex) {
		this.tintIndex = tintIndex;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setSprite(TextureAtlasSprite sprite) {
		this.sprite = sprite;
	}

	public void setShade(boolean shade) {
		this.shade = shade;
	}

	public void setLightEmission(int lightEmission) {
		this.lightEmission = lightEmission;
	}

	public void setHasAmbientOcclusion(boolean hasAmbientOcclusion) {
		this.hasAmbientOcclusion = hasAmbientOcclusion;
	}

	public BakedQuad bakeQuad() {
		if (!building || ++vertexIndex != 4) {
			throw new IllegalStateException("Not enough vertices available. Vertices in buffer: " + vertexIndex);
		}

		BakedQuad quad = new BakedQuad(quadData.clone(), tintIndex, direction, sprite, shade/*, lightEmission, hasAmbientOcclusion*/);
		vertexIndex = 0;
		building = false;
		Arrays.fill(quadData, 0);
		return quad;
	}
}
