package thoren.api.content.inventory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.xml.internal.ws.api.pipe.NextAction;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author ThorenDragonGem
 * Adds recipes to the game. 
 */
public class ThorCrafting
{
	/**
	 * Adds a basic recipe.
	 * @param output the expelled item stack.
	 * @param params the parameters of the recipe.
	 */
	public static void addRecipe(ItemStack output, Object... params)
	{
		GameRegistry.addRecipe(output, params);
	}

	/**
	 * Adds a shaped recipe.
	 * @param output the expelled item stack.
	 * @param params the parameters of the recipe.
	 */
	public static void addShapedRecipe(ItemStack output, Object... params)
	{
		GameRegistry.addShapedRecipe(output, params);
	}

	/**
	 * Adds a shapeless recipe.
	 * @param output the expelled item stack.
	 * @param params the parameters of the recipe.
	 */
	public static void addShapelessRecipe(ItemStack output, Object... params)
	{
		GameRegistry.addShapelessRecipe(output, params);
	}
	
	/**
	 * Adds a smelting recipe.
	 * @param input the input of the recipe.
	 * @param output the output of the recipe.
	 * @param xp the experience amount dropped for each item smelted. 
	 */
	public static void addSmeltingRecipe(ItemStack input, ItemStack output, float xp)
	{
		GameRegistry.addSmelting(input, output, xp);
	}
	
	/**
	 * Remove item consumption in workbench (e.g buckets).
	 * @param item the item not consumed.
	 */
	public static void removeConsumptingItem(Item item)
	{
		item.setContainerItem(item);
	}
	
	/**
	 * Remove a crafting recipe.
	 * @param stack the output stack to remove from recipes.
	 */
	public static void removeCraftingRecipe(ItemStack stack)
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		
		Iterator<IRecipe> remover = recipes.iterator();
		
			while(remover.hasNext())
			{
				ItemStack itemstack = remover.next().getRecipeOutput();
				if(itemstack != null && itemstack.getItem() == stack.getItem())
				{
					remover.remove();
				}
							
		}
	}
	
	/**
	 * Remove a smelting recipe.
	 * @param stack the output stack to remove from the recipe.
	 */
	public static void removeFurnaceRecipe(ItemStack stack)
	{
		Map recipes = FurnaceRecipes.instance().getSmeltingList();
		
		Iterator entries = recipes.entrySet().iterator();
		while (entries.hasNext())
		{
			Entry thisEntry = (Entry) entries.next();
			if (thisEntry != null && thisEntry.getKey() == stack)
		  	{
				entries.remove();
		  	}
		}
	}
}