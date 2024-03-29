/*******************************************************************************
 * GuiSleep.java
 * Copyright (c) 2014 Radix-Shock Entertainment.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/

package spiderqueen.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import spiderqueen.core.SpiderQueen;
import spiderqueen.enums.EnumPacketType;

import com.radixshock.radixcore.network.Packet;

public class GuiSleep extends GuiScreen
{
	private EntityPlayer player;
	private int sleepProgress = 0;
	private boolean hasSentSleepComplete = false;

	public GuiSleep(EntityPlayer player)
	{
		this.player = player;
	}

	@Override
	protected void keyTyped(char keyChar, int keyCode)
	{
		if (keyCode == Keyboard.KEY_ESCAPE)
		{
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		if (sleepProgress != 100)
		{
			sleepProgress += 2;
		}

		else
		{
			if (!hasSentSleepComplete)
			{
				SpiderQueen.packetPipeline.sendPacketToServer(new Packet(EnumPacketType.SleepComplete));
				hasSentSleepComplete = true;
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	public void drawScreen(int posX, int posY, float partialTickTime)
	{
		super.drawScreen(posX, posY, partialTickTime);

		if (sleepProgress > 0)
		{
			ScaledResolution scaledResolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);

			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			{
				float colorIntensity = (float)sleepProgress / 100.0F;

				if (colorIntensity > 1.0F)
				{
					colorIntensity = 1.0F - (float)(sleepProgress - 100) / 10.0F;
				}

				int color = (int)(220.0F * colorIntensity) << 24 | 1052704;

				drawRect(0, 0,  scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), color);
			}
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
	}
}
