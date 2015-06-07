package thoren.api.helpers.game;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author ThorenDragonGem
 */
public class GrassHelper
{
	/**
	 * Adds a new item to be dropped when breaking tall grass.
	 * @param stack the stack dropped.
	 * @param rarity the rarity of the drop (0 -> 100 ; wheat: 10).
	 */
	public static void addGrass(ItemStack stack, int rarity)
	{
		MinecraftForge.addGrassSeed(stack, rarity);
	}
}
