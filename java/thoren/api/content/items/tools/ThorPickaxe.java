package thoren.api.content.items.tools;

import java.util.HashMap;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
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
public class ThorPickaxe extends ItemPickaxe
{
	private final ToolMaterial toolMaterial;
	private boolean hasToolTip = false;
	private boolean hasEffect = false;
	private Object effect[] = new Object[2];
	private static HashMap<String, List<ThorPickaxe>> pickaxeWithModIdMap = new HashMap<String, List<ThorPickaxe>>();
	private List<String> toolTipStrings = Lists.newArrayList();
	private String modId;
	private ItemStack repairMaterial;

	/**
	 * Creates a ThorPickaxe.
	 * @param toolMaterial the tool material of the item.
	 */
	public ThorPickaxe(ToolMaterial toolMaterial) 
	{
		super(toolMaterial);
		this.toolMaterial = toolMaterial;
		this.setHarvestLevel("pickaxe", toolMaterial.getHarvestLevel());
	}
	
	/**
	 * Sets the name of the tool.
	 * @param unlocalizedName the name of the tool.
	 * @return ThorPickaxe
	 */
	public ThorPickaxe setItemName(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		GameRegistry.registerItem(this, unlocalizedName);
		ContentRegistry.registerItem(this, unlocalizedName, modId, ContentTypes.Item.TOOL);
		return this;
	}
	
	/**
	 * Sets the repair material of the tool.
	 * @param repairMaterial the repair material of the tool.
	 * @return ThorPickaxe
	 */
	public ThorPickaxe setRepairMaterial(ItemStack repairMaterial)
	{
		this.canRepair = true;
		this.repairMaterial = repairMaterial;
		return this;
	}

	/**
	 * Adds a tool tip on the tool.
	 * @param toolTip the String displayed on the tool.
	 * @return ThorPickaxe
	 */
	public ThorPickaxe addToolTip(String toolTip)
	{
		this.toolTipStrings.add(toolTip);
		this.hasToolTip = true;
		return this;
	}

	/**
	 * Sets the modId of the item.
	 * @param modId the modId of the item.
	 * @return ThorPickaxe
	 */
	public ThorPickaxe modId(String modId)
	{
		List<ThorPickaxe> list = Lists.newArrayList();
		list.add(this);
		this.modId = modId;
		if(this.pickaxeWithModIdMap.containsKey(modId))
			this.pickaxeWithModIdMap.get(modId).add(this);
		else
			this.pickaxeWithModIdMap.put(modId, list);
		return this;
	}
	
	/**
	 * Adds an enchantment to the tool.
	 * @param enchantment the enchantment id.
	 * @param level the enchantment level.
	 * @return ThorPickaxe
	 */
	public ThorPickaxe setEffect(Enchantment enchantment, int level)
	{
		this.hasEffect = true;
		this.effect[0] = enchantment;
		this.effect[1] = level;
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the item will be displayed.
	 * @param creativeTab the CreativeTabs.
	 * @return ThorPickaxe
	 */
	public ThorPickaxe setTab(CreativeTabs creativeTab)
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
	
	public static List<ThorPickaxe> getItemListFromModId(String modId)
	{
		if(pickaxeWithModIdMap.containsKey(modId))
			return pickaxeWithModIdMap.get(modId);
		else
			return Lists.newArrayList();
	}
	
	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player)
	{
		if(this.hasEffect)
			itemstack.addEnchantment((Enchantment)this.effect[0], (Integer)this.effect[1]);
	}
}