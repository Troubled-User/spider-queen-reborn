/*******************************************************************************
 * ItemSpawnEnemyQueen.java
 * Copyright (c) 2014 Radix-Shock Entertainment.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/

package spiderqueen.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import spiderqueen.core.SpiderQueen;
import spiderqueen.entity.EntityEnemyQueen;

public class ItemSpawnEnemyQueen extends AbstractItemSpawner
{
	public ItemSpawnEnemyQueen()
	{
		super();
		setCreativeTab(SpiderQueen.getInstance().tabSpiderQueen);
	}
	
	@Override
	public void registerIcons(IIconRegister IIconRegister) 
	{
		itemIcon = IIconRegister.registerIcon("spiderqueen:SpawnEnemyQueen");
	}

	@Override
	public boolean spawnEntity(World world, EntityPlayer player, double posX, double posY, double posZ) 
	{
		if (world.isRemote)
		{	
			return true;
		}

		else
		{
			final EntityEnemyQueen entityEnemyQueen = new EntityEnemyQueen(world);
			entityEnemyQueen.setLocationAndAngles(posX, posY, posZ, world.rand.nextFloat() * 360F, 0.0F);

			if (!world.isRemote)
			{
				world.spawnEntityInWorld(entityEnemyQueen);
				entityEnemyQueen.spawnAdditionalSpiders();
			}

			return true;
		}
	}
	
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
	{	
		par3List.add("Spawns an enemy spider queen.");
	}
}