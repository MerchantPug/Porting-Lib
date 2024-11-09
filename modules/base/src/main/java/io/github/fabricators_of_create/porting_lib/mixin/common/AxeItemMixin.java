package io.github.fabricators_of_create.porting_lib.mixin.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import io.github.fabricators_of_create.porting_lib.tool.ItemAbilities;
import io.github.fabricators_of_create.porting_lib.util.PortingHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

// This really should be in the item abilities modules fix this TODO: fix this on 1.21
@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Unique
    private UseOnContext porting_lib_base$context;

	@Inject(method = "useOn", at = @At("HEAD"))
	private void shareUseOnContext(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
		porting_lib_base$context = context;
	}

	@WrapOperation(method = "evaluateNewBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/AxeItem;getStripped(Lnet/minecraft/world/level/block/state/BlockState;)Ljava/util/Optional;"))
	private Optional<BlockState> onStripToolAction(AxeItem instance, BlockState blockState, Operation<Optional<BlockState>> original) {
		BlockState eventState = PortingHooks.onToolUse(blockState, porting_lib_base$context, ItemAbilities.AXE_STRIP, false);
		return eventState != blockState ? Optional.ofNullable(eventState) : original.call(instance, blockState);
	}

	@WrapOperation(method = "evaluateNewBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/WeatheringCopper;getPrevious(Lnet/minecraft/world/level/block/state/BlockState;)Ljava/util/Optional;"))
	private Optional<BlockState> onScrapeToolAction(BlockState blockState, Operation<Optional<BlockState>> original) {
		BlockState eventState = PortingHooks.onToolUse(blockState, porting_lib_base$context, ItemAbilities.AXE_SCRAPE, false);
		return eventState != blockState ? Optional.ofNullable(eventState) : original.call(blockState);
	}

    @Inject(method = "evaluateNewBlockState", at = @At("RETURN"))
    private void onScrapeToolAction(Level level, BlockPos pos, Player player, BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
        porting_lib_base$context = null;
    }
}
