package ambos.slimskin.mixin;

import ambos.slimskin.CustomModelBiped;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.ModelZombie;
import net.minecraft.src.RenderLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderLiving.class, remap = false)
public class RenderLivingMixin {
    @Shadow
    protected ModelBase mainModel;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void createNewModel(ModelBase modelbase, float f, CallbackInfo ci) {
        if (this.mainModel instanceof ModelBiped && !(this.mainModel instanceof ModelZombie)) {
            this.mainModel = new CustomModelBiped(0, 0);
        }
    }
}
