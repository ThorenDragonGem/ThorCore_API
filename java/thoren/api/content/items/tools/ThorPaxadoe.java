package thoren.api.content.items.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author ThorenDragonGem
 */
public class ThorPaxadoe extends ItemPickaxe
{
	private final ToolMaterial toolMaterial;
	private ItemStack repairMaterial;
	private boolean hasToolTip = false;
	private boolean hasEffect = false;
	private Object effect[] = new Object[2];
	private static HashMap<String, List<ThorPaxadoe>> paxadeWithmodIdMap = new HashMap<String, List<ThorPaxadoe>>();
	private List<String> toolTipStrings = Lists.newArrayList();
	private String modId;

	/**
	 * Creates a ThorPaxadoe (pickaxe + axe + spade + hoe).
	 * @param toolMaterial the tool material of the item.
	 */
	public ThorPaxadoe(ToolMaterial toolMaterial) 
	{
		super(toolMaterial);
		this.toolMaterial = toolMaterial;
	}
	
	/**
	 * Sets the name of the tool.
	 * @param unlocalizedName the name of the tool.
	 * @return ThorPaxadoe
	 */
	public ThorPaxadoe setItemName(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		GameRegistry.registerItem(this, unlocalizedName);
		ContentRegistry.registerItem(this, unlocalizedName, modId, ContentTypes.Item.TOOL);
		return this;
	}
	
	/**
	 * Sets the repair material of the tool.
	 * @param repairMaterial the repair material of the tool.
	 * @return ThorPaxadoe
	 */
	public ThorPaxadoe setRepairMaterial(ItemStack repairMaterial)
	{
		this.canRepair = true;
		this.repairMaterial = repairMaterial;
		return this;
	}
	
	/**
	 * Adds a tool tip on the tool.
	 * @param toolTip the String displayed on the tool.
	 * @return ThorPaxadoe
	 */
	public ThorPaxadoe addToolTip(String toolTip)
	{
		this.toolTipStrings.add(toolTip);
		this.hasToolTip = true;
		return this;
	}
	
	/**
	 * Sets the harvest level of the tool.
	 * @param level the level of the tool.
	 * @return ThorPaxadoe
	 */
	public ThorPaxadoe setToolHarvestLevel(int level)
	{
		this.setHarvestLevel("pickaxe", level);
		return this;
	}

	/**
	 * Sets the modId of the item.
	 * @param modId the modId of the item.
	 * @return ThorPaxadoe
	 */
	public ThorPaxadoe modId(String modId)
	{
		List<ThorPaxadoe> list = Lists.newArrayList();
		list.add(this);
		this.modId = modId;
		if(this.paxadeWithmodIdMap.containsKey(modId))
			this.paxadeWithmodIdMap.get(modId).add(this);
		else
			this.paxadeWithmodIdMap.put(modId, list);
		return this;
	}
	
	/**
	 * Adds an enchantment to the tool.
	 * @param enchantment the enchantment id.
	 * @param level the enchantment level.
	 * @return ThorPaxadoe
	 */
	public ThorPaxadoe setEffect(Enchantment enchantment, int level)
	{
		this.hasEffect = true;
		this.effect[0] = enchantment;
		this.effect[1] = level;
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the item will be displayed.
	 * @param creativeTab the CreativeTabs.
	 * @return ThorPaxadoe
	 */
	public ThorPaxadoe setTab(CreativeTabs creativeTab)
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
	
	public static List<ThorPaxadoe> getItemListFrommodId(String modId)
	{
		if(paxadeWithmodIdMap.containsKey(modId))
			return paxadeWithmodIdMap.get(modId);
		else
			return Lists.newArrayList();
	}
	
	@Override
	public Set<String> getToolClasses(ItemStack stack)
	{
	    return ImmutableSet.of("pickaxe", "spade", "axe");
	}
	
	private static Set effectiveAgainst = Sets.newHashSet(new Block[] {
		    Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, 
		    Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, 
		    Blocks.soul_sand, Blocks.mycelium, Blocks.planks, 
		    Blocks.bookshelf, Blocks.log, Blocks.log2, 
		    Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin});
	
	@Override
	public boolean canHarvestBlock(Block block)
	{
	    return effectiveAgainst.contains(block) ? true : super.canHarvestBlock(block);
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, Block block)
	{
	    if (block.getMaterial() == Material.wood || block.getMaterial() == Material.vine || block.getMaterial() == Material.plants)
	        return this.efficiencyOnProperMaterial;
	    return effectiveAgainst.contains(block) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(stack, block);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) 
	{
	    if (!player.canPlayerEdit(pos.offset(side), side, stack)) 
	    {
	        return false;
	    } else {
	        int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, player, world, pos);
	        if (hook != 0)
	            return hook > 0;

	        IBlockState iblockstate = world.getBlockState(pos);
	        Block block = iblockstate.getBlock();

	        if (side != EnumFacing.DOWN && world.isAirBlock(pos.up()))
	        {
	            if (block == Blocks.grass) 
	            {
	                return this.useHoe(stack, player, world, pos, Blocks.farmland.getDefaultState());
	            }

	            if (block == Blocks.dirt) 
	            {
	                switch (SwitchDirtType.TYPE_LOOKUP[((BlockDirt.DirtType) iblockstate.getValue(BlockDirt.VARIANT)).ordinal()]) 
	                {
	                case 1:
	                    return this.useHoe(stack, player, world, pos, Blocks.farmland.getDefaultState());
	                case 2:
	                    return this.useHoe(stack, player, world, pos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
	                }
	            }
	        }

	        return false;
	    }
	}

	protected boolean useHoe(ItemStack stack, EntityPlayer player, World worldIn, BlockPos target, IBlockState newState)
	{
	    worldIn.playSoundEffect(target.getX() + 0.5F, target.getY() + 0.5F, target.getZ() + 0.5F, newState.getBlock().stepSound.getStepSound(), (newState.getBlock().stepSound.getVolume() + 1.0F) / 2.0F, newState.getBlock().stepSound.getFrequency() * 0.8F);

	    if (worldIn.isRemote)
	    {
	        return true;
	    } else {
	        worldIn.setBlockState(target, newState);
	        stack.damageItem(1, player);
	        return true;
	    }
	}

	static final class SwitchDirtType
	{

	    static final int[] TYPE_LOOKUP = new int[BlockDirt.DirtType.values().length];
	    private static final String __OBFID = "CL_00002179";

	    static
	    {
	        try
	        {
	            TYPE_LOOKUP[BlockDirt.DirtType.DIRT.ordinal()] = 1;
	        }
	        catch (NoSuchFieldError var2) 
	        {
	            ;
	        }

	        try 
	        {
	            TYPE_LOOKUP[BlockDirt.DirtType.COARSE_DIRT.ordinal()] = 2;
	        }
	        catch (NoSuchFieldError var1)
	        {
	            ;
	        }
	    }
	}
	
	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player)
	{
		if(this.hasEffect)
			itemstack.addEnchantment((Enchantment)this.effect[0], (Integer)this.effect[1]);
	}
}