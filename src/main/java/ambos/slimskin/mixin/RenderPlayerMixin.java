package ambos.slimskin.mixin;

import ambos.slimskin.CustomModelBiped;
import ambos.slimskin.SlimSkin;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderPlayer.class, remap = false)
public class RenderPlayerMixin extends RenderLiving {
    private CustomModelBiped newModel;
    @Shadow
    private ModelBiped modelBipedMain;

    private RenderPlayerMixin(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void replaceModel(CallbackInfo ci) {
        this.modelBipedMain = this.newModel = (CustomModelBiped) this.mainModel;
    }


    @Inject(method = "renderPlayer", at = @At("HEAD"))
    private void injectModel(EntityPlayer entityplayer, double d, double d1, double d2, float f, float f1, CallbackInfo ci) {
        newModel.setSlim(SlimSkin.areSlim.containsKey(entityplayer.username));

        newModel.field_1278_i = entityplayer.inventory.getCurrentItem() != null;
        newModel.isSneak = entityplayer.isSneaking();
    }

    @Inject(method = "renderPlayer", at = @At("RETURN"))
    private void injectModel2(EntityPlayer entityplayer, double d, double d1, double d2, float f, float f1, CallbackInfo ci) {
        newModel.isSneak = false;
        newModel.field_1278_i = false;
    }

    /*@Redirect(method = "drawFirstPersonHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ModelBiped;setRotationAngles(FFFFFF)V"))
    private void onSetRotationAngles(ModelBiped m, float f1, float f2, float f3, float f4, float f5, float f6) {

    }
    */

//    @Inject(method = "drawFirstPersonHand", at = @At("RETURN"))
//    private void injectModel3(CallbackInfo ci) {
//        newModel.setSlim(SlimSkin.areSlim.containsKey(entityplayer.username));
//    }

    /**
     * @author Ambos
     * @reason Testing
     */
    @Overwrite
    public void drawFirstPersonHand() {
        newModel.onGround = 0.0F;

        this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        newModel.bipedRightArm.render(0.0625F);
        newModel.bipedRightArmwear.render(0.0625F);
    }
}

