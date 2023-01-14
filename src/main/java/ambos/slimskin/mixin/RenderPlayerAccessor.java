package ambos.slimskin.mixin;

import net.minecraft.src.ModelBiped;
import net.minecraft.src.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderPlayer.class)
public interface RenderPlayerAccessor {
    @Accessor("modelBipedMain")
    ModelBiped getModelBipedMain();
}
