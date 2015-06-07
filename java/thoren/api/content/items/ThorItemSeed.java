package thoren.api.content.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

/**
 * @author ThorenDragonGem
 */
public class ThorItemSeed extends ItemSeeds
{
	private static String modId = "";
	
	/**
	 * Creates a ThorItemSeed.
	 * @param crops the block representing the seed outwards the inventory.
	 * @param soil the block where the seed can be planted.
	 */
	public ThorItemSeed(Block crops, Block soil)
	{
		super(crops, soil);
	}
	
	/**
	 * Sets the name of the item.
	 * @param unlocalizedName the name of the item.
	 * @return ThorItemSeed
	 */
	public ThorItemSeed setItemName(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		GameRegistry.registerItem(this, unlocalizedName);
		ContentRegistry.registerItem(this, unlocalizedName, modId, ContentTypes.Item.SEED);
		return this;
	}
	
	/**
	 * Sets the modId of the item.
	 * @param modId the modId of the item.
	 * @return ThorItemSeed
	 */
	public ThorItemSeed modId(String modId)
	{
		this.modId = modId;
		return this;
	}

	/**
	 * Sets the CreativeTab where the item will be displayed.
	 * @param tab the CreativeTabs.
	 * @return ThorItemSeed
	 */
	public ThorItemSeed setTab(CreativeTabs tab)
	{
		this.setCreativeTab(tab);
		return this;
	}
}