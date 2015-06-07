package thoren.api.helpers.game;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

/**
 * @author ThorenDragonGem
 */
public class ChestEventHelper
{
	/**
	 * Adds chest loots in Vanilla Structures.
	 * @param category the structure where the chest is. See String final variables in ChestGenHooks
	 * @param stack the stack to be looted in chests.
	 * @param amount the number of item of the stack looted.
	 * @param minChance the minimum of the chance that the loot appears in the chest.
	 * @param maxChance the maximum of the chance that the loot appears in the chest.
	 * @param weight how often the item is chosen (higher number is higher chance, and lower number is lower chance))
	 */
	public static void addChest(String category, ItemStack stack, int amount, int minChance, int maxChance, int weight)
	{
		ChestGenHooks.addItem(category, new WeightedRandomChestContent(stack.getItem(), amount, minChance, maxChance, weight));
	}
	
	/**
	 * Adds chest loots in Dungeon Structures.
	 * @param stack the stack to be looted in dungeon chests.
	 * @param amount the number of item of the stack looted.
	 * @param minChance the minimum of the chance that the loot appears in the chest.
	 * @param maxChance the maximum of the chance that the loot appears in the chest.
	 * @param weight how often the item is chosen (higher number is higher chance, and lower number is lower chance))
	 */
	public static void addDungeonChest(ItemStack stack, int amount, int minChance, int maxChance, int weight)
	{
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(stack.getItem(), amount, minChance, maxChance, weight));
	}
}