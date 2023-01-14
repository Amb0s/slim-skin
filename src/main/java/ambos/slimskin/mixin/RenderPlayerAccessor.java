package ambos.slimskin.mixin;

import net.minecraft.src.ModelBiped;
import net.minecraft.src.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = RenderPlayer.class, remap = false)
public interface RenderPlayerAccessor {
    @Accessor(value = "modelBipedMain", remap = false)
    ModelBiped getModelBipedMain();
}
