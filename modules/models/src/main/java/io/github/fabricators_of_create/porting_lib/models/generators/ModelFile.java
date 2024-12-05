package io.github.fabricators_of_create.porting_lib.models.generators;

import com.google.common.base.Preconditions;

import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.minecraft.resources.ResourceLocation;

public abstract class ModelFile {

	protected ResourceLocation location;

	protected ModelFile(ResourceLocation location) {
		this.location = location;
	}

	protected abstract boolean exists();

	public ResourceLocation getLocation() {
		assertExistence();
		return location;
	}

	/**
	 * Assert that this model exists.
	 * @throws IllegalStateException if this model does not exist
	 */
	public void assertExistence() {
		Preconditions.checkState(exists(), "Model at %s does not exist", location);
	}

	public ResourceLocation getUncheckedLocation() {
		return location;
	}

	public static class UncheckedModelFile extends ModelFile {

		public UncheckedModelFile(String location) {
			this(ResourceLocation.parse(location));
		}
		public UncheckedModelFile(ResourceLocation location) {
			super(location);
		}

		@Override
		protected boolean exists() {
			return true;
		}
	}

	public static class ExistingModelFile extends ModelFile {
		private final ExistingFileHelper existingHelper;

		public ExistingModelFile(ResourceLocation location, ExistingFileHelper existingHelper) {
			super(location);
			this.existingHelper = existingHelper;
		}

		@Override
		protected boolean exists() {
			if (getUncheckedLocation().getPath().contains("."))
				return existingHelper.exists(getUncheckedLocation(), ModelProvider.MODEL_WITH_EXTENSION);
			else
				return existingHelper.exists(getUncheckedLocation(), ModelProvider.MODEL);
		}
	}
}