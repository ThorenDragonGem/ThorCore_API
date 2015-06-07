package thoren.api.helpers.game;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import thoren.api.core.ThorInfo;

/**
 * @author ThorenDragonGem
 */
public class ObjectRendererHelper
{
	/**
	 * Renders a Block with meta-data 
	 * @param block the Block to be rendered.
	 * @param metadata the meta-data of the Block.
	 */
 	public ObjectRendererHelper(Block block, int metadata)
	{
		RenderBlock(block, metadata);
	}
	
 	/**
 	 * Renders an Item with meta-data.
 	 * @param item the Item to be rendered.
 	 * @param metadata the meta-data of the Item.
 	 */
	public ObjectRendererHelper(Item item, int metadata)
	{
		RenderItem(item, metadata);
	}
	
	/**
	 * Renders a Block with the Minecraft functions.
	 * @param block the Block to be rendered.
	 * @param metadata the meta-data of the Block.
	 */
	public static void RenderBlock(Block block, int metadata)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(ThorInfo.ID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
	
	/**
	 * Renders an Item with the Minecraft functions.
	 * @param item the Item to be rendered.
	 * @param metadata the meta-data of the Item.
	 */
	public static void RenderItem(Item item, int metadata)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, metadata, new ModelResourceLocation(ThorInfo.ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
	
	/**
	 * @deprecated WIP
	 */
	public static void Renderer3DItem(Item item, int metadata)
	{
		/** TODO: Make 3DItem renderer API */
	}
}
