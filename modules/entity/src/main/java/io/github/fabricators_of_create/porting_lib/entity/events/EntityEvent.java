package io.github.fabricators_of_create.porting_lib.entity.events;

import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import io.github.fabricators_of_create.porting_lib.core.event.CancellableEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;

public abstract class EntityEvent extends BaseEvent {
	protected final Entity entity;

	public EntityEvent(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

	/**
	 * EntityConstructing is fired when an Entity is being created. <br>
	 * This event is fired within the constructor of the Entity.<br>
	 * <br>
	 * This event is not {@link CancellableEvent}.<br>
	 **/
	public static class EntityConstructing extends EntityEvent {
		public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
			for (final Callback callback : callbacks)
				callback.onConstructing(event);
		});

		public EntityConstructing(Entity entity) {
			super(entity);
		}

		@Override
		public void sendEvent() {
			EVENT.invoker().onConstructing(this);
		}

		public interface Callback {
			void onConstructing(EntityConstructing event);
		}
	}

	/**
	 * This event is fired on server and client after an Entity has entered a different section. <br>
	 * Sections are 16x16x16 block grids of the world.<br>
	 * This event does not fire when a new entity is spawned, only when an entity moves from one section to another one.
	 * Use {@link EntityJoinLevelEvent} to detect new entities joining the world.
	 * <br>
	 * This event is not {@link CancellableEvent}.<br>
	 **/
	public static class EnteringSection extends EntityEvent {
		public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
			for (final Callback callback : callbacks)
				callback.onEnteringSection(event);
		});

		private final long packedOldPos;
		private final long packedNewPos;

		public EnteringSection(Entity entity, long packedOldPos, long packedNewPos) {
			super(entity);
			this.packedOldPos = packedOldPos;
			this.packedNewPos = packedNewPos;
		}

		/**
		 * A packed version of the old section's position. This is to be used with the various methods in {@link SectionPos},
		 * such as {@link SectionPos#of(long)} or {@link SectionPos#x(long)} to avoid allocation.
		 *
		 * @return the packed position of the old section
		 */
		public long getPackedOldPos() {
			return packedOldPos;
		}

		/**
		 * A packed version of the new section's position. This is to be used with the various methods in {@link SectionPos},
		 * such as {@link SectionPos#of(long)} or {@link SectionPos#x(long)} to avoid allocation.
		 *
		 * @return the packed position of the new section
		 */
		public long getPackedNewPos() {
			return packedNewPos;
		}

		/**
		 * @return the position of the old section
		 */
		public SectionPos getOldPos() {
			return SectionPos.of(packedOldPos);
		}

		/**
		 * @return the position of the new section
		 */
		public SectionPos getNewPos() {
			return SectionPos.of(packedNewPos);
		}

		/**
		 * Whether the chunk has changed as part of this event. If this method returns false, only the Y position of the
		 * section has changed.
		 */
		public boolean didChunkChange() {
			return SectionPos.x(packedOldPos) != SectionPos.x(packedNewPos) || SectionPos.z(packedOldPos) != SectionPos.z(packedNewPos);
		}

		@Override
		public void sendEvent() {
			EVENT.invoker().onEnteringSection(this);
		}

		public interface Callback {
			void onEnteringSection(EnteringSection event);
		}
	}

	/**
	 * This event is fired whenever the {@link Pose} changes, and in a few other hardcoded scenarios.<br>
	 * CAREFUL: This is also fired in the Entity constructor. Therefore the entity(subclass) might not be fully initialized. Check Entity#isAddedToWorld() or !Entity#firstUpdate.<br>
	 * If you change the player's size, you probably want to set the eye height accordingly as well<br>
	 **/
	public static class Size extends EntityEvent {
		public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
			for (final Callback callback : callbacks)
				callback.onEntitySizeChange(event);
		});

		private final Pose pose;
		private final EntityDimensions oldSize;
		private EntityDimensions newSize;
		private final float oldEyeHeight;
		private float newEyeHeight;

		public Size(Entity entity, Pose pose, EntityDimensions size, float defaultEyeHeight) {
			this(entity, pose, size, size, defaultEyeHeight, defaultEyeHeight);
		}

		public Size(Entity entity, Pose pose, EntityDimensions oldSize, EntityDimensions newSize, float oldEyeHeight, float newEyeHeight) {
			super(entity);
			this.pose = pose;
			this.oldSize = oldSize;
			this.newSize = newSize;
			this.oldEyeHeight = oldEyeHeight;
			this.newEyeHeight = newEyeHeight;
		}

		public Pose getPose() {
			return pose;
		}

		public EntityDimensions getOldSize() {
			return oldSize;
		}

		public EntityDimensions getNewSize() {
			return newSize;
		}

		public void setNewSize(EntityDimensions size) {
			setNewSize(size, false);
		}

		/**
		 * Set the new size of the entity. Set updateEyeHeight to true to also update the eye height according to the new size.
		 */
		public void setNewSize(EntityDimensions size, boolean updateEyeHeight) {
			this.newSize = size;
			if (updateEyeHeight) {
				this.newEyeHeight = this.getEntity().getEyeHeight(this.getPose());
			}
		}

		public float getOldEyeHeight() {
			return oldEyeHeight;
		}

		public float getNewEyeHeight() {
			return newEyeHeight;
		}

		public void setNewEyeHeight(float newHeight) {
			this.newEyeHeight = newHeight;
		}

		@Override
		public void sendEvent() {
			EVENT.invoker().onEntitySizeChange(this);
		}

		public interface Callback {
			void onEntitySizeChange(Size event);
		}
	}
}
