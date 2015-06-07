package thoren.api.content.items.tools;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
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
public class ThorShears extends ItemShears
{
	private boolean hasToolTip = false;
	private boolean hasEffect  = false;
	private Object effect[] = new Object[2];
	private ItemStack repairMaterial;
	private List<String> toolTipStrings = Lists.newArrayList();
	private String modId;
	
	/**
	 * Creates ThorShears
	 * @param maxDamage the durability of the item.
	 */
	public ThorShears(int maxDamage)
	{
		super();
		this.setMaxStackSize(1);
		this.setMaxDamage(maxDamage);
		this.repairMaterial = new ItemStack(this);
	}
	
	/**
	 * Sets the name of the item.
	 * @param unlocalizedName the name of the item.
	 * @return ThorShears
	 */
	public ThorShears setItemName(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		GameRegistry.registerItem(this, unlocalizedName);
		ContentRegistry.registerItem(this, unlocalizedName, modId, ContentTypes.Item.TOOL);
		return this;
	}

	/**
	 * Adds a tool tip on the tool.
	 * @param toolTip the String displayed on the tool.
	 * @return ThorShears
	 */
	public ThorShears addToolTip(String toolTip)
	{
		this.toolTipStrings.add(toolTip);
		this.hasToolTip = true;
		return this;
	}

	/**
	 * Sets the repair material of the tool.
	 * @param repairMaterial the repair material.
	 * @return ThorShears
	 */
	public ThorShears setRepairMaterial(ItemStack repairMaterial)
	{
		this.canRepair = true;
		this.repairMaterial = repairMaterial;
		return this;
	}
	
	/**
	 * Sets the modId of the item.
	 * @param modId the modId of the item.
	 * @return ThorShears
	 */
	public ThorShears modId(String modId)
	{
		this.modId = modId;
		return this;
	}
	
	/**
	 * Adds an enchantment to the tool.
	 * @param enchantment the enchantment id.
	 * @param level the enchantment level.
	 * @return ThorShears
	 */
	public ThorShears setEffect(Enchantment enchantment, int level)
	{
		this.hasEffect = true;
		this.effect[0] = enchantment;
		this.effect[1] = level;
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the item will be displayed.
	 * @param creativeTab the CreativeTabs.
	 * @return ThorShears
	 */
	public ThorShears setTab(CreativeTabs creativeTab)
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