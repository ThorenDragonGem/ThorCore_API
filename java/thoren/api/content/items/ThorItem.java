package thoren.api.content.items;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thoren.api.content.items.tools.ThorPaxadoe;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;
import thoren.api.helpers.game.RenderTypes;

/**
 * @author ThorenDragonGem
 */
public class ThorItem extends Item
{
	private static String modId = "";
	private static HashMap<String, List<ThorItem>> itemWithmodIdMap = new HashMap<String, List<ThorItem>>();
	private static boolean isIngot = false;

	/**
	 * Creates a ThorItem.
	 */
	public ThorItem()
	{
		super();
	}
	
	/**
	 * Sets the name to the item.
	 * @param name the name of the item.
	 * @return ThorItem
	 */
	public ThorItem setItemName(String name)
	{
		this.setUnlocalizedName(name);
		GameRegistry.registerItem(this, name);
		ContentRegistry.registerItem(this, name, modId, isIngot ? ContentTypes.Item.INGOT : ContentTypes.Item.OTHER);
		return this;
	}
	
	/**
	 * Sets the modId of the item.
	 * @param modId the modId of the item.
	 * @return ThorItem
	 */
	public ThorItem modId(String modId)
	{
		List<ThorItem> list = Lists.newArrayList();
		list.add(this);
		this.modId = modId;
		if(this.itemWithmodIdMap.containsKey(modId))
			this.itemWithmodIdMap.get(modId).add(this);
		else
			this.itemWithmodIdMap.put(modId, list);
		return this;
	}
	
	/**
	 * Sets the item as an ingot.
	 * @return ThorItem
	 */
	public ThorItem isIngot()
	{
		this.isIngot = true;
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the item will be displayed.
	 * @param tab the CreativeTabs.
	 * @return ThorItem.
	 */
	public ThorItem setTab(CreativeTabs tab)
	{
		this.setCreativeTab(tab);
		return this;
	}
}