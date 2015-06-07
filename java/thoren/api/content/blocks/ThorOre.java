package thoren.api.content.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
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
public class ThorOre extends Block
{
	private String modId = "";
	private int max;
	private int min;
	private ArrayList<ItemStack> droplist;
	private String toolClass;
	private int toolLevel;
	private ThorOre object;
	private boolean isbeaconbase = false;
	private static HashMap<String, List<ThorOre>> blockWithmodIDMap = new HashMap<String, List<ThorOre>>();
	private static Item drop;
	private static int meta;

	/**
	 * Creates a ThorOre.
	 * @param material the material of the block. See Material.
	 */
	public ThorOre(Material material)
	{
		super(material);
	}
	
	/**
	 * Sets the modId of the block.
	 * @param modId the modId of the block.
	 * @return ThorOre
	 */
	public ThorOre modId(String modId)
	{
		List<ThorOre> list = Lists.newArrayList();
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
	 * @return ThorOre
	 */
	public ThorOre setBlockName(String name)
	{
		this.setUnlocalizedName(name);
		this.object = this;
		GameRegistry.registerBlock(this, name);
		ContentRegistry.registerBlock(this, name, modId, ContentTypes.Block.ORE);
		return this;
	}
	
	/**
	 * Sets the hardness of the block.
	 * @param hardness the hardness amount of the block (depends of materials, stone: 1.5F, obsidian: 50.0F).
	 * @return ThorOre ThorOre
	 */
	public ThorOre setBlockHardness(float hardness)
	{
		this.setHardness(hardness);
		return this;
	}
	
	/**
	 * Sets the resistance of the block.
	 * @param resistance the blast resistance of the block (depends of materials, stone: 10.0F, obsidian: 2000.0F).
	 * @return ThorOre
	 */
	public ThorOre setBlockResistance(float resistance)
	{
		this.setResistance(resistance);
		return this;
	}
	
	/**
	 * Sets the harvest tool and level of the block.
	 * @param toolclass the tool class which can break the block.
	 * @param toollevel the level of the tool. Wood: 0, Stone: 1, Iron: 2, Diamond: 3, Gold: 0
	 * @return ThorOre
	 */
	public ThorOre setBlockHarvestLevel(String toolclass, int toollevel)
	{
		this.setHarvestLevel(toolclass, toollevel);
		this.toolClass = toolclass;
		this.toolLevel = toollevel;
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the block will be displayed.
	 * @param tab the CreativeTabs.
	 * @return ThorOre
	 */
	public ThorOre setTab(CreativeTabs tab)
	{
		this.setCreativeTab(tab);
		return this;
	}
	
	/**
	 * Sets what sound is played when you place, or break the block.
	 * @param sound the sound played.
	 * @return ThorOre
	 */
	public ThorOre setBlockStepSound(Block.SoundType sound)
	{
		this.setStepSound(sound);
		return this;
	}
	
	/**
	 * Sets the light level emitted by the block.
	 * @param lightlevel the light level emitted.
	 * @return ThorOre
	 */
	public ThorOre setBlockLightLevel(float lightlevel)
	{
		this.setLightLevel(lightlevel);
		return this;
	}

	/**
	 * Sets the block as unbreakable.
	 * @return ThorOre
	 */
	public ThorOre isUnbreakable()
	{
		this.setBlockUnbreakable();
		return this;
	}
	
	/**
	 * Sets if the block receive randomly ticks.
	 * @param tick true for receiving, and false for not.
	 * @return ThorOre
	 */
	public ThorOre setBlockTickRandomly(boolean tick)
	{
		this.setTickRandomly(tick);
		return this;
	}
	
	/**
	 * Sets if the block can be a part of a beacon base.
	 * @param isbeaconbase if the block can be a part of a beacon base.
	 * @return ThorOre
	 */
	public ThorOre setBeaconBase(boolean isbeaconbase)
	{
		this.isbeaconbase = isbeaconbase;
		return this;
	}
	
	/**
	 * Sets the drops of the block.
	 * @param item the item dropped.
	 * @param meta the meta-data of the item
	 * @param min the minimum amount dropped.
	 * @param max the maximum amount dropped.
	 * @return ThorOre
	 */
	public ThorOre setBlockDrops(Item item, int meta, int min, int max)
	{
		this.drop = item;
		this.meta = meta;
		this.min = min;
		this.max = max;
		return this;
	}
	
	@Override
	public Item getItemDropped(IBlockState blockstate, Random random, int fortune)
	{
	    return this.drop;
	}
	
	@Override
	public int damageDropped(IBlockState blockstate)
	{
	    return this.meta;
	}

	@Override
	public int quantityDropped(IBlockState blockstate, int fortune, Random random)
	{
	    if (this.min >= this.max)
	        return this.min;
	    return this.min + random.nextInt(this.max - this.min + fortune + 1);
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