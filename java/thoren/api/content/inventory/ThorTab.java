package thoren.api.content.inventory;

import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author ThorenDragonGem
 */
public class ThorTab extends CreativeTabs
{
	private ItemStack stack;
	
	/**
	 * Creates a ThorTab.
	 * @param label the name of the custom tab.
	 * @param icon the icon of the tab (must be an item).
	 */
	public ThorTab(String label, ItemStack icon)
	{
		super(label);
		ContentRegistry.registerCreativeTab(this, label, ContentTypes.CreativeTab.GENERAL);
		this.stack = icon;
	}
	
	/**
	 * Sets the background picture of the tab
	 * @param bckName the picture file name.
	 * @return ThorTab
	 */
	public ThorTab setBackgroundImage(String bckName)
	{
		this.setBackgroundImageName(bckName + ".png");
		return this;
	}
	
	@Override
	public Item getTabIconItem() {
		return stack.getItem();
	}
}