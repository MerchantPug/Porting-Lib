package io.github.fabricators_of_create.porting_lib.event.common;

import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import io.github.fabricators_of_create.porting_lib.core.event.CancellableEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.item.ItemStack;

public abstract class GrindstoneEvent extends BaseEvent {
	private final ItemStack top;
	private final ItemStack bottom;
	private int xp;

	protected GrindstoneEvent(ItemStack top, ItemStack bottom, int xp) {
		this.top = top;
		this.bottom = bottom;
		this.xp = xp;
	}

	/**
	 * @return The item in the top input grindstone slot. <br>
	 */
	public ItemStack getTopItem() {
		return top;
	}

	/**
	 * @return The item in the bottom input grindstone slot. <br>
	 */
	public ItemStack getBottomItem() {
		return bottom;
	}

	/**
	 * This is the experience amount determined by the event. It will be {@code -1} unless {@link #setXp(int)} is called. <br>
	 *
	 * @return The experience amount given to the player. <br>
	 */
	public int getXp() {
		return xp;
	}

	/**
	 * Sets the experience amount. <br>
	 *
	 * @param xp The experience amount given to the player. <br>
	 */
	public void setXp(int xp) {
		this.xp = xp;
	}

	/**
	 * This event is {@link CancellableEvent} <br>
	 * {@link OnPlaceItem} is fired when the inputs to a grindstone are changed. <br>
	 * <p>
	 * The following rules apply:
	 * <ul>
	 * <li>If the event is canceled, vanilla behavior will not run, and the output will be {@linkplain ItemStack#EMPTY empty}.</li>
	 * <li>If the event is not canceled</li>
	 * <ul>
	 * <li>and the output is empty, the output will be determined by vanilla.</li>
	 * <li>and the output is not empty, the output will be set, without running vanilla behavior.</li>
	 * </ul>
	 * <li>Vanilla XP calculation logic will be used unless all of the following criterias are met:</li>
	 * <ul>
	 * <li>the amount of experience is greater than or equal to {@code 0};</li>
	 * <li>the event is not {@linkplain #isCanceled() canceled};</li>
	 * <li>the {@linkplain #getOutput() output} is not empty.</li>
	 * </ul>
	 * </ul>
	 */
	public static class OnPlaceItem extends GrindstoneEvent implements CancellableEvent {
		public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
			for (Callback callback : callbacks)
				callback.onGrindstonePlaceItem(event);
		});

		private ItemStack output;

		public OnPlaceItem(ItemStack top, ItemStack bottom, int xp) {
			super(top, bottom, xp);
			this.output = ItemStack.EMPTY;
		}

		/**
		 * This is the output as determined by the event, not by the vanilla behavior between these two items. <br>
		 * If you are the first receiver of this event, it is guaranteed to be empty. <br>
		 * It will only be non-empty if changed by an event handler. <br>
		 * If this event is cancelled, this output stack is discarded. <br>
		 *
		 * @return The item to set in the output grindstone slot. <br>
		 */
		public ItemStack getOutput() {
			return output;
		}

		/**
		 * Sets the output slot to a specific itemstack.
		 *
		 * @param output The stack to change the output to.
		 */
		public void setOutput(ItemStack output) {
			this.output = output;
		}

		@Override
		public void sendEvent() {
			EVENT.invoker().onGrindstonePlaceItem(this);
		}

		public interface Callback {
			void onGrindstonePlaceItem(OnPlaceItem event);
		}
	}

	/**
	 * This event is {@link CancellableEvent} <br>
	 * {@link OnTakeItem} is fired when the output in a grindstone are is taken. <br>
	 * It is called from {@link GrindstoneMenu#GrindstoneMenu(int, Inventory)}. <br>
	 * If the event is canceled, vanilla behavior will not run, and no inputs will be consumed. <br>
	 * if the amount of experience is larger than or equal 0, the vanilla behavior for calculating experience will not run. <br>
	 */
	public static class OnTakeItem extends GrindstoneEvent implements CancellableEvent {
		public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
			for (Callback callback : callbacks)
				callback.onGrindstoneTakeItem(event);
		});

		private ItemStack newTop = ItemStack.EMPTY;
		private ItemStack newBottom = ItemStack.EMPTY;

		public OnTakeItem(ItemStack top, ItemStack bottom, int xp) {
			super(top, bottom, xp);
		}

		/**
		 * @return The item in that will be in the top input grindstone slot after the event. <br>
		 */
		public ItemStack getNewTopItem() {
			return newTop;
		}

		/**
		 * @return The item in that will be in the bottom input grindstone slot after the event. <br>
		 */
		public ItemStack getNewBottomItem() {
			return newBottom;
		}

		/**
		 * Sets the itemstack in the top slot. <br>
		 *
		 * @param newTop
		 */
		public void setNewTopItem(ItemStack newTop) {
			this.newTop = newTop;
		}

		/**
		 * Sets the itemstack in the bottom slot. <br>
		 *
		 * @param newBottom
		 */
		public void setNewBottomItem(ItemStack newBottom) {
			this.newBottom = newBottom;
		}

		/**
		 * This is the experience amount that will be returned by the event. <br>
		 *
		 * @return The experience amount given to the player. <br>
		 */
		public int getXp() {
			return super.getXp();
		}

		@Override
		public void sendEvent() {
			EVENT.invoker().onGrindstoneTakeItem(this);
		}

		public interface Callback {
			void onGrindstoneTakeItem(OnTakeItem event);
		}
	}
}
