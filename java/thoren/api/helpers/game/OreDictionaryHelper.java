package thoren.api.helpers.game;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * @author ThorenDragonGem
 */
public class OreDictionaryHelper
{
	/**
	 * Adds an ore dictionary.
	 * @param name the name of the entry in the dictionary.
	 * @param stack the stack to enter in the dictionary.
	 */
	public static void addOre(String name, ItemStack stack)
	{
		OreDictionary.registerOre(name, stack);
	}
}