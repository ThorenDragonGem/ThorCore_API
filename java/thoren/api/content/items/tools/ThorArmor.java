package thoren.api.content.items.tools;

import java.util.HashMap;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;
import thoren.api.helpers.game.RenderTypes;

import com.google.common.collect.Lists;

/**
 * @author ThorenDragonGem
 */
public class ThorArmor extends ItemArmor
{
	private final ArmorMaterial material;
	private boolean hasToolTip = false;
	private static HashMap<String, List<ThorArmor>> armorWithModIdMap = new HashMap<String, List<ThorArmor>>();
	private static int renderer;
	private int slotnumber;
	private List<String> toolTipStrings = Lists.newArrayList();
	private String modId, texturePath, type;
	private ItemStack repairMaterial;
	
	/**
	 * Creates a ThorArmor.
	 * @param material the armor material of the item. 
	 * @param slotnumber the slot number of the armor (0 - 3).
	 */
	public ThorArmor(ArmorMaterial material, int slotnumber) 
	{
		super(material, renderer, slotnumber);
		this.material = material;
		this.slotnumber = slotnumber;
	}
	
	/**
	 * Sets the name of the armor.
	 * @param unlocalizedName the name of the armor.
	 * @return ThorArmor
	 */
	public ThorArmor setItemName(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		GameRegistry.registerItem(this, unlocalizedName);
		ContentRegistry.registerItem(this, unlocalizedName, modId, ContentTypes.Item.ARMOR);
		return this;
	}
	
	/**
	 * Adds a tool tip on the armor.
	 * @param toolTip the String displayed on the armor piece.
	 * @return ThorArmor
	 */
	public ThorArmor addToolTip(String toolTip)
	{
		this.toolTipStrings.add(toolTip);
		this.hasToolTip = true;
		return this;
	}
	
	/**
	 * Sets the repair material of the item.
	 * @param repairMaterial the repair material of the item.
	 * @return ThorArmor
	 */
	public ThorArmor setRepairMaterial(ItemStack repairMaterial)
	{
		this.canRepair = true;
		this.repairMaterial = repairMaterial;
		return this;
	}
	
	/**
	 * Sets the modId of the item.
	 * @param modId th emodId of the item.
	 * @return ThorArmor
	 */
	public ThorArmor modId(String modId)
	{
		List<ThorArmor> list = Lists.newArrayList();
		list.add(this);
		this.modId = modId;
		if(this.armorWithModIdMap.containsKey(modId))
			this.armorWithModIdMap.get(modId).add(this);
		else
			this.armorWithModIdMap.put(modId, list);
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the item will be displayed.
	 * @param creativeTab the CreativeTabs.
	 * @return ThorArmor
	 */
	public ThorArmor setTab(CreativeTabs creativeTab)
	{
		this.setCreativeTab(creativeTab);
		return this;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		if(hasToolTip)
			for(String toolTip : this.toolTipStrings)
				list.add(StatCollector.translateToLocal(toolTip));
	}
	
	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return this.repairMaterial.getItem() == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
}