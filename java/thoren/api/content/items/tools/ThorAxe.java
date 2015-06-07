package thoren.api.content.items.tools;

import java.util.HashMap;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

import com.google.common.collect.Lists;

/**
 * @author ThorenDragonGem
 */
public class ThorAxe extends ItemAxe
{
	private final ToolMaterial toolMaterial;
	private boolean hasToolTip = false;
	private boolean hasEffect = false;
	private Object effect[] = new Object[2];
	private static HashMap<String, List<ThorAxe>> axeWithmodIdMap = new HashMap<String, List<ThorAxe>>();
	private List<String> toolTipStrings = Lists.newArrayList();
	private String modId;
	private ItemStack repairMaterial;
	
	/**
	 * Creates a ThorAxe.
	 * @param toolMaterial the tool material of the item.
	 */
	public ThorAxe(ToolMaterial toolMaterial) 
	{
		super(toolMaterial);
		this.toolMaterial = toolMaterial;
	}

	/**
	 * Sets the name of the tool.
	 * @param name the name of the tool.
	 * @return ThorAxe
	 */
	public ThorAxe setItemName(String name)
	{
		this.setUnlocalizedName(name);
		GameRegistry.registerItem(this, name);
		ContentRegistry.registerItem(this, name, modId, ContentTypes.Item.TOOL);
		return this;
	}
	
	/**
	 * Sets the repair material of the tool.
	 * @param repairMaterial the repair material.
	 * @return ThorAxe
	 */
	public ThorAxe setRepairMaterial(ItemStack repairMaterial)
	{
		this.canRepair = true;
		this.repairMaterial = repairMaterial;
		return this;
	}

	/**
	 * Adds a tool tip on the tool.
	 * @param toolTip the String displayed on the tool.
	 * @return ThorAxe
	 */
	public ThorAxe addToolTip(String toolTip)
	{
		this.toolTipStrings.add(toolTip);
		this.hasToolTip = true;
		return this;
	}

	/**
	 * Sets the harvest level of the tool (default: axe).
	 * @param level the level of the tool.
	 * @return ThorAxe
	 */
	public ThorAxe setToolHarvestLevel(int level)
	{
		this.setHarvestLevel("axe", level);
		return this;
	}

	/**
	 * Sets the modId of the tool.
	 * @param modId the modId of the tool.
	 * @return ThorAxe
	 */
	public ThorAxe modId(String modId)
	{
		List<ThorAxe> list = Lists.newArrayList();
		list.add(this);
		this.modId = modId;
		if(this.axeWithmodIdMap.containsKey(modId))
			this.axeWithmodIdMap.get(modId).add(this);
		else
			this.axeWithmodIdMap.put(modId, list);
		return this;
	}
	
	/**
	 * Adds an enchantment to the tool.
	 * @param enchantment the enchantment id.
	 * @param level the level of the enchantment.
	 * @return ThorAxe
	 */
	public ThorAxe setEffect(Enchantment enchantment, int level)
	{
		this.hasEffect = true;
		this.effect[0] = enchantment;
		this.effect[1] = level;
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the item will be displayed.
	 * @param creativeTab the CreativeTabs.
	 * @return ThorAxe
	 */
	public ThorAxe setTab(CreativeTabs creativeTab)
	{
		this.setCreativeTab(creativeTab);
		return this;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer entityPlayer, List list, boolean bool)
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
	
	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player)
	{
		if(this.hasEffect)
			itemstack.addEnchantment((Enchantment)this.effect[0], (Integer)this.effect[1]);
	}
}