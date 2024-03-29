/*******************************************************************************
 * Inventory.java
 * Copyright (c) 2014 Radix-Shock Entertainment.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/

package spiderqueen.inventory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import spiderqueen.core.SpiderQueen;
import spiderqueen.entity.EntityHatchedSpider;
import spiderqueen.enums.EnumPacketType;

import com.radixshock.radixcore.logic.LogicHelper;
import com.radixshock.radixcore.network.Packet;

/**
 * Defines an inventory belonging to an entity from Spider Queen.
 */
public class Inventory implements IInventory, IInvBasic, Serializable
{
	/** The owner of the inventory. */
	public EntityLivingBase	owner;

	/** The armor items that are being worn. */
	public ItemStack		armorItems[];

	/**
	 * The items in the main inventory. Armor items exist here and are copied to
	 * the other array.
	 */
	public ItemStack		inventoryItems[];

	/**
	 * Constructor
	 */
	public Inventory()
	{
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param entity
	 *            The entity this inventory will belong to.
	 */
	public Inventory(EntityLivingBase entity)
	{
		owner = entity;
		inventoryItems = new ItemStack[getSizeInventory()];
		armorItems = new ItemStack[4];
	}

	@Override
	public boolean equals(Object obj)
	{
		try
		{
			if (!(obj instanceof Inventory))
			{
				return false;
			}

			else
			{
				final Inventory otherInventory = (Inventory) obj;

				for (int i = 0; i < getSizeInventory(); i++)
				{
					if (inventoryItems[i] == null || otherInventory.inventoryItems[i] == null)
					{
						if (inventoryItems[i] == null)
						{
							if (otherInventory.inventoryItems[i] != null) { return false; }
						}

						else if (otherInventory.inventoryItems[i] == null)
						{
							if (inventoryItems[i] != null) { return false; }
						}
					}

					else
					{
						if (inventoryItems[i] != otherInventory.inventoryItems[i]) { return false; }
					}
				}

				return true;
			}
		}

		catch (final Exception e)
		{
			SpiderQueen.getInstance().getLogger().log(e);
			return false;
		}
	}

	/**
	 * Gets the number of slots in the inventory.
	 * 
	 * @return The total number of slots in the inventory.
	 */
	@Override
	public final int getSizeInventory()
	{
		if (owner instanceof EntityHatchedSpider)
		{
			final EntityHatchedSpider spider = (EntityHatchedSpider) owner;
			return spider.level * 10;
		}

		else
		{
			return 40;
		}
	}

	/**
	 * Gets an item stack from the inventory.
	 * 
	 * @param slotId
	 *            The slot that the item stack instance should be created from.
	 * 
	 * @return The item stack in the specified slot. Null if there is no stack
	 *         in that slot.
	 */
	@Override
	public ItemStack getStackInSlot(int slotId)
	{
		if (slotId < getSizeInventory())
		{
			return inventoryItems[slotId];
		}

		else
		{
			return null;
		}
	}

	/**
	 * Decreases the size of the stack in a slot by a certain amount.
	 * 
	 * @param slotId
	 *            The slot that contains the item stack to be decreased.
	 * @param removalAmount
	 *            The amount that should be removed from the item stack.
	 * 
	 * @return The new item stack with the appropriate amount removed from it.
	 */
	@Override
	public ItemStack decrStackSize(int slotId, int removalAmount)
	{
		if (slotId == -1)
		{
			return null;
		}

		else
		{
			if (inventoryItems[slotId] == null)
			{
				return null;
			}

			else
			{
				if (inventoryItems[slotId].stackSize <= removalAmount)
				{
					final ItemStack itemstack = inventoryItems[slotId];
					inventoryItems[slotId] = null;
					return itemstack;
				}

				final ItemStack newStack = inventoryItems[slotId].splitStack(removalAmount);

				if (inventoryItems[slotId].stackSize == 0)
				{
					inventoryItems[slotId] = null;
				}

				onInventoryChanged(this);
				return newStack;
			}
		}
	}

	/**
	 * When some containers are closed they call this on each slot, then drop
	 * whatever it returns as an EntityItem - like when you close a workbench
	 * GUI.
	 * 
	 * @param slotId
	 *            The ID of an inventory slot.
	 * 
	 * @return Item stack that should be dropped if it was being held while the
	 *         GUI closed. Null if none should be dropped.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int slotId)
	{
		if (inventoryItems[slotId] == null)
		{
			return null;
		}

		else
		{
			final ItemStack itemStack = inventoryItems[slotId];
			inventoryItems[slotId] = null;
			return itemStack;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 * 
	 * @param slotNumber
	 *            The slot in which the item stack is to be placed in.
	 * @param itemStack
	 *            The item stack that is being placed in the slot.
	 */
	@Override
	public void setInventorySlotContents(int slotNumber, ItemStack itemStack)
	{
		inventoryItems[slotNumber] = itemStack;

		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}

		onInventoryChanged(this);
	}

	/**
	 * Gets the name of the inventory which is displayed in the inventory GUI.
	 * 
	 * @return Localized string that states the name of the inventory.
	 */
	@Override
	public String getInventoryName()
	{
		return "Inventory";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return true;
	}

	@Override
	public void markDirty()
	{
		onInventoryChanged(this);
	}

	/**
	 * Returns the maximum stack size for an inventory slot.
	 * 
	 * @return The maximum stack size for an inventory slot.
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/**
	 * Called when an the contents of an Inventory change.
	 */
	public void onInventoryChanged(Inventory inventory)
	{
		SpiderQueen.packetPipeline.sendPacketToAllPlayers(new Packet(EnumPacketType.SetInventory, owner.getEntityId(), this));
	}

	/**
	 * Can the player use this container?
	 * 
	 * @param player
	 *            The player trying to access the container.
	 * 
	 * @return True or false depending on if the player can use this container.
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		if (owner.isDead) { return false; }

		return player.getDistanceSqToEntity(owner) <= 64D;
	}

	/**
	 * Called when the inventory is opened.
	 */
	@Override
	public void openInventory()
	{
		// Nothing to do.
	}

	/**
	 * Called when the inventory is closed.
	 */
	@Override
	public void closeInventory()
	{
		setWornArmorItems();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		return false;
	}

	/**
	 * Counts the number of the specified item that is contained in the
	 * inventory.
	 * 
	 * @param item
	 *            The item whose ID will be counted.
	 * 
	 * @return The total number of the specified items that are in the
	 *         inventory.
	 */
	public int getQuantityOfItem(Item item)
	{
		int quantity = 0;

		for (final ItemStack stack : inventoryItems)
		{
			if (stack != null && stack.getItem() == item)
			{
				quantity += stack.stackSize;
			}
		}

		return quantity;
	}

	/**
	 * Counts the number of the specified item ID that is contained in the
	 * inventory.
	 * 
	 * @param itemId
	 *            The ID to be counted.
	 * 
	 * @return The total number of items with the specified ID that are in the
	 *         inventory.
	 */
	public int getQuantityOfItem(int itemId)
	{
		int quantity = 0;

		for (final ItemStack stack : inventoryItems)
		{
			if (stack != null && stack.getItem() == Item.getItemById(itemId))
			{
				quantity += stack.stackSize;
			}
		}

		return quantity;
	}

	/**
	 * Drops all items in the inventory.
	 */
	public void dropAllItems(boolean dropRandomly)
	{
		if (!owner.worldObj.isRemote)
		{
			for (int i = 0; i < inventoryItems.length; i++)
			{
				if (dropRandomly && LogicHelper.getBooleanWithProbability(25) || !dropRandomly)
				{
					final ItemStack stack = inventoryItems[i];

					if (stack != null)
					{
						owner.entityDropItem(stack, owner.worldObj.rand.nextFloat());
						setInventorySlotContents(i, null);
					}
				}
			}

			onInventoryChanged(this);
		}
	}

	/**
	 * Gets the entity's armor value from worn armor.
	 * 
	 * @return int expressing the armor value of the entity's worn armor.
	 */
	public int getTotalArmorValue()
	{
		int armorValue = 0;

		for (int slot = 0; slot < 4; slot++)
		{
			if (armorItemInSlot(slot) != null)
			{
				final ItemStack armorItem = armorItemInSlot(slot);
				armorValue += ((ItemArmor) armorItem.getItem()).damageReduceAmount;
			}
		}

		return armorValue;
	}

	/**
	 * Gets the armor item that is in the specified slot.
	 * 
	 * @param slotId
	 *            The armor slot that the item stack should be retrieved from.
	 * 
	 * @return The item stack contained in the specified armor slot.
	 */
	public ItemStack armorItemInSlot(int slotId)
	{
		try
		{
			if (slotId != -1 && armorItems[slotId] != null) { return inventoryItems[getFirstSlotContainingItem(armorItems[slotId].getItem())]; }
		}

		catch (final ArrayIndexOutOfBoundsException e)
		{
			return null;
		}

		return null;
	}

	/**
	 * Sets the best possible armor combination in the armor inventory.
	 */
	public void setWornArmorItems()
	{
		for (final ItemStack stack : inventoryItems)
		{
			if (stack != null)
			{
				if (stack.getItem() instanceof ItemArmor)
				{
					final ItemArmor itemAsArmor = (ItemArmor) stack.getItem();
					final int armorType = itemAsArmor.armorType;

					try
					{
						if (((ItemArmor) armorItems[armorType].getItem()).damageReduceAmount < itemAsArmor.damageReduceAmount)
						{
							armorItems[armorType] = stack;
						}
					}

					// Hit when there's nothing in that armor slot.
					catch (final NullPointerException e)
					{
						armorItems[armorType] = stack;
					}
				}
			}
		}

		// Check and be sure the inventory still contains each armor item in the
		// armor slots.
		// Set it to null if it is not contained in the main inventory.
		for (int i = 0; i < 4; i++)
		{
			if (armorItems[i] != null)
			{
				if (getQuantityOfItem(armorItems[i].getItem()) == 0)
				{
					armorItems[i] = null;
				}
			}
		}
	}

	/**
	 * Gets the best quality (max damage) item of the specified type that is in
	 * the inventory.
	 * 
	 * @param type
	 *            The class of item that will be returned.
	 * 
	 * @return The item stack containing the item of the specified type with the
	 *         highest max damage .
	 */
	public ItemStack getBestItemOfType(Class type)
	{
		ItemStack stack = null;
		int highestMaxDamage = 0;

		for (final ItemStack stackInInventory : inventoryItems)
		{
			if (stackInInventory != null)
			{
				final String itemClassName = stackInInventory.getItem().getClass().getName();

				if (itemClassName.equals(type.getName()) && highestMaxDamage < stackInInventory.getMaxDamage())
				{
					highestMaxDamage = stackInInventory.getMaxDamage();
					stack = stackInInventory;
				}
			}
		}

		return stack;
	}

	/**
	 * Stores the item stack in the inventory.
	 * 
	 * @param itemStack
	 *            The item stack that is to be stored in the inventory.
	 * 
	 * @return The slot ID that the item stack was put in to.
	 */
	private int storeItemStack(ItemStack itemStack)
	{
		for (int i = 0; i < inventoryItems.length; ++i)
		{
			if (inventoryItems[i] != null && inventoryItems[i] == itemStack && inventoryItems[i].isStackable() && inventoryItems[i].stackSize < inventoryItems[i].getMaxStackSize() && inventoryItems[i].stackSize < getInventoryStackLimit() && (!inventoryItems[i].getHasSubtypes() || inventoryItems[i].getItemDamage() == itemStack.getItemDamage()) && ItemStack.areItemStacksEqual(inventoryItems[i], itemStack)) { return i; }
		}

		return -1;
	}

	/**
	 * Gets the first empty slot in the inventory.
	 * 
	 * @return The slot ID of the first empty slot in the inventory.
	 */
	private int getFirstEmptyStack()
	{
		for (int i = 0; i < inventoryItems.length; i++)
		{
			if (inventoryItems[i] == null) { return i; }
		}

		return -1;
	}

	/**
	 * Stores a partial item stack in the inventory.
	 * 
	 * @param itemStack
	 *            The item stack that is to be placed in the inventory.
	 * 
	 * @return Any remainder left in the item stack.
	 */
	private int storePartialItemStack(ItemStack itemStack)
	{
		int stackSize = itemStack.stackSize;

		if (itemStack.getMaxStackSize() == 1)
		{
			final int slotId = getFirstEmptyStack();

			if (slotId < 0) { return stackSize; }

			if (inventoryItems[slotId] == null)
			{
				inventoryItems[slotId] = ItemStack.copyItemStack(itemStack);
			}

			return 0;
		}

		int slotId = storeItemStack(itemStack);

		if (slotId < 0)
		{
			slotId = getFirstEmptyStack();
		}

		if (slotId < 0) { return stackSize; }

		if (inventoryItems[slotId] == null)
		{
			inventoryItems[slotId] = new ItemStack(itemStack.getItem(), 0, itemStack.getItemDamage());

			if (itemStack.hasTagCompound())
			{
				inventoryItems[slotId].setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
			}
		}

		int itemStackSize = stackSize;

		if (itemStackSize > inventoryItems[slotId].getMaxStackSize() - inventoryItems[slotId].stackSize)
		{
			itemStackSize = inventoryItems[slotId].getMaxStackSize() - inventoryItems[slotId].stackSize;
		}

		if (itemStackSize > getInventoryStackLimit() - inventoryItems[slotId].stackSize)
		{
			itemStackSize = getInventoryStackLimit() - inventoryItems[slotId].stackSize;
		}

		if (itemStackSize == 0)
		{
			return stackSize;
		}

		else
		{
			stackSize -= itemStackSize;
			inventoryItems[slotId].stackSize += itemStackSize;
			inventoryItems[slotId].animationsToGo = 5;
			return stackSize;
		}
	}

	/**
	 * Combines two partial item stacks into one stack.
	 */
	private void combinePartialStacks()
	{
		for (int i = 0; i < inventoryItems.length; i++)
		{
			final ItemStack currentStack = inventoryItems[i];

			if (currentStack != null)
			{
				if (currentStack.stackSize != currentStack.getMaxStackSize())
				{
					for (int i2 = 0; i2 < inventoryItems.length; i2++)
					{
						final ItemStack searchingStack = inventoryItems[i2];

						if (searchingStack != null)
						{
							if (currentStack.getItem() == searchingStack.getItem() && i != i2)
							{
								if (currentStack.getItemDamage() == searchingStack.getItemDamage())
								{
									while (searchingStack.stackSize < searchingStack.getMaxStackSize())
									{
										currentStack.stackSize++;
										searchingStack.stackSize--;

										if (searchingStack.stackSize == 0)
										{
											inventoryItems[i2] = null;
											break;
										}
									}
								}
							}

							else
							{
								continue;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Adds a whole item stack to the inventory.
	 * 
	 * @param itemStack
	 *            The item stack to be added to the inventory.
	 * 
	 * @return True or false depending on if adding the stack to the inventory
	 *         was successful.
	 */
	public boolean addItemStackToInventory(ItemStack itemStack)
	{
		int slotId;

		if (itemStack.stackSize > 0)
		{
			if (itemStack.isItemDamaged())
			{
				slotId = getFirstEmptyStack();

				if (slotId >= 0)
				{
					inventoryItems[slotId] = ItemStack.copyItemStack(itemStack);
					inventoryItems[slotId].animationsToGo = 5;
					itemStack.stackSize = 0;

					combinePartialStacks();
					onInventoryChanged(this);
					return true;
				}

				else
				{
					combinePartialStacks();
					onInventoryChanged(this);
					return false;
				}
			}

			else
			{
				do
				{
					slotId = itemStack.stackSize;
					itemStack.stackSize = storePartialItemStack(itemStack);
				}
				while (itemStack.stackSize > 0 && itemStack.stackSize < slotId);

				combinePartialStacks();
				onInventoryChanged(this);
				return itemStack.stackSize < slotId;
			}
		}

		else
		{
			return false;
		}
	}

	/**
	 * Damages the armor in the armor inventory by the specified amount.
	 * 
	 * @param damageAmount
	 *            The amount that the armor should be damaged.
	 */
	public void damageArmor(int damageAmount)
	{
		int newDamageAmount = damageAmount /= 4;

		if (newDamageAmount < 1)
		{
			newDamageAmount = 1;
		}

		for (int i = 0; i < armorItems.length; i++)
		{
			if (!(armorItems[i].getItem() instanceof ItemArmor))
			{
				continue;
			}

			// Damage the armor and update the main inventory with the damaged
			// armor.
			armorItems[i].damageItem(newDamageAmount, owner);
			setInventorySlotContents(getFirstSlotContainingItem(armorItems[i].getItem()), armorItems[i]);

			if (armorItems[i].stackSize == 0)
			{
				setInventorySlotContents(getFirstSlotContainingItem(armorItems[i].getItem()), null);
				armorItems[i] = null;

				// Get the next armor item to go in the slot, if there is one.
				setWornArmorItems();
			}
		}
	}

	/**
	 * Gets how much an entity should be damaged.
	 * 
	 * @param target
	 *            The entity being damaged.
	 * 
	 * @return Integer amount that is the amount in half hearts that an entity
	 *         should be damaged.
	 */
	public int getDamageVsEntity(EntityLivingBase target)
	{
		if (owner.getHeldItem() == null) { return 1; }

		return getDamageByHeldItemType(owner.getHeldItem());
	}

	/**
	 * Gets the ID of the first slot that contains the specified item.
	 * 
	 * @param item
	 *            The item that must be in the slot.
	 * 
	 * @return The ID of the slot containing the specified item. -1 if the item
	 *         is not in any slot.
	 */
	public int getFirstSlotContainingItem(Item item)
	{
		int slot = 0;

		for (final ItemStack stack : inventoryItems)
		{
			if (stack != null && stack.getItem() == item) { return slot; }

			slot++;
		}

		return -1;
	}

	/**
	 * Gets the ID of the first slot that contains the specified item ID.
	 * 
	 * @param itemId
	 *            The item ID that must be in the slot.
	 * 
	 * @return The ID of the slot containing the specified item. -1 if the item
	 *         is not in any slot.
	 */
	public int getFirstSlotContainingItem(int itemId)
	{
		int slot = 0;

		for (final ItemStack stack : inventoryItems)
		{
			if (stack != null && stack.getItem() == Item.getItemById(itemId)) { return slot; }

			slot++;
		}

		return -1;
	}

	/**
	 * Gets the ID of the first slot the contains a food item.
	 * 
	 * @return The ID of the first slot containing a food item.
	 */
	public int getFirstSlotContainingFood()
	{
		int slot = 0;

		for (final ItemStack stack : inventoryItems)
		{
			if (stack != null && stack.getItem() instanceof ItemFood) { return slot; }

			slot++;
		}

		return -1;
	}

	/**
	 * Determines if this inventory contains the specified item.
	 * 
	 * @param item
	 *            The item that should be contained in the inventory.
	 * 
	 * @return True or false depending on if the item provided is in the
	 *         inventory.
	 */
	public boolean contains(Item item)
	{
		for (final ItemStack stack : inventoryItems)
		{
			if (stack != null && stack.getItem() == item) { return true; }
		}

		return false;
	}

	/**
	 * Determines if this inventory contains the specified block.
	 * 
	 * @param block
	 *            The block that should be contained in the inventory.
	 * 
	 * @return True or false depending on if the block provided is in the
	 *         inventory.
	 */
	public boolean contains(Block block)
	{
		for (final ItemStack stack : inventoryItems)
		{
			if (stack != null && stack.getItem() == Item.getItemFromBlock(block)) { return true; }
		}

		return false;
	}

	/**
	 * Writes the owner's inventory to NBT.
	 * 
	 * @param nbt
	 *            The instance of the NBTTagCompound used to write info to NBT.
	 */
	public void writeInventoryToNBT(NBTTagCompound nbt)
	{
		// Write the main inventory to NBT.
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inventoryItems.length; i++)
		{
			if (inventoryItems[i] != null)
			{
				final NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) i);
				inventoryItems[i].writeToNBT(tagCompound);
				nbttaglist.appendTag(tagCompound);
			}
		}

		nbt.setTag("Items", nbttaglist);

		// Write the armor inventory to NBT.
		nbttaglist = new NBTTagList();

		for (int i = 0; i < armorItems.length; i++)
		{
			if (armorItems[i] != null)
			{
				final NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) i);
				armorItems[i].writeToNBT(tagCompound);
				nbttaglist.appendTag(tagCompound);
			}
		}

		nbt.setTag("Armor", nbttaglist);
	}

	/**
	 * Reads the owner's inventory from NBT.
	 * 
	 * @param nbt
	 *            The instance of the NBTTagCompound used to read info from NBT.
	 */
	public void readInventoryFromNBT(NBTTagCompound nbt)
	{
		NBTTagList tagList = nbt.getTagList("Items", 10);
		inventoryItems = new ItemStack[getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			final NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
			final int slotId = nbttagcompound.getByte("Slot") & 0xff;

			if (slotId >= 0 && slotId < inventoryItems.length)
			{
				inventoryItems[slotId] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		tagList = nbt.getTagList("Armor", 10);
		armorItems = new ItemStack[4];

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			final NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
			final int armorSlotId = nbttagcompound.getByte("Slot") & 0xff;

			if (armorSlotId >= 0 && armorSlotId < armorItems.length)
			{
				armorItems[armorSlotId] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		setWornArmorItems();
	}

	/**
	 * Writes this object to an object output stream. (Serialization)
	 * 
	 * @param outStream
	 *            The object output stream that this object should be written
	 *            to.
	 * 
	 * @throws IOException
	 *             This exception should never happen.
	 */
	private void writeObject(ObjectOutputStream outStream) throws IOException
	{
		for (int i = 0; i < getSizeInventory(); i++)
		{
			try
			{
				final ItemStack stack = inventoryItems[i];

				String writeString = i + ":" + Item.getIdFromItem(stack.getItem()) + ":" + stack.stackSize + ":" + stack.getItemDamage();

				if (stack.getItem() instanceof ItemArmor)
				{
					final ItemArmor armor = (ItemArmor) stack.getItem();
					writeString += ":" + armor.getColor(stack);
				}

				else
				{
					writeString += ":0";
				}

				outStream.writeObject(writeString);
			}

			catch (final NullPointerException e)
			{
				outStream.writeObject(i + ":" + "null");
			}

			catch (final ArrayIndexOutOfBoundsException e)
			{
				outStream.writeObject(i + ":" + "null");
			}
		}
	}

	/**
	 * Reads this object from an object input stream. (Deserialization)
	 * 
	 * @param inStream
	 *            The object input stream that this object should be read from.
	 * 
	 * @throws IOException
	 *             This exception should never happen.
	 * @throws ClassNotFoundException
	 *             This exception should never happen.
	 */
	private void readObject(ObjectInputStream inStream) throws IOException, ClassNotFoundException
	{
		inventoryItems = new ItemStack[getSizeInventory()];
		armorItems = new ItemStack[4];

		for (int i = 0; i < getSizeInventory(); i++)
		{
			try
			{
				final String data = inStream.readObject().toString();

				if (data.contains("null"))
				{
					inventoryItems[i] = null;
				}

				else
				{
					final int itemID = Integer.parseInt(data.split(":")[1]);
					final int stackSize = Integer.parseInt(data.split(":")[2]);
					final int damage = Integer.parseInt(data.split(":")[3]);
					final int color = Integer.parseInt(data.split(":")[4]);

					final ItemStack inventoryStack = new ItemStack(Item.getItemById(itemID), stackSize, damage);

					if (inventoryStack.getItem() instanceof ItemArmor)
					{
						final ItemArmor armor = (ItemArmor) inventoryStack.getItem();

						if (armor.getArmorMaterial() == ArmorMaterial.CLOTH)
						{
							armor.func_82813_b(inventoryStack, color);
						}
					}

					inventoryItems[i] = inventoryStack;
				}
			}

			catch (final Throwable e)
			{
				continue;
			}
		}
	}

	private int getDamageByHeldItemType(ItemStack stack)
	{
		if (stack.getItem() instanceof ItemSword)
		{
			final ToolMaterial material = ToolMaterial.valueOf(((ItemSword) stack.getItem()).getToolMaterialName());

			switch (material)
			{
				case WOOD:
					return 4;
				case STONE:
					return 5;
				case IRON:
					return 6;
				case GOLD:
					return 4;
				case EMERALD:
					return 7;
				default:
					return 5;
			}
		}

		else if (stack.getItem() instanceof ItemBow)
		{
			return 9;
		}

		else
		{
			return 1;
		}
	}

	@Override
	public void onInventoryChanged(InventoryBasic var1)
	{
		// Nothing to do here.
	}

	public static void populateWithRandomEquipment(Inventory inventory)
	{
		final boolean giveBow = LogicHelper.getBooleanWithProbability(30);
		final boolean giveSword = LogicHelper.getBooleanWithProbability(60);
		final boolean giveHelmet = LogicHelper.getBooleanWithProbability(65);
		final boolean giveChestplate = LogicHelper.getBooleanWithProbability(30);
		final boolean giveLeggings = LogicHelper.getBooleanWithProbability(40);
		final boolean giveBoots = LogicHelper.getBooleanWithProbability(70);
		final int armorLevel = LogicHelper.getNumberInRange(1, 4);
		final int weaponLevel = LogicHelper.getNumberInRange(1, 4);

		if (giveBow)
		{
			inventory.addItemStackToInventory(new ItemStack(Items.bow, 1));
			inventory.addItemStackToInventory(new ItemStack(Items.arrow, LogicHelper.getNumberInRange(4, 16)));
		}

		if (giveSword)
		{
			switch (weaponLevel)
			{
				case 1:
					inventory.addItemStackToInventory(new ItemStack(Items.wooden_sword, 1));
					break;
				case 2:
					inventory.addItemStackToInventory(new ItemStack(Items.stone_sword, 1));
					break;
				case 3:
					inventory.addItemStackToInventory(new ItemStack(Items.iron_sword, 1));
					break;
				case 4:
					inventory.addItemStackToInventory(new ItemStack(Items.diamond_sword, 1));
					break;
			}
		}

		if (giveHelmet)
		{
			switch (armorLevel)
			{
				case 1:
					inventory.addItemStackToInventory(new ItemStack(Items.leather_helmet, 1));
					break;
				case 2:
					inventory.addItemStackToInventory(new ItemStack(Items.golden_helmet, 1));
					break;
				case 3:
					inventory.addItemStackToInventory(new ItemStack(Items.iron_helmet, 1));
					break;
				case 4:
					inventory.addItemStackToInventory(new ItemStack(Items.diamond_helmet, 1));
					break;
			}
		}

		if (giveChestplate)
		{
			switch (armorLevel)
			{
				case 1:
					inventory.addItemStackToInventory(new ItemStack(Items.leather_chestplate, 1));
					break;
				case 2:
					inventory.addItemStackToInventory(new ItemStack(Items.golden_chestplate, 1));
					break;
				case 3:
					inventory.addItemStackToInventory(new ItemStack(Items.iron_chestplate, 1));
					break;
				case 4:
					inventory.addItemStackToInventory(new ItemStack(Items.diamond_chestplate, 1));
					break;
			}
		}

		if (giveLeggings)
		{
			switch (armorLevel)
			{
				case 1:
					inventory.addItemStackToInventory(new ItemStack(Items.leather_leggings, 1));
					break;
				case 2:
					inventory.addItemStackToInventory(new ItemStack(Items.golden_leggings, 1));
					break;
				case 3:
					inventory.addItemStackToInventory(new ItemStack(Items.iron_leggings, 1));
					break;
				case 4:
					inventory.addItemStackToInventory(new ItemStack(Items.diamond_leggings, 1));
					break;
			}
		}

		if (giveBoots)
		{
			switch (armorLevel)
			{
				case 1:
					inventory.addItemStackToInventory(new ItemStack(Items.leather_boots, 1));
					break;
				case 2:
					inventory.addItemStackToInventory(new ItemStack(Items.golden_boots, 1));
					break;
				case 3:
					inventory.addItemStackToInventory(new ItemStack(Items.iron_boots, 1));
					break;
				case 4:
					inventory.addItemStackToInventory(new ItemStack(Items.diamond_boots, 1));
					break;
			}
		}

		inventory.setWornArmorItems();
	}

	public boolean isEmpty()
	{
		for (int i = 0; i < getSizeInventory(); i++)
		{
			if (inventoryItems[i] != null) { return false; }
		}

		return true;
	}
}
