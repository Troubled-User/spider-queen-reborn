package spiderqueen.old.client.render;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;

import org.lwjgl.opengl.GL11;

import spiderqueen.old.entity.EntityWeb;

public class RenderWeb extends Render
{

    public RenderWeb()
    {
    }

    public void func_154_a(EntityWeb entityarrow, double d, double d1, double d2, 
            float f, float f1)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        float f2 = 0.70F;
        GL11.glScalef(f2 / 1.0F, f2 / 1.0F, f2 / 1.0F);
        int i = mod_SpiderQueen.itemWeb.getIconFromDamage(0);
        loadTexture("/gui/items.png");
        Tessellator tessellator = Tessellator.instance;
        float f3 = (float)((i % 16) * 16 + 0) / 256F;
        float f4 = (float)((i % 16) * 16 + 16) / 256F;
        float f5 = (float)((i / 16) * 16 + 0) / 256F;
        float f6 = (float)((i / 16) * 16 + 16) / 256F;
        float f7 = 1.0F;
        float f8 = 0.5F;
        float f9 = 0.25F;
        GL11.glRotatef(180F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(0.0F - f8, 0.0F - f9, 0.0D, f3, f6);
        tessellator.addVertexWithUV(f7 - f8, 0.0F - f9, 0.0D, f4, f6);
        tessellator.addVertexWithUV(f7 - f8, 1.0F - f9, 0.0D, f4, f5);
        tessellator.addVertexWithUV(0.0F - f8, 1.0F - f9, 0.0D, f3, f5);
        tessellator.draw();
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
        
        
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        func_154_a((EntityWeb)entity, d, d1, d2, f, f1);
    }
}
