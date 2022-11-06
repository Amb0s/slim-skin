package ambos.slimskin;

public class CustomModelSlimArms {
    private final CustomModelRenderer slimArms;
    private final CustomModelRenderer arms;
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    public boolean isSlim = false;
    public boolean mirror = false;
    private boolean outer;
    private final float value;

    public CustomModelSlimArms(int i, int i2, float scale, float value, boolean outer) {
        this.outer = outer;
        this.value = value;

        if (outer) {
            scale = scale + 0.25F;
        }

        this.slimArms = new CustomModelRenderer(i, i2);
        this.slimArms.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, scale, 64, 64);
        this.slimArms.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.slimArms.showModel = false;

        this.arms = new CustomModelRenderer(i, i2);
        this.arms.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scale, 64, 64);
        this.arms.setRotationPoint(5.0F, 1.5F, 0.0F);
        this.arms.showModel = false;
    }

    public void setRotationPoint(float var1, float var2, float var3) {
        this.rotationPointX = var1;
        this.rotationPointY = var2;
        this.rotationPointZ = var3;
    }

    public void setSlim(boolean b) {
        this.isSlim = b;
    }

    public CustomModelRenderer getModel()  {
        return isSlim ? slimArms : arms;
    }

    public void render(float scale) {
        if (isSlim) {
            this.slimArms.showModel = true;

            if (!mirror) {
                this.rotationPointX -= 1F;
                if(this.outer)
                    this.rotationPointX += 1F;
            } else {
                if (this.outer)
                    this.rotationPointX -= 1F;
            }

            this.rotationPointY = 2.0F + value;

            doRotations(slimArms);
            slimArms.render(scale);
        } else {
            this.arms.showModel = true;

            if (mirror && !this.outer) {
                this.rotationPointX += 2F;
            }

            if (!mirror && this.outer) {
                this.rotationPointX += 2F;
            }

            this.rotationPointY = 2.0F + value;

            doRotations(arms);
            arms.render(scale);
        }
    }

    public void postRender(float scale) {
        if (isSlim) {
            slimArms.postRender(scale);
        } else {
            arms.postRender(scale);
        }
    }

    private void doRotations(CustomModelRenderer arm) {
        arm.rotationPointX = this.rotationPointX;
        arm.rotationPointY = this.rotationPointY;
        arm.rotationPointZ = this.rotationPointZ;
        arm.rotateAngleX = this.rotateAngleX;
        arm.rotateAngleY = this.rotateAngleY;
        arm.rotateAngleZ = this.rotateAngleZ;
        arm.mirror = this.mirror;
    }
}
