package io.github.fabricators_of_create.porting_lib.models.generators;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.math.Transformation;

import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Builder for block models, does not currently provide any additional
 * functionality over {@link ModelBuilder}, purely a stub class with a concrete
 * generic.
 *
 * @see ModelProvider
 * @see ModelBuilder
 */
public class BlockModelBuilder extends ModelBuilder<BlockModelBuilder> {
	private final RootTransformBuilder rootTransform = new RootTransformBuilder();

	public BlockModelBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
		super(outputLocation, existingFileHelper);
	}

	public RootTransformBuilder rootTransform() {
		return rootTransform;
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = super.toJson();

		// If there were any transform properties set, add them to the output.
		JsonObject transform = rootTransform.toJson();
		if (transform.size() > 0) {
			json.add("transform", transform);
		}

		return json;
	}

	public class RootTransformBuilder {
		private static final Vector3f ONE = new Vector3f(1, 1, 1);

		private Vector3f translation = new Vector3f();
		private Quaternionf leftRotation = new Quaternionf();
		private Quaternionf rightRotation = new Quaternionf();
		private Vector3f scale = ONE;

		private @Nullable TransformOrigin origin;
		private @Nullable Vector3f originVec;

		private RootTransformBuilder() {}

		/**
		 * Sets the translation of the root transform.
		 *
		 * @param translation the translation
		 * @return this builder
		 * @throws NullPointerException if {@code translation} is {@code null}
		 */
		public RootTransformBuilder translation(Vector3f translation) {
			this.translation = Preconditions.checkNotNull(translation, "Translation must not be null");
			return this;
		}

		/**
		 * Sets the translation of the root transform.
		 *
		 * @param x x translation
		 * @param y y translation
		 * @param z z translation
		 * @return this builder
		 */
		public RootTransformBuilder translation(float x, float y, float z) {
			return translation(new Vector3f(x, y, z));
		}

		/**
		 * Sets the left rotation of the root transform.
		 *
		 * @param rotation the left rotation
		 * @return this builder
		 * @throws NullPointerException if {@code rotation} is {@code null}
		 */
		public RootTransformBuilder rotation(Quaternionf rotation) {
			this.leftRotation = Preconditions.checkNotNull(rotation, "Rotation must not be null");
			return this;
		}

		/**
		 * Sets the left rotation of the root transform.
		 *
		 * @param x x rotation
		 * @param y y rotation
		 * @param z z rotation
		 * @param isDegrees whether the rotation is in degrees or radians
		 * @return this builder
		 */
		public RootTransformBuilder rotation(float x, float y, float z, boolean isDegrees) {
			return rotation(quatFromXYZ(x, y, z, isDegrees));
		}

		/**
		 * Sets the left rotation of the root transform.
		 *
		 * @param leftRotation the left rotation
		 * @return this builder
		 * @throws NullPointerException if {@code leftRotation} is {@code null}
		 */
		public RootTransformBuilder leftRotation(Quaternionf leftRotation) {
			return rotation(leftRotation);
		}

		/**
		 * Sets the left rotation of the root transform.
		 *
		 * @param x x rotation
		 * @param y y rotation
		 * @param z z rotation
		 * @param isDegrees whether the rotation is in degrees or radians
		 * @return this builder
		 */
		public RootTransformBuilder leftRotation(float x, float y, float z, boolean isDegrees) {
			return leftRotation(quatFromXYZ(x, y, z, isDegrees));
		}

		/**
		 * Sets the right rotation of the root transform.
		 *
		 * @param rightRotation the right rotation
		 * @return this builder
		 * @throws NullPointerException if {@code rightRotation} is {@code null}
		 */
		public RootTransformBuilder rightRotation(Quaternionf rightRotation) {
			this.rightRotation = Preconditions.checkNotNull(rightRotation, "Rotation must not be null");
			return this;
		}

		/**
		 * Sets the right rotation of the root transform.
		 *
		 * @param x x rotation
		 * @param y y rotation
		 * @param z z rotation
		 * @param isDegrees whether the rotation is in degrees or radians
		 * @return this builder
		 */
		public RootTransformBuilder rightRotation(float x, float y, float z, boolean isDegrees) {
			return rightRotation(quatFromXYZ(x, y, z, isDegrees));
		}

		/**
		 * Sets the right rotation of the root transform.
		 *
		 * @param postRotation the right rotation
		 * @return this builder
		 * @throws NullPointerException if {@code rightRotation} is {@code null}
		 */
		public RootTransformBuilder postRotation(Quaternionf postRotation) {
			return rightRotation(postRotation);
		}

		/**
		 * Sets the right rotation of the root transform.
		 *
		 * @param x x rotation
		 * @param y y rotation
		 * @param z z rotation
		 * @param isDegrees whether the rotation is in degrees or radians
		 * @return this builder
		 */
		public RootTransformBuilder postRotation(float x, float y, float z, boolean isDegrees) {
			return postRotation(quatFromXYZ(x, y, z, isDegrees));
		}

		/**
		 * Sets the scale of the root transform.
		 *
		 * @param scale the scale
		 * @return this builder
		 * @throws NullPointerException if {@code scale} is {@code null}
		 */
		public RootTransformBuilder scale(float scale) {
			return scale(new Vector3f(scale, scale, scale));
		}

		/**
		 * Sets the scale of the root transform.
		 *
		 * @param xScale x scale
		 * @param yScale y scale
		 * @param zScale z scale
		 * @return this builder
		 */
		public RootTransformBuilder scale(float xScale, float yScale, float zScale) {
			return scale(new Vector3f(xScale, yScale, zScale));
		}

		/**
		 * Sets the scale of the root transform.
		 *
		 * @param scale the scale vector
		 * @return this builder
		 * @throws NullPointerException if {@code scale} is {@code null}
		 */
		public RootTransformBuilder scale(Vector3f scale) {
			this.scale = Preconditions.checkNotNull(scale, "Scale must not be null");
			return this;
		}

		/**
		 * Sets the root transform.
		 *
		 * @param transformation the transformation to use
		 * @return this builder
		 * @throws NullPointerException if {@code transformation} is {@code null}
		 */
		public RootTransformBuilder transform(Transformation transformation) {
			Preconditions.checkNotNull(transformation, "Transformation must not be null");
			this.translation = transformation.getTranslation();
			this.leftRotation = transformation.getLeftRotation();
			this.rightRotation = transformation.getRightRotation();
			this.scale = transformation.getScale();
			return this;
		}

		/**
		 * Sets the origin of the root transform.
		 *
		 * @param origin the origin vector
		 * @return this builder
		 * @throws NullPointerException if {@code origin} is {@code null}
		 */
		public RootTransformBuilder origin(Vector3f origin) {
			this.originVec = Preconditions.checkNotNull(origin, "Origin must not be null");
			this.origin = null;
			return this;
		}

		/**
		 * Sets the origin of the root transform.
		 *
		 * @param origin the origin name
		 * @return this builder
		 * @throws NullPointerException if {@code origin} is {@code null}
		 * @throws IllegalArgumentException if {@code origin} is not {@code center}, {@code corner} or {@code opposing-corner}
		 */
		public RootTransformBuilder origin(TransformOrigin origin) {
			this.origin = Preconditions.checkNotNull(origin, "Origin must not be null");
			this.originVec = null;
			return this;
		}

		/**
		 * Finish configuring the parent builder
		 * @return the parent block model builder
		 */
		public BlockModelBuilder end() {
			return BlockModelBuilder.this;
		}

		public JsonObject toJson() {
			// Write the transform to an object
			JsonObject transform = new JsonObject();

			if (!translation.equals(0, 0, 0)) {
				transform.add("translation", writeVec3(translation));
			}

			if (!scale.equals(ONE)) {
				transform.add("scale", writeVec3(scale));
			}

			if (!leftRotation.equals(0, 0, 0, 1)) {
				transform.add("rotation", writeQuaternion(leftRotation));
			}

			if (!rightRotation.equals(0, 0, 0, 1)) {
				transform.add("post_rotation", writeQuaternion(rightRotation));
			}

			if (origin != null) {
				transform.addProperty("origin", origin.getSerializedName());
			} else if (originVec != null && !originVec.equals(0, 0, 0)) {
				transform.add("origin", writeVec3(originVec));
			}

			return transform;
		}

		public enum TransformOrigin implements StringRepresentable {
			CENTER(new Vector3f(.5f, .5f, .5f), "center"),
			CORNER(new Vector3f(), "corner"),
			OPPOSING_CORNER(ONE, "opposing-corner");

			private final Vector3f vec;
			private final String name;

			TransformOrigin(Vector3f vec, String name) {
				this.vec = vec;
				this.name = name;
			}

			public Vector3f getVector() {
				return vec;
			}

			@Override
			@NotNull
			public String getSerializedName() {
				return name;
			}

			public static @Nullable TransformOrigin fromString(String originName) {
				if (CENTER.getSerializedName().equals(originName)) {
					return CENTER;
				}
				if (CORNER.getSerializedName().equals(originName)) {
					return CORNER;
				}
				if (OPPOSING_CORNER.getSerializedName().equals(originName)) {
					return OPPOSING_CORNER;
				}
				return null;
			}
		}

		private JsonArray writeVec3(Vector3f vector) {
			JsonArray array = new JsonArray();
			array.add(vector.x());
			array.add(vector.y());
			array.add(vector.z());
			return array;
		}

		private JsonArray writeQuaternion(Quaternionf quaternion) {
			JsonArray array = new JsonArray();
			array.add(quaternion.x());
			array.add(quaternion.y());
			array.add(quaternion.z());
			array.add(quaternion.w());
			return array;
		}
	}

	public static Quaternionf quatFromXYZ(float x, float y, float z, boolean degrees) {
		float conversionFactor = degrees ? (float) Math.PI / 180 : 1;
		return new Quaternionf().rotationXYZ(x * conversionFactor, y * conversionFactor, z * conversionFactor);
	}
}
