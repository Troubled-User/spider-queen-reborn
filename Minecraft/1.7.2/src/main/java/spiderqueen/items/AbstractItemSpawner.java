/*******************************************************************************
 * AbstractItemSpawner.java
 * Copyright (c) 2014 Radix-Shock Entertainment.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/

package spiderqueen.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

/**
 * Defines what an Female Spawner Egg is and how it behaves.
 */
public abstract class AbstractItemSpawner extends Item
{
	/**
	 * Constructor
	 */
	public AbstractItemSpawner()
	{
		super();
	}

	/**
	 * Called when the player right clicks a block while holding this item.
	 * 
	 * @param itemStack
	 *            The item stack that the player was holding when they right
	 *            clicked.
	 * @param player
	 *            The player that right clicked.
	 * @param world
	 *            The world that the player right clicked in.
	 * @param posX
	 *            X coordinate of the block that the player right clicked.
	 * @param posY
	 *            Y coordinate of the block that the player right clicked.
	 * @param posZ
	 *            Z coordinate of the block that the player right clicked.
	 * @param meta
	 *            Metadata associated with the block clicked.
	 * @param xOffset
	 *            X offset of the point where the block was clicked.
	 * @param yOffset
	 *            Y offset of the point where the block was clicked.
	 * @param zOffset
	 *            Z offset of the point where the block was clicked.
	 * 
	 * @return True or false depending on if placing the item into the world was
	 *         successful.
	 */
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int posX, int posY, int posZ, int meta, float xOffset, float yOffset, float zOffset)
	{
		if (world.isRemote) { return true; }

		final Block block = world.getBlock(posX, posY, posZ);
		double verticalOffset = 0.0D;

		posX += Facing.offsetsXForSide[meta];
		posY += Facing.offsetsYForSide[meta];
		posZ += Facing.offsetsZForSide[meta];

		if (meta == 1 && block == Blocks.fence || block == Blocks.nether_brick_fence)
		{
			verticalOffset = 0.5D;
		}

		if (spawnEntity(world, player, posX + 0.5D, posY + verticalOffset, posZ + 0.5D) && !player.capabilities.isCreativeMode)
		{
			itemStack.stackSize--;
		}

		return true;
	}

	@Override
	public abstract void registerIcons(IIconRegister IIconRegister);

	/**
	 * Spawns an entity into the world.
	 * 
	 * @param world
	 *            The world that the entity is being spawned in.
	 * @param entityPlayer
	 *            The player that spawned the entity.
	 * @param posX
	 *            X coordinates that the player clicked.
	 * @param posY
	 *            Y coordinates that the player clicked.
	 * @param posZ
	 *            Z coordinates that the player clicked.
	 * 
	 * @return True or false depending on if placing the villager into the world
	 *         was successful.
	 */
	public abstract boolean spawnEntity(World world, EntityPlayer player, double posX, double posY, double posZ);
}
