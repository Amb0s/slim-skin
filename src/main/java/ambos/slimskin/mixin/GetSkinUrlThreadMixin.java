package ambos.slimskin.mixin;

import ambos.slimskin.SlimSkin;
import b100.json.element.JsonArray;
import b100.json.element.JsonObject;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.utils.GetSkinUrlThread;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = GetSkinUrlThread.class, remap = false)
public class GetSkinUrlThreadMixin {
    @Shadow private EntityPlayer player;

    @Inject(method = "run", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V", ordinal = 4, shift = At.Shift.BEFORE), remap = false, locals = LocalCapture.CAPTURE_FAILHARD)
    public void injectRun(CallbackInfo ci, String name, String string, JsonObject object, JsonArray properties, JsonObject textureProperty, JsonObject texturesObject) {
        JsonObject skinObject = texturesObject.get("SKIN").getAsObject();
        if (skinObject.get("metadata") != null) {
            String model = skinObject.get("metadata").getAsObject().get("model").getAsString().value;

            if (model.equalsIgnoreCase("slim")) {
                SlimSkin.areSlim.put(this.player.username, true);
            }
        }
        System.out.println("Is slim: " + SlimSkin.areSlim.containsKey(this.player.username));
    }
}
