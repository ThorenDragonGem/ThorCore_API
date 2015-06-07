package thoren.api.content.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

import com.google.common.collect.Lists;

/**
 * @author ThorenDragonGem
 */
public class ThorMultiOre extends Block
{
	private String modId = "";
	private ArrayList<ItemStack> droplist;
	private String toolClass;
	private int toolLevel;
	private ThorMultiOre object;
	private boolean isbeaconbase = false;
	private static HashMap<String, List<ThorMultiOre>> blockWithmodIDMap = new HashMap<String, List<ThorMultiOre>>();

	/**
	 * Creates a ThorMultiOre.
	 * @param material the material of the block. See Material.
	 */
	public ThorMultiOre(Material material)
	{
		super(material);
	}
	
	/**
	 * Sets the modId of the block.
	 * @param modId the modId of the block
	 * @return ThorMultiOre
	 */
	public ThorMultiOre modId(String modId)
	{
		List<ThorMultiOre> list = Lists.newArrayList();
		list.add(this);
		this.modId = modId;
		if(this.blockWithmodIDMap.containsKey(modId))
			this.blockWithmodIDMap.get(modId).add(this);
		else
			this.blockWithmodIDMap.put(modId, list);
		return this;
	}
	
	/**
	 * Sets the name of the block.
	 * @param name the name of the block.
	 * @return ThorMultiOre
	 */
	public ThorMultiOre setBlockName(String name)
	{
		this.setUnlocalizedName(name);
		this.object = this;
		GameRegistry.registerBlock(this, name);
		ContentRegistry.registerBlock(this, name, modId, ContentTypes.Block.GENERAL);
		return this;
	}
	
	/**
	 * Sets the hardness of the block.
	 * @param hardness the hardness amount of the block (depends of materials, stone: 1.5F, obsidian: 50.0F).
	 * @return ThorMultiOre
	 */
	public ThorMultiOre setBlockHardness(float hardness)
	{
		this.setHardness(hardness);
		return this;
	}
	
	/**
	 * Sets the resistance of the block.
	 * @param resistance the blast resistance of the block (depends of materials, stone: 10.0F, obsidian: 2000.0F).
	 * @return ThorMultiOre
	 */
	public ThorMultiOre setBlockResistance(float resistance)
	{
		this.setResistance(resistance);
		return this;
	}
	
	/**
	 * Sets the harvest tool and level of the block.
	 * @param toolclass the tool class which can break the block.
	 * @param toollevel the level of the tool. Wood: 0, Stone: 1, Iron: 2, Diamond: 3, Gold: 0
	 * @return ThorMultiOre
	 */
	public ThorMultiOre setBlockHarvestLevel(String toolclass, int toollevel)
	{
		this.setHarvestLevel(toolclass, toollevel);
		this.toolClass = toolclass;
		this.toolLevel = toollevel;
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the block will be displayed.
	 * @param tab the CreativeTabs.
	 * @return ThorMultiOre
	 */
	public ThorMultiOre setTab(CreativeTabs tab)
	{
		this.setCreativeTab(tab);
		return this;
	}
	
	/**
	 * Sets what sound is played when you place, or break the block.
	 * @param sound the sound played.
	 * @return ThorMultiOre
	 */
	public ThorMultiOre setBlockStepSound(Block.SoundType sound)
	{
		this.setStepSound(sound);
		return this;
	}
	
	/**
	 * Sets the light level emitted by the block.
	 * @param lightlevel the light level emitted.
	 * @return ThorMultiOre
	 */
	public ThorMultiOre setBlockLightLevel(float lightlevel)
	{
		this.setLightLevel(lightlevel);
		return this;
	}

	/**
	 * Sets the block as unbreakable.
	 * @return ThorMultiOre
	 */
	public ThorMultiOre isUnbreakable()
	{
		this.setBlockUnbreakable();
		return this;
	}
	
	/**
	 * Sets if the block receive randomly ticks.
	 * @param tick true for receiving, and false for not.
	 * @return ThorMultiOre
	 */
	public ThorMultiOre setBlockTickRandomly(boolean tick)
	{
		this.setTickRandomly(tick);
		return this;
	}
	
	/**
	 * Sets if the block can be a part of a beacon base.
	 * @param isbeaconbase if the block can be a part of a beacon base.
	 * @return ThorMultiOre
	 */
	public ThorMultiOre setBeaconBase(boolean isbeaconbase)
	{
		this.isbeaconbase = isbeaconbase;
		return this;
	}
	
	public ThorMultiOre setDropList(ArrayList<ItemStack> stack)
	{
		this.droplist = stack;
		return this;
	}
	
	public ArrayList<ItemStack> getDropList()
	{
		return droplist;
	}
	
	@Override
	public ArrayList getDrops(IBlockAccess world, BlockPos pos, IBlockState blockstate, int fortune)
	{
		ArrayList<ItemStack> drops = this.getDropList();
		return drops;
	}
	
	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
    {
		return this == object;
    }
	
	@Override
	public String getHarvestTool(IBlockState state)
	{
		return toolClass;
	}

	@Override
	public int getHarvestLevel(IBlockState state)
	{
		return toolLevel;
	}
}