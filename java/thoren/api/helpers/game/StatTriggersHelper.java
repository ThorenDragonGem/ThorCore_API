package thoren.api.helpers.game;

import java.util.HashMap;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

/**
 * @author ThorenDragonGem
 */
public class StatTriggersHelper
{
	public static final StatTriggersHelper INSTANCE = new StatTriggersHelper();
	
	private static HashMap<Item, Achievement> craftingAchievs = new HashMap<Item, Achievement>();
	private static HashMap<Item, Achievement> smeltingAchievs = new HashMap<Item, Achievement>();
	private static HashMap<Item, Achievement> pickupAchievs = new HashMap<Item, Achievement>();

	/**
	 * Adds a crafting achievement.
	 * @param stack the stack to craft to trigger.
	 * @param achievToTrigger the Achievement.
	 */
	public static void addCraftingTrigger(ItemStack stack, Achievement achievToTrigger)
	{
		craftingAchievs.put(stack.getItem(), achievToTrigger);
	}

	/**
	 * Adds a smelting achievement.
	 * @param stack the stack to smelt to trigger.
	 * @param achievToTrigger the Achievement.
	 */
	public static void addSmeltingTrigger(ItemStack stack, Achievement achievToTrigger)
	{
		smeltingAchievs.put(stack.getItem(), achievToTrigger);
	}
	
	/**
	 * Adds a pickup achievement.
	 * @param stack the stack to pick up to trigger.
	 * @param achievToTrigger the Achievement.
	 */
	public static void addPickupTrigger(ItemStack stack, Achievement achievToTrigger)
	{
		pickupAchievs.put(stack.getItem(), achievToTrigger);
	}

	/**
	 * Notifies the Achievement to the Player.
	 * @param player the Player who has the achievement.
	 * @param stack the stack crafted to have this achievement.
	 * @param craftMatrix the Matrix of the workbench.
	 */
	public static void notifyCrafting(EntityPlayer player, ItemStack stack, IInventory craftMatrix)
	{
		if(craftingAchievs.containsKey(stack.getItem()))
		{
			player.addStat(craftingAchievs.get(stack.getItem()), 1);
		}
	}
	
	/**
	 * Notifies the Achievement to the Player.
	 * @param player the Player who has the achievement.
	 * @param stack the stack smelted to have this achievement.
	 */
	public static void notifySmelting(EntityPlayer player, ItemStack stack)
	{
		if(craftingAchievs.containsKey(stack.getItem()))
		{
			player.addStat(craftingAchievs.get(stack.getItem()), 1);
		}
	}

	/**
	 * Notifies the Achievement to the Player.
	 * @param entityItem the EntityItem picked up by the Player.
	 * @param player the Player who has this achievement.
	 */
	public void notifyPickup(EntityItem entityItem, EntityPlayer player)
	{
		if(pickupAchievs.containsKey(entityItem.getEntityItem().getItem()))
		{
			player.addStat(pickupAchievs.get(entityItem.getEntityItem().getItem()), 1);
		}
	}
}