package spiderqueen.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import spiderqueen.entity.EntityHatchedSpider;
import spiderqueen.enums.EnumCocoonType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHatchedSpider extends ModelBase
{
	public ModelRenderer defaultSpiderHead;
	public ModelRenderer defaultSpiderNeck;
	public ModelRenderer defaultSpiderBody;
	public ModelRenderer defaultSpiderLeg1;
	public ModelRenderer defaultSpiderLeg2;
	public ModelRenderer defaultSpiderLeg3;
	public ModelRenderer defaultSpiderLeg4;
	public ModelRenderer defaultSpiderLeg5;
	public ModelRenderer defaultSpiderLeg6;
	public ModelRenderer defaultSpiderLeg7;
	public ModelRenderer defaultSpiderLeg8;

	public ModelRenderer raisedSpiderHead;
	public ModelRenderer raisedSpiderBody;
	public ModelRenderer raisedSpiderRearEnd;
	public ModelRenderer raisedSpiderLeg1;
	public ModelRenderer raisedSpiderLeg2;
	public ModelRenderer raisedSpiderLeg3;
	public ModelRenderer raisedSpiderLeg4;
	public ModelRenderer raisedSpiderLeg5;
	public ModelRenderer raisedSpiderLeg6;
	public ModelRenderer raisedSpiderLeg7;
	public ModelRenderer raisedSpiderLeg8;

	public ModelRenderer tinySpiderHead;
	public ModelRenderer tinySpiderBody;
	public ModelRenderer tinySpiderRearEnd;
	public ModelRenderer tinySpiderLeg1;
	public ModelRenderer tinySpiderLeg2;
	public ModelRenderer tinySpiderLeg3;
	public ModelRenderer tinySpiderLeg4;
	public ModelRenderer tinySpiderLeg5;
	public ModelRenderer tinySpiderLeg6;
	public ModelRenderer tinySpiderLeg7;
	public ModelRenderer tinySpiderLeg8;

	public ModelHatchedSpider()
	{
		initDefaultSpider();
		initRaisedSpider();
		initTinySpider();
	}

	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		final EntityHatchedSpider spider = (EntityHatchedSpider)par1Entity;
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

		if (EnumCocoonType.isAnimalBased(spider.cocoonType) || spider.cocoonType == EnumCocoonType.EMPTY)
		{
			this.defaultSpiderHead.render(par7);
			this.defaultSpiderNeck.render(par7);
			this.defaultSpiderBody.render(par7);
			this.defaultSpiderLeg1.render(par7);
			this.defaultSpiderLeg2.render(par7);
			this.defaultSpiderLeg3.render(par7);
			this.defaultSpiderLeg4.render(par7);
			this.defaultSpiderLeg5.render(par7);
			this.defaultSpiderLeg6.render(par7);
			this.defaultSpiderLeg7.render(par7);
			this.defaultSpiderLeg8.render(par7);
		}

		else if (spider.cocoonType == EnumCocoonType.CREEPER)
		{
			this.raisedSpiderHead.render(par7);
			this.raisedSpiderBody.render(par7);
			this.raisedSpiderRearEnd.render(par7);
			this.raisedSpiderLeg1.render(par7);
			this.raisedSpiderLeg2.render(par7);
			this.raisedSpiderLeg3.render(par7);
			this.raisedSpiderLeg4.render(par7);
			this.raisedSpiderLeg5.render(par7);
			this.raisedSpiderLeg6.render(par7);
			this.raisedSpiderLeg7.render(par7);
			this.raisedSpiderLeg8.render(par7);
		}
		
		else if (spider.cocoonType == EnumCocoonType.WOLF)
		{
			this.tinySpiderHead.render(par7);
			this.tinySpiderBody.render(par7);
			this.tinySpiderRearEnd.render(par7);
			this.tinySpiderLeg1.render(par7);
			this.tinySpiderLeg2.render(par7);
			this.tinySpiderLeg3.render(par7);
			this.tinySpiderLeg4.render(par7);
			this.tinySpiderLeg5.render(par7);
			this.tinySpiderLeg6.render(par7);
			this.tinySpiderLeg7.render(par7);
			this.tinySpiderLeg8.render(par7);
		}
	}

	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		final EntityHatchedSpider spider = (EntityHatchedSpider)par7Entity;

		if (EnumCocoonType.isAnimalBased(spider.cocoonType) || spider.cocoonType == EnumCocoonType.EMPTY)
		{
			this.defaultSpiderHead.rotateAngleY = par4 / (180F / (float)Math.PI);
			this.defaultSpiderHead.rotateAngleX = par5 / (180F / (float)Math.PI);
			float f6 = ((float)Math.PI / 4F);
			this.defaultSpiderLeg1.rotateAngleZ = -f6;
			this.defaultSpiderLeg2.rotateAngleZ = f6;
			this.defaultSpiderLeg3.rotateAngleZ = -f6 * 0.74F;
			this.defaultSpiderLeg4.rotateAngleZ = f6 * 0.74F;
			this.defaultSpiderLeg5.rotateAngleZ = -f6 * 0.74F;
			this.defaultSpiderLeg6.rotateAngleZ = f6 * 0.74F;
			this.defaultSpiderLeg7.rotateAngleZ = -f6;
			this.defaultSpiderLeg8.rotateAngleZ = f6;
			float f7 = -0.0F;
			float f8 = 0.3926991F;
			this.defaultSpiderLeg1.rotateAngleY = f8 * 2.0F + f7;
			this.defaultSpiderLeg2.rotateAngleY = -f8 * 2.0F - f7;
			this.defaultSpiderLeg3.rotateAngleY = f8 * 1.0F + f7;
			this.defaultSpiderLeg4.rotateAngleY = -f8 * 1.0F - f7;
			this.defaultSpiderLeg5.rotateAngleY = -f8 * 1.0F + f7;
			this.defaultSpiderLeg6.rotateAngleY = f8 * 1.0F - f7;
			this.defaultSpiderLeg7.rotateAngleY = -f8 * 2.0F + f7;
			this.defaultSpiderLeg8.rotateAngleY = f8 * 2.0F - f7;
			float f9 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * par2;
			float f10 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * par2;
			float f11 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * par2;
			float f12 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + ((float)Math.PI * 3F / 2F)) * 0.4F) * par2;
			float f13 = Math.abs(MathHelper.sin(par1 * 0.6662F + 0.0F) * 0.4F) * par2;
			float f14 = Math.abs(MathHelper.sin(par1 * 0.6662F + (float)Math.PI) * 0.4F) * par2;
			float f15 = Math.abs(MathHelper.sin(par1 * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * par2;
			float f16 = Math.abs(MathHelper.sin(par1 * 0.6662F + ((float)Math.PI * 3F / 2F)) * 0.4F) * par2;
			this.defaultSpiderLeg1.rotateAngleY += f9;
			this.defaultSpiderLeg2.rotateAngleY += -f9;
			this.defaultSpiderLeg3.rotateAngleY += f10;
			this.defaultSpiderLeg4.rotateAngleY += -f10;
			this.defaultSpiderLeg5.rotateAngleY += f11;
			this.defaultSpiderLeg6.rotateAngleY += -f11;
			this.defaultSpiderLeg7.rotateAngleY += f12;
			this.defaultSpiderLeg8.rotateAngleY += -f12;
			this.defaultSpiderLeg1.rotateAngleZ += f13;
			this.defaultSpiderLeg2.rotateAngleZ += -f13;
			this.defaultSpiderLeg3.rotateAngleZ += f14;
			this.defaultSpiderLeg4.rotateAngleZ += -f14;
			this.defaultSpiderLeg5.rotateAngleZ += f15;
			this.defaultSpiderLeg6.rotateAngleZ += -f15;
			this.defaultSpiderLeg7.rotateAngleZ += f16;
			this.defaultSpiderLeg8.rotateAngleZ += -f16;
		}

		else if (spider.cocoonType == EnumCocoonType.CREEPER)
		{
			raisedSpiderHead.rotateAngleY = par4 / 57.29578F;
			raisedSpiderHead.rotateAngleX = par5 / 57.29578F;
			float f6 = 0.7853982F;
			raisedSpiderLeg1.rotateAngleZ = -f6;
			raisedSpiderLeg2.rotateAngleZ = f6;
			raisedSpiderLeg3.rotateAngleZ = -f6 * 0.74F;
			raisedSpiderLeg4.rotateAngleZ = f6 * 0.74F;
			raisedSpiderLeg5.rotateAngleZ = -f6 * 0.74F;
			raisedSpiderLeg6.rotateAngleZ = f6 * 0.74F;
			raisedSpiderLeg7.rotateAngleZ = -f6;
			raisedSpiderLeg8.rotateAngleZ = f6;
			float f7 = -0F;
			float f8 = 0.3926991F;
			raisedSpiderLeg1.rotateAngleY = f8 * 2.0F + f7;
			raisedSpiderLeg2.rotateAngleY = -f8 * 2.0F - f7;
			raisedSpiderLeg3.rotateAngleY = f8 * 1.0F + f7;
			raisedSpiderLeg4.rotateAngleY = -f8 * 1.0F - f7;
			raisedSpiderLeg5.rotateAngleY = -f8 * 1.0F + f7;
			raisedSpiderLeg6.rotateAngleY = f8 * 1.0F - f7;
			raisedSpiderLeg7.rotateAngleY = -f8 * 2.0F + f7;
			raisedSpiderLeg8.rotateAngleY = f8 * 2.0F - f7;
			float f9 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * par2;
			float f10 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 3.141593F) * 0.4F) * par2;
			float f11 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 1.570796F) * 0.4F) * par2;
			float f12 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 4.712389F) * 0.4F) * par2;
			float f13 = Math.abs(MathHelper.sin(par1 * 0.6662F + 0.0F) * 0.4F) * par2;
			float f14 = Math.abs(MathHelper.sin(par1 * 0.6662F + 3.141593F) * 0.4F) * par2;
			float f15 = Math.abs(MathHelper.sin(par1 * 0.6662F + 1.570796F) * 0.4F) * par2;
			float f16 = Math.abs(MathHelper.sin(par1 * 0.6662F + 4.712389F) * 0.4F) * par2;
			raisedSpiderLeg1.rotateAngleY += f9;
			raisedSpiderLeg2.rotateAngleY += -f9;
			raisedSpiderLeg3.rotateAngleY += f10;
			raisedSpiderLeg4.rotateAngleY += -f10;
			raisedSpiderLeg5.rotateAngleY += f11;
			raisedSpiderLeg6.rotateAngleY += -f11;
			raisedSpiderLeg7.rotateAngleY += f12;
			raisedSpiderLeg8.rotateAngleY += -f12;
			raisedSpiderLeg1.rotateAngleZ += f13;
			raisedSpiderLeg2.rotateAngleZ += -f13;
			raisedSpiderLeg3.rotateAngleZ += f14;
			raisedSpiderLeg4.rotateAngleZ += -f14;
			raisedSpiderLeg5.rotateAngleZ += f15;
			raisedSpiderLeg6.rotateAngleZ += -f15;
			raisedSpiderLeg7.rotateAngleZ += f16;
			raisedSpiderLeg8.rotateAngleZ += -f16;
		}

		else if (spider.cocoonType == EnumCocoonType.WOLF)
		{
			tinySpiderHead.rotateAngleY = par4 / 57.29578F;
			tinySpiderHead.rotateAngleX = par5 / 57.29578F;
			float f6 = 0.7853982F;
			tinySpiderLeg1.rotateAngleZ = -f6;
			tinySpiderLeg2.rotateAngleZ = f6;
			tinySpiderLeg3.rotateAngleZ = -f6 * 0.74F;
			tinySpiderLeg4.rotateAngleZ = f6 * 0.74F;
			tinySpiderLeg5.rotateAngleZ = -f6 * 0.74F;
			tinySpiderLeg6.rotateAngleZ = f6 * 0.74F;
			tinySpiderLeg7.rotateAngleZ = -f6;
			tinySpiderLeg8.rotateAngleZ = f6;
			float f7 = -0F;
			float f8 = 0.3926991F;
			tinySpiderLeg1.rotateAngleY = f8 * 2.0F + f7;
			tinySpiderLeg2.rotateAngleY = -f8 * 2.0F - f7;
			tinySpiderLeg3.rotateAngleY = f8 * 1.0F + f7;
			tinySpiderLeg4.rotateAngleY = -f8 * 1.0F - f7;
			tinySpiderLeg5.rotateAngleY = -f8 * 1.0F + f7;
			tinySpiderLeg6.rotateAngleY = f8 * 1.0F - f7;
			tinySpiderLeg7.rotateAngleY = -f8 * 2.0F + f7;
			tinySpiderLeg8.rotateAngleY = f8 * 2.0F - f7;
			float f9 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * par2;
			float f10 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 3.141593F) * 0.4F) * par2;
			float f11 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 1.570796F) * 0.4F) * par2;
			float f12 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 4.712389F) * 0.4F) * par2;
			float f13 = Math.abs(MathHelper.sin(par1 * 0.6662F + 0.0F) * 0.4F) * par2;
			float f14 = Math.abs(MathHelper.sin(par1 * 0.6662F + 3.141593F) * 0.4F) * par2;
			float f15 = Math.abs(MathHelper.sin(par1 * 0.6662F + 1.570796F) * 0.4F) * par2;
			float f16 = Math.abs(MathHelper.sin(par1 * 0.6662F + 4.712389F) * 0.4F) * par2;
			tinySpiderLeg1.rotateAngleY += f9;
			tinySpiderLeg2.rotateAngleY += -f9;
			tinySpiderLeg3.rotateAngleY += f10;
			tinySpiderLeg4.rotateAngleY += -f10;
			tinySpiderLeg5.rotateAngleY += f11;
			tinySpiderLeg6.rotateAngleY += -f11;
			tinySpiderLeg7.rotateAngleY += f12;
			tinySpiderLeg8.rotateAngleY += -f12;
			tinySpiderLeg1.rotateAngleZ += f13;
			tinySpiderLeg2.rotateAngleZ += -f13;
			tinySpiderLeg3.rotateAngleZ += f14;
			tinySpiderLeg4.rotateAngleZ += -f14;
			tinySpiderLeg5.rotateAngleZ += f15;
			tinySpiderLeg6.rotateAngleZ += -f15;
			tinySpiderLeg7.rotateAngleZ += f16;
			tinySpiderLeg8.rotateAngleZ += -f16;
		}
	}

	private void initDefaultSpider()
	{
		this.defaultSpiderHead = new ModelRenderer(this, 32, 4);
		this.defaultSpiderHead.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
		this.defaultSpiderHead.setRotationPoint(0.0F, 15, -3.0F);

		this.defaultSpiderNeck = new ModelRenderer(this, 0, 0);
		this.defaultSpiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
		this.defaultSpiderNeck.setRotationPoint(0.0F, 15, 0.0F);

		this.defaultSpiderBody = new ModelRenderer(this, 0, 12);
		this.defaultSpiderBody.addBox(-5.0F, -4.0F, -6.0F, 10, 8, 12, 0.0F);
		this.defaultSpiderBody.setRotationPoint(0.0F, 15, 9.0F);

		this.defaultSpiderLeg1 = new ModelRenderer(this, 18, 0);
		this.defaultSpiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		this.defaultSpiderLeg1.setRotationPoint(-4.0F, 15, 2.0F);

		this.defaultSpiderLeg2 = new ModelRenderer(this, 18, 0);
		this.defaultSpiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		this.defaultSpiderLeg2.setRotationPoint(4.0F, 15, 2.0F);

		this.defaultSpiderLeg3 = new ModelRenderer(this, 18, 0);
		this.defaultSpiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		this.defaultSpiderLeg3.setRotationPoint(-4.0F, 15, 1.0F);

		this.defaultSpiderLeg4 = new ModelRenderer(this, 18, 0);
		this.defaultSpiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		this.defaultSpiderLeg4.setRotationPoint(4.0F, 15, 1.0F);

		this.defaultSpiderLeg5 = new ModelRenderer(this, 18, 0);
		this.defaultSpiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		this.defaultSpiderLeg5.setRotationPoint(-4.0F, 15, 0.0F);

		this.defaultSpiderLeg6 = new ModelRenderer(this, 18, 0);
		this.defaultSpiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		this.defaultSpiderLeg6.setRotationPoint(4.0F, 15, 0.0F);

		this.defaultSpiderLeg7 = new ModelRenderer(this, 18, 0);
		this.defaultSpiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		this.defaultSpiderLeg7.setRotationPoint(-4.0F, 15, -1.0F);

		this.defaultSpiderLeg8 = new ModelRenderer(this, 18, 0);
		this.defaultSpiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		this.defaultSpiderLeg8.setRotationPoint(4.0F, 15, -1.0F);
	}

	private void initRaisedSpider()
	{
		this.raisedSpiderHead = new ModelRenderer(this,32, 4);
		this.raisedSpiderHead.addBox(-4F, -4F, -8F, 8, 8, 8, 0.0F);
		this.raisedSpiderHead.setRotationPoint(0F, 15F, -3F);

		this.raisedSpiderBody = new ModelRenderer(this,0, 0);
		this.raisedSpiderBody.addBox(-3F, -3F, -3F, 6, 6, 6, 0.0F);
		this.raisedSpiderBody.setRotationPoint(0F, 15F, 0F);

		this.raisedSpiderRearEnd = new ModelRenderer(this,0, 12);
		this.raisedSpiderRearEnd.addBox(-5F, -4F, -6F, 10, 8, 12, 0.0F);
		this.raisedSpiderRearEnd.setRotationPoint(0F, 11F, 7F);
		this.raisedSpiderRearEnd.rotateAngleX = 0.63284F;

		this.raisedSpiderLeg1 = new ModelRenderer(this,18, 0);
		this.raisedSpiderLeg1.addBox(-15F, -1F, -1F, 16, 2, 2, 0.0F);
		this.raisedSpiderLeg1.setRotationPoint(-4F, 15F, 2F);
		this.raisedSpiderLeg1.rotateAngleX = 0.57596F;
		this.raisedSpiderLeg1.rotateAngleY = 0.19199F;

		this.raisedSpiderLeg2 = new ModelRenderer(this,18, 0);
		this.raisedSpiderLeg2.addBox(-1F, -1F, -1F, 16, 2, 2, 0.0F);
		this.raisedSpiderLeg2.setRotationPoint(4F, 15F, 2F);
		this.raisedSpiderLeg2.rotateAngleX = -0.57596F;
		this.raisedSpiderLeg2.rotateAngleY = -0.19199F;

		this.raisedSpiderLeg3 = new ModelRenderer(this,18, 0);
		this.raisedSpiderLeg3.addBox(-15F, -1F, -1F, 16, 2, 2, 0.0F);
		this.raisedSpiderLeg3.setRotationPoint(-4F, 15F, 1F);
		this.raisedSpiderLeg3.rotateAngleX = 0.27925F;
		this.raisedSpiderLeg3.rotateAngleY = 0.19199F;

		this.raisedSpiderLeg4 = new ModelRenderer(this,18, 0);
		this.raisedSpiderLeg4.addBox(-1F, -1F, -1F, 16, 2, 2, 0.0F);
		this.raisedSpiderLeg4.setRotationPoint(4F, 15F, 1F);
		this.raisedSpiderLeg4.rotateAngleX = -0.27925F;
		this.raisedSpiderLeg4.rotateAngleY = -0.19199F;

		this.raisedSpiderLeg5 = new ModelRenderer(this,18, 0);
		this.raisedSpiderLeg5.addBox(-15F, -1F, -1F, 16, 2, 2, 0.0F);
		this.raisedSpiderLeg5.setRotationPoint(-4F, 15F, 0F);
		this.raisedSpiderLeg5.rotateAngleX = -0.27925F;
		this.raisedSpiderLeg5.rotateAngleY = 0.19199F;

		this.raisedSpiderLeg6 = new ModelRenderer(this,18, 0);
		this.raisedSpiderLeg6.addBox(-1F, -1F, -1F, 16, 2, 2, 0.0F);
		this.raisedSpiderLeg6.setRotationPoint(4F, 15F, 0F);
		this.raisedSpiderLeg6.rotateAngleX = 0.27925F;
		this.raisedSpiderLeg6.rotateAngleY = -0.19199F;

		this.raisedSpiderLeg7 = new ModelRenderer(this,18, 0);
		this.raisedSpiderLeg7.addBox(-15F, -1F, -1F, 16, 2, 2, 0.0F);
		this.raisedSpiderLeg7.setRotationPoint(-4F, 15F, -1F);
		this.raisedSpiderLeg7.rotateAngleX = -0.57596F;
		this.raisedSpiderLeg7.rotateAngleY = 0.19199F;

		this.raisedSpiderLeg8 = new ModelRenderer(this,18, 0);
		this.raisedSpiderLeg8.addBox(-1F, -1F, -1F, 16, 2, 2, 0.0F);
		this.raisedSpiderLeg8.setRotationPoint(4F, 15F, -1F);
		this.raisedSpiderLeg8.rotateAngleX = 0.57596F;
		this.raisedSpiderLeg8.rotateAngleY = -0.19199F;
	}

	private void initTinySpider()
	{	
		tinySpiderHead = new ModelRenderer(this,0, 8);
		tinySpiderHead.addBox(-3F, -2F, -4F, 4, 3, 4, 0.0F);
		tinySpiderHead.setRotationPoint(0F, 19F, -3F);

		tinySpiderBody = new ModelRenderer(this,0, 0);
		tinySpiderBody.addBox(-3F, -3F, -3F, 4, 4, 4, 0.0F);
		tinySpiderBody.setRotationPoint(0F, 19F, 0F);

		tinySpiderRearEnd = new ModelRenderer(this,0, 15);
		tinySpiderRearEnd.addBox(-3F, -4F, 0F, 6, 6, 6, 0.0F);
		tinySpiderRearEnd.setRotationPoint(-1F, 18F, 1F);

		tinySpiderLeg1 = new ModelRenderer(this,18, 0);
		tinySpiderLeg1.addBox(-9F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg1.setRotationPoint(-3F, 19F, 1F);
		tinySpiderLeg1.rotateAngleX = 0.57596F;
		tinySpiderLeg1.rotateAngleY = 0.19199F;

		tinySpiderLeg2 = new ModelRenderer(this,18, 0);
		tinySpiderLeg2.addBox(-1F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg2.setRotationPoint(1F, 19F, 1F);
		tinySpiderLeg2.rotateAngleX = -0.57596F;
		tinySpiderLeg2.rotateAngleY = -0.19199F;

		tinySpiderLeg3 = new ModelRenderer(this,18, 0);
		tinySpiderLeg3.addBox(-9F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg3.setRotationPoint(-3F, 19F, 0F);
		tinySpiderLeg3.rotateAngleX = 0.27925F;
		tinySpiderLeg3.rotateAngleY = 0.19199F;

		tinySpiderLeg4 = new ModelRenderer(this,18, 0);
		tinySpiderLeg4.addBox(-1F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg4.setRotationPoint(1F, 19F, 0F);
		tinySpiderLeg4.rotateAngleX = -0.27925F;
		tinySpiderLeg4.rotateAngleY = -0.19199F;

		tinySpiderLeg5 = new ModelRenderer(this,18, 0);
		tinySpiderLeg5.addBox(-9F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg5.setRotationPoint(-3F, 19F, -1F);
		tinySpiderLeg5.rotateAngleX = -0.27925F;
		tinySpiderLeg5.rotateAngleY = 0.19199F;

		tinySpiderLeg6 = new ModelRenderer(this,18, 0);
		tinySpiderLeg6.addBox(-1F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg6.setRotationPoint(1F, 19F, -1F);
		tinySpiderLeg6.rotateAngleX = 0.27925F;
		tinySpiderLeg6.rotateAngleY = -0.19199F;

		tinySpiderLeg7 = new ModelRenderer(this,18, 0);
		tinySpiderLeg7.addBox(-10F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg7.setRotationPoint(-2F, 19F, -2F);
		tinySpiderLeg7.rotateAngleX = -0.57596F;
		tinySpiderLeg7.rotateAngleY = 0.19199F;

		tinySpiderLeg8 = new ModelRenderer(this,18, 0);
		tinySpiderLeg8.addBox(-1F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg8.setRotationPoint(1F, 19F, -2F);
		tinySpiderLeg8.rotateAngleX = 0.57596F;
		tinySpiderLeg8.rotateAngleY = -0.19199F;
	}
}