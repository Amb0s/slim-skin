package ambos.slimskin.mixin;

import ambos.slimskin.CustomModelBiped;
import ambos.slimskin.SlimSkin;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = ItemRenderer.class, remap = false)
public class ItemRendererMixin {
    @Inject(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/RenderPlayer;drawFirstPersonHand()V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, remap = false, require = 0)
    public void injectPreFPH(float f, CallbackInfo ci, float f1, EntityPlayerSP entityplayersp, float f2, ItemStack itemstack, float f3, float f4, float f8, float f12, float f16, Render render1, RenderPlayer renderplayer1) {
        ((CustomModelBiped)((RenderPlayerAccessor)renderplayer1).getModelBipedMain()).setSlim(SlimSkin.areSlim.containsKey(entityplayersp.username));
    }
}
