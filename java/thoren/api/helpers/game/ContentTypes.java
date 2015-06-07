package thoren.api.helpers.game;

import net.minecraft.creativetab.CreativeTabs;

/**
 * @author ThorenDragonGem
 * Enum to arrange Items and Blocks in Categories.
 */
public class ContentTypes 
{
	public enum Block
	{
		GENERAL, ORE, FLOWER, CROP, MACHINE, DECORATIVE, OTHER;
	}
	
	public enum Item
	{
		INGOT, FOOD, SEED, TOOL, ARMOR, WEAPON, OTHER;
	}
	
	public enum CreativeTab
	{
		GENERAL(CreativeTabs.tabBlock),
		BLOCKS(CreativeTabs.tabBlock), 
		MATERIALS(CreativeTabs.tabMaterials), 
		FOOD(CreativeTabs.tabFood),
		DECORATIONS(CreativeTabs.tabDecorations), 
		TOOLS(CreativeTabs.tabTools), 
		COMBAT(CreativeTabs.tabCombat), 
		OTHER(CreativeTabs.tabMisc);
		
		public final CreativeTabs vanillaTab;
		
		private CreativeTab(CreativeTabs vanillaTab)
		{
			this.vanillaTab = vanillaTab;
		}
	}
}
