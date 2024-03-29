package io.github.fabricators_of_create.porting_lib.entity.mixin.common;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalLongRef;

import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntitySection;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(targets = "net/minecraft/world/level/entity/PersistentEntitySectionManager$Callback")
public abstract class PersistentEntitySectionManager$CallbackMixin<T extends EntityAccess> {
	@Shadow
	private long currentSectionKey;
	@Shadow
	@Final
	private T entity;

	@Inject(
			method = "onMove",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/entity/EntitySection;add(Lnet/minecraft/world/level/entity/EntityAccess;)V",
					shift = At.Shift.AFTER
			)
	)
	public void grabOldKey(CallbackInfo ci, @Share("oldKey") LocalLongRef oldKey) {
		oldKey.set(this.currentSectionKey);
	}

	@Inject(
			method = "onMove",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/entity/PersistentEntitySectionManager$Callback;updateStatus(Lnet/minecraft/world/level/entity/Visibility;Lnet/minecraft/world/level/entity/Visibility;)V",
					shift = Shift.AFTER
			)
	)
	public void afterSectionChange(CallbackInfo ci, @Local(ordinal = 0) BlockPos pos,
								   @Local(ordinal = 0) long newKey, @Share("oldKey") LocalLongRef oldKey) {

		if (this.entity instanceof Entity realEntity) {
			EntityEvents.ENTERING_SECTION.invoker().onEntityEnterSection(realEntity, oldKey.get(), newKey);
		}
	}
}
