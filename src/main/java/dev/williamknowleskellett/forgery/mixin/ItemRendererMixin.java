package dev.williamknowleskellett.forgery.mixin;

import dev.williamknowleskellett.forgery.Forgery;
import dev.williamknowleskellett.forgery.ForgeryItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(at = @At("HEAD"), method = "getModel(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)Lnet/minecraft/client/render/model/BakedModel;", cancellable = true)
    private void getHeldItemModelInjector(ItemStack stack, World world, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
        if (stack.isOf(ForgeryItems.FORGERY)) {
            NbtCompound nbt = stack.getNbt();
            if (nbt != null && nbt.contains("forged", 10)) {
                NbtCompound nbtCompound = nbt.getCompound("forged");
//                Forgery.LOGGER.info("forged was " + nbtCompound);
                stack = ItemStack.fromNbt(nbtCompound);
                BakedModel returnValue = ((ItemRenderer) (Object) this).getModel(stack, world, entity, seed);
                cir.setReturnValue(returnValue);
            }
        }
    }
}
