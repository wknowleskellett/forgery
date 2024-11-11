package dev.williamknowleskellett.forgery.mixin;

import dev.williamknowleskellett.forgery.ForgeryItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Unique
	private ItemStack getForged() {
		ItemStack that = (ItemStack) (Object) this;
		if (that.isOf(ForgeryItems.FORGERY)) {
			NbtCompound nbt = that.getNbt();
			if (nbt != null && nbt.contains("forged", 10)) {
				NbtCompound nbtCompound = nbt.getCompound("forged");
				return ItemStack.fromNbt(nbtCompound);
			}
		}
		return null;
	}
	@Unique
	private void setForged(ItemStack forged) {
		ItemStack that = (ItemStack) (Object) this;
		if (that.isOf(ForgeryItems.FORGERY)) {
			NbtCompound forgedNbt = forged.writeNbt(new NbtCompound());
//			Forgery.LOGGER.info("Set forged of "+that.getName().getString()+" to "+ forgedNbt);
			that.setSubNbt("forged", forgedNbt);
		}
	}

	@Inject(at = @At("HEAD"), method = "getMaxCount", cancellable = true)
	private void getMaxCountInjector(CallbackInfoReturnable<Integer> cir) {
		ItemStack forged = getForged();
		if (forged != null) {
			cir.setReturnValue(forged.getMaxCount());
		}
	}

	@Inject(at = @At("HEAD"), method = "isStackable", cancellable = true)
	private void isStackableInjector(CallbackInfoReturnable<Boolean> cir) {
		ItemStack forged = getForged();
		if (forged != null) {
			cir.setReturnValue(forged.isStackable());
		}
	}

	@Inject(at = @At("HEAD"), method = "isItemBarVisible", cancellable = true)
	private void isItemBarVisibleInjector(CallbackInfoReturnable<Boolean> cir) {
		ItemStack forged = getForged();
		if (forged != null) {
			cir.setReturnValue(forged.isItemBarVisible());
		}
	}

	@Inject(at = @At("HEAD"), method = "getItemBarStep", cancellable = true)
	private void getItemBarStepInjector(CallbackInfoReturnable<Integer> cir) {
		ItemStack forged = getForged();
		if (forged != null) {
			cir.setReturnValue(forged.getItemBarStep());
		}
	}

	@Inject(at = @At("HEAD"), method = "getItemBarColor", cancellable = true)
	private void getItemBarColorInjector(CallbackInfoReturnable<Integer> cir) {
		ItemStack forged = getForged();
		if (forged != null) {
			cir.setReturnValue(forged.getItemBarColor());
		}
	}

	@Inject(at = @At("HEAD"), method = "getTranslationKey", cancellable = true)
	private void getTranslationKeyInjector(CallbackInfoReturnable<String> cir) {
		ItemStack forged = getForged();
		if (forged != null) {
			cir.setReturnValue(forged.getTranslationKey());
		}
	}

	@Inject(at = @At("HEAD"), method = "getName", cancellable = true)
	private void getNameInjector(CallbackInfoReturnable<Text> cir) {
		ItemStack forged = getForged();
		if (forged != null) {
			cir.setReturnValue(forged.getName());
		}
	}

	@Inject(at = @At("HEAD"), method = "setCustomName", cancellable = true)
	private void setCustomNameInjector(Text name, CallbackInfoReturnable<ItemStack> cir) {
		ItemStack forged = getForged();
		if (forged != null) {
			forged.setCustomName(name);
			setForged(forged);
			cir.setReturnValue(((ItemStack)(Object)this));
		}
	}

	@Inject(at = @At("HEAD"), method = "removeCustomName", cancellable = true)
	private void removeCustomNameInjector(CallbackInfo ci) {
		ItemStack forged = getForged();
		if (forged != null) {
			forged.removeCustomName();
			setForged(forged);
			ci.cancel();
		}
	}

	@Inject(at = @At("HEAD"), method = "hasCustomName", cancellable = true)
	private void hasCustomNameInjector(CallbackInfoReturnable<Boolean> cir) {
		ItemStack forged = getForged();
		if (forged != null) {
			cir.setReturnValue(forged.hasCustomName());
		}
	}

	@Inject(at = @At("HEAD"), method = "getTooltip", cancellable = true)
	private void getTooltipInjector(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
		ItemStack forged = getForged();
		if (forged != null) {
			cir.setReturnValue(forged.getTooltip(player, context));
		}
	}

	@Inject(at = @At("HEAD"), method = "hasGlint", cancellable = true)
	private void hasGlintInjector(CallbackInfoReturnable<Boolean> cir) {
		ItemStack forged = getForged();
		if (forged != null) {
			cir.setReturnValue(forged.hasGlint());
		}
	}

	@Inject(at = @At("HEAD"), method = "getRarity", cancellable = true)
	private void getRarityInjector(CallbackInfoReturnable<Rarity> cir) {
		ItemStack forged = getForged();
		if (forged != null) {
			cir.setReturnValue(forged.getRarity());
		}
	}

	@Inject(at = @At("HEAD"), method = "toHoverableText", cancellable = true)
	private void toHoverableTextInjector(CallbackInfoReturnable<Text> cir) {
		ItemStack forged = getForged();
		if (forged != null) {
			cir.setReturnValue(forged.toHoverableText());
		}
	}
}