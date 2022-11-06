package ambos.slimskin.mixin;

import net.minecraft.src.PlayerSkinParser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;

@Mixin(value = PlayerSkinParser.class, remap = false)
public class PlayerSkinParserMixin {
    @Shadow
    private int[] imageData;
    @Shadow
    private int width;
    @Shadow
    private int height;

    /**
     * @author Ambos
     * @reason Testing
     */
    @Overwrite
    public BufferedImage parseImage(BufferedImage image) {
        if (image == null) {
            return null;
        } else {
            this.height = 64;
            int i = image.getWidth();
            int j = image.getHeight();
            int k;

            for (k = 1; this.width < i || this.height < j; k *= 2) {
                this.width *= 2;
                this.height *= 2;
            }

            BufferedImage bufferedimage = new BufferedImage(this.width, this.height, 2);
            Graphics graphics = bufferedimage.getGraphics();
            graphics.drawImage(image, 0, 0, (ImageObserver) null);

            if (image.getHeight() == 32 * k) {
                graphics.drawImage(bufferedimage, 24 * k, 48 * k, 20 * k, 52 * k, 4 * k, 16 * k, 8 * k, 20 * k, (ImageObserver) null);
                graphics.drawImage(bufferedimage, 28 * k, 48 * k, 24 * k, 52 * k, 8 * k, 16 * k, 12 * k, 20 * k, (ImageObserver) null);
                graphics.drawImage(bufferedimage, 20 * k, 52 * k, 16 * k, 64 * k, 8 * k, 20 * k, 12 * k, 32 * k, (ImageObserver) null);
                graphics.drawImage(bufferedimage, 24 * k, 52 * k, 20 * k, 64 * k, 4 * k, 20 * k, 8 * k, 32 * k, (ImageObserver) null);
                graphics.drawImage(bufferedimage, 28 * k, 52 * k, 24 * k, 64 * k, 0 * k, 20 * k, 4 * k, 32 * k, (ImageObserver) null);
                graphics.drawImage(bufferedimage, 32 * k, 52 * k, 28 * k, 64 * k, 12 * k, 20 * k, 16 * k, 32 * k, (ImageObserver) null);
                graphics.drawImage(bufferedimage, 40 * k, 48 * k, 36 * k, 52 * k, 44 * k, 16 * k, 48 * k, 20 * k, (ImageObserver) null);
                graphics.drawImage(bufferedimage, 44 * k, 48 * k, 40 * k, 52 * k, 48 * k, 16 * k, 52 * k, 20 * k, (ImageObserver) null);
                graphics.drawImage(bufferedimage, 36 * k, 52 * k, 32 * k, 64 * k, 48 * k, 20 * k, 52 * k, 32 * k, (ImageObserver) null);
                graphics.drawImage(bufferedimage, 40 * k, 52 * k, 36 * k, 64 * k, 44 * k, 20 * k, 48 * k, 32 * k, (ImageObserver) null);
                graphics.drawImage(bufferedimage, 44 * k, 52 * k, 40 * k, 64 * k, 40 * k, 20 * k, 44 * k, 32 * k, (ImageObserver) null);
                graphics.drawImage(bufferedimage, 48 * k, 52 * k, 44 * k, 64 * k, 52 * k, 20 * k, 56 * k, 32 * k, (ImageObserver) null);
            }

            graphics.dispose();
            this.imageData = ((DataBufferInt) bufferedimage.getRaster().getDataBuffer()).getData();

            this.doSomething2(0, 0, 32 * k, 16 * k);
            this.doSomething(32 * k, 0, 64 * k, 32 * k);
            this.doSomething2(0, 16 * k, 64 * k, 32 * k);
            this.doSomething(0, 32 * k, 16 * k, 48 * k);
            this.doSomething(16 * k, 32 * k, 40 * k, 48 * k);
            this.doSomething(40 * k, 32 * k, 56 * k, 48 * k);
            this.doSomething(0, 48 * k, 16 * k, 64 * k);
            this.doSomething2(16 * k, 48 * k, 48 * k, 64 * k);
            this.doSomething(48 * k, 48 * k, 64 * k, 64 * k);

            return bufferedimage;
        }
    }

    private void doSomething(int i1, int i2, int i3, int i4) {
        if(!this.checkSomething(i1, i2, i3, i4)) {
            for(int i5 = i1; i5 < i3; ++i5) {
                for(int i6 = i2; i6 < i4; ++i6) {
                    this.imageData[i5 + i6 * this.width] &= 0xFFFFFF;
                }
            }
        }
    }

    private void doSomething2(int i1, int i2, int i3, int i4) {
        for(int i5 = i1; i5 < i3; ++i5) {
            for(int i6 = i2; i6 < i4; ++i6) {
                this.imageData[i5 + i6 * this.width] |= 0xFF000000;
            }
        }
    }

    private boolean checkSomething(int i1, int i2, int i3, int i4) {
        for(int i5 = i1; i5 < i3; ++i5) {
            for(int i6 = i2; i6 < i4; ++i6) {
                int i7 = this.imageData[i5 + i6 * this.width];
                if((i7 >> 24 & 255) < 128) {
                    return true;
                }
            }
        }

        return false;
    }
}
